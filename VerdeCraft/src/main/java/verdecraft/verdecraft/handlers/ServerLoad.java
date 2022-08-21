package verdecraft.verdecraft.handlers;

import org.bukkit.Location;
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
                for(Location NuclearReactor : (List<Location>) BlockLocations.get().getList("NuclearReactors"))
                {
                    Block reactor = NuclearReactor.getBlock();
                    Block reactor_chest = reactor.getWorld().getBlockAt(reactor.getX(), reactor.getY()+1, reactor.getZ());
                    if(reactor.getType().equals(BlockManager.NuclearReactor.getType()) && reactor_chest.getState() instanceof TileState)
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
        if(reactor.getType().equals(BlockManager.NuclearReactor.getType()) && reactor_chest.getState() instanceof TileState)
        {
            TileState state = (TileState) reactor_chest.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
            if(container.has(key, PersistentDataType.INTEGER))
            {
                int reactor_fuel = container.get(key,PersistentDataType.INTEGER);
                if(reactor_fuel > 0)
                {
                    System.out.println("reaktor pracuje " + reactor_fuel);
                    reactor_fuel = reactor_fuel - 1;
                    container.set(key,PersistentDataType.INTEGER,reactor_fuel);
                    state.update();
                    Random random = new Random();
                    int RNG = random.nextInt(300);
                    if(RNG != 0)
                    {
                        Chest chest = (Chest) reactor_chest.getState();
                        chest.getInventory().addItem(ItemManager.Battery);
                    }
                }
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        ReactorWorking(reactor.getWorld().getBlockAt(reactor.getLocation()), reactor_chest.getWorld().getBlockAt(reactor_chest.getLocation()));
                    }
                }.runTaskLater(plugin,20);
            }
        }
    }
}
