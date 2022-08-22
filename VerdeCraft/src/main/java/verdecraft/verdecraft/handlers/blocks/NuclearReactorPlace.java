package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.TileState;
import org.bukkit.block.data.Lightable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.material.Redstone;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NuclearReactorPlace implements Listener
{
    Verdecraft plugin;
    public NuclearReactorPlace(Verdecraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNuclearReactorPlace(BlockPlaceEvent event)
    {
       if(event.getItemInHand().getItemMeta().equals(BlockManager.NuclearReactor.getItemMeta()))
       {
            BlockLocations.addNuclearReactor(event.getBlock().getLocation());
            Block upperblock = event.getBlock().getLocation().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(),event.getBlock().getLocation().getBlockY()+1,event.getBlock().getLocation().getBlockZ());
            upperblock.setType(Material.CHEST);
            TileState state = (TileState) upperblock.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");

            container.set(key,PersistentDataType.INTEGER,0);
            state.update();

            ReactorWorking(event.getBlock(), upperblock);
       }
    }

    public void ReactorWorking(Block reactor, Block reactor_chest)
    {
        if(isNuclearReactor(reactor))
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

    public static boolean isNuclearReactor(Block potential)
    {
        Block upper = potential.getWorld().getBlockAt(potential.getX(), potential.getY()+1, potential.getZ());
        if(potential.getType().equals(BlockManager.NuclearReactor.getType()) && upper.getState() instanceof TileState)
        {
            TileState state = (TileState) upper.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class), "reactor-fuel");
            if (container.has(key, PersistentDataType.INTEGER))
            {
                return true;
            }
        }
        return false;
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
            if(isNuclearReactor(block))
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


}
