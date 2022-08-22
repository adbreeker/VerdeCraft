package verdecraft.verdecraft.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.handlers.blocks.NuclearReactorPlace;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ServerLoad implements Listener
{
    Verdecraft plugin;
    public ServerLoad(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onServerLoad(ServerLoadEvent event)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                List<Location> check_reactors = new ArrayList<>((List<Location>) BlockLocations.get().getList("NuclearReactors"));
                for(Location NuclearReactor : check_reactors)
                {
                    Block reactor = NuclearReactor.getBlock();
                    Block reactor_chest = reactor.getWorld().getBlockAt(reactor.getX(), reactor.getY()+1, reactor.getZ());
                    if(NuclearReactorPlace.isNuclearReactor(reactor))
                    {
                        System.out.println("wznawiam prace reaktora");
                        ReactorWorking(reactor,reactor_chest);
                    }
                    else
                    {
                        System.out.println("usuwam nieistniejacy reaktor");
                        BlockLocations.deleteNuclearReactor(NuclearReactor);
                    }
                }
            }
        }.runTaskLater(plugin,20*10);

    }

    public void ReactorWorking(Block reactor, Block reactor_chest)
    {
        if(NuclearReactorPlace.isNuclearReactor(reactor))
        {
            TileState state = (TileState) reactor_chest.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
            int reactor_fuel = container.get(key,PersistentDataType.INTEGER);
            if(reactor_fuel > 0)
            {
                reactor_fuel = reactor_fuel - 1;
                container.set(key,PersistentDataType.INTEGER,reactor_fuel);
                state.update();
                Random random = new Random();
                int RNG = random.nextInt(1000);
                if(RNG == 0)
                {
                    Chest chest = (Chest) reactor_chest.getState();
                    chest.getInventory().addItem(ItemManager.Battery);
                }
                List<Block> cables = new ArrayList<>();
                List<Block> devices = new ArrayList<>();
                devices.add(reactor);
                NuclearReactorPlace.findConnections(reactor,cables,devices);
                if(reactor_fuel == 0)
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for(Block check_in : devices)
                            {
                                if(check_in.getType() == Material.REDSTONE_LAMP)
                                {
                                    NuclearReactorPlace.setLampLight(check_in,false);
                                }
                            }
                        }
                    }.runTaskLater(plugin,10);
                }
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        List<Block> cables_check = new ArrayList<>();
                        List<Block> devices_check = new ArrayList<>();
                        devices_check.add(reactor);
                        NuclearReactorPlace.findConnections(reactor,cables_check,devices_check);
                        for(Block check_in : devices)
                        {
                            if(!devices_check.contains(check_in))
                            {
                                if(check_in.getType() == Material.REDSTONE_LAMP)
                                {
                                    NuclearReactorPlace.setLampLight(check_in,false);
                                }
                            }
                        }
                    }
                }.runTaskLater(plugin,5);
            }
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    ReactorWorking(reactor.getWorld().getBlockAt(reactor.getLocation()), reactor_chest.getWorld().getBlockAt(reactor_chest.getLocation()));
                }
            }.runTaskLater(plugin,5);
        }
    }
}
