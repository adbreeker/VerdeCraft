package verdecraft.verdecraft.handlers.blocks.nuclear_reactor;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Furnace;
import org.bukkit.block.TileState;
import org.bukkit.block.data.Lightable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MakeNuclearReactorWork
{
    Verdecraft plugin;
    public MakeNuclearReactorWork(Verdecraft plugin, Block reactor, Block reactor_chest)
    {
        this.plugin = plugin;
        ReactorWorking(reactor,reactor_chest);
    }
    public void ReactorWorking(Block reactor, Block reactor_chest)
    {
        if(BlockManager.isNuclearReactor(reactor))
        {
            TileState state = (TileState) reactor_chest.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
            int reactor_fuel = container.get(key, PersistentDataType.INTEGER);
            if(reactor_fuel > 0)
            {
                reactor_fuel = reactor_fuel - 1;
                container.set(key,PersistentDataType.INTEGER,reactor_fuel);
                state.update();
                Random random = new Random();
                int RNG = random.nextInt(5000);
                if(RNG < 3)
                {
                    System.out.println(RNG);
                    Chest chest = (Chest) reactor_chest.getState();
                    chest.getInventory().addItem(ItemManager.Battery);
                }
                if(RNG == 4)
                {
                    System.out.println(RNG);
                    Chest chest = (Chest) reactor_chest.getState();
                    chest.getInventory().addItem(ItemManager.NuclearWaste);
                }
                List<Block> cables = new ArrayList<>();
                List<Block> devices = new ArrayList<>();
                devices.add(reactor);
                findConnections(reactor,cables,devices);
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
                                    setLampLight(check_in,false);
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
                        findConnections(reactor,cables_check,devices_check);
                        for(Block check_in : devices)
                        {
                            if(!devices_check.contains(check_in))
                            {
                                if(check_in.getType() == Material.REDSTONE_LAMP)
                                {
                                    setLampLight(check_in,false);
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



    public static void findConnections(Block center, List<Block> cables, List<Block> devices)
    {
        List<Block> adjacent = new ArrayList<>();
        adjacent.add(center.getWorld().getBlockAt(center.getX()+1,center.getY(), center.getZ()));
        adjacent.add(center.getWorld().getBlockAt(center.getX()-1,center.getY(), center.getZ()));
        adjacent.add(center.getWorld().getBlockAt(center.getX(),center.getY()+1, center.getZ()));
        adjacent.add(center.getWorld().getBlockAt(center.getX(),center.getY()-1, center.getZ()));
        adjacent.add(center.getWorld().getBlockAt(center.getX(),center.getY(), center.getZ()+1));
        adjacent.add(center.getWorld().getBlockAt(center.getX(),center.getY(), center.getZ()-1));
        for(Block block : adjacent)
        {
            if(BlockManager.isNuclearReactor(block))
            {
                if(!devices.contains(block))
                {
                    devices.add(block);
                }
            }
            if(block.getType() == Material.REDSTONE_LAMP)
            {
                if(!devices.contains(block))
                {
                    setLampLight(block, true);
                    devices.add(block);
                }
            }
            if(block.getType() == Material.FURNACE || block.getType() == Material.BLAST_FURNACE || block.getType() == Material.SMOKER)
            {
                if(!devices.contains(block))
                {
                    fireFurnace(block);
                    devices.add(block);
                }
            }
            if(block.getType().equals(BlockManager.Cable.getType()))
            {
                if(!cables.contains(block))
                {
                    cables.add(block);
                    findConnections(block,cables,devices);
                }
            }
        }
    }

    public static void setLampLight(Block lamp, boolean turnOn)
    {
        if(turnOn)
        {
            Lightable lightable = (Lightable) lamp.getBlockData();
            lightable.setLit(true);
            lamp.setBlockData(lightable);
        }
        else
        {
            Lightable lightable = (Lightable) lamp.getBlockData();
            lightable.setLit(false);
            lamp.setBlockData(lightable);
        }
    }

    public static void fireFurnace(Block probably_furnace)
    {
        Furnace furnace = (Furnace) probably_furnace.getState();
        furnace.setBurnTime((short) 6);
        furnace.update();
    }
}
