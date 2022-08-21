package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

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

            container.set(key,PersistentDataType.INTEGER,10);
            state.update();

            ReactorWorking(event.getBlock(), upperblock);
       }
    }

    public void ReactorWorking(Block reactor, Block reactor_chest)
    {
        if(reactor.getType().equals(BlockManager.NuclearReactor.getType()) && reactor_chest.getState() instanceof TileState)
        {
            TileState state = (TileState) reactor_chest.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
            if(container.has(key,PersistentDataType.INTEGER))
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
                    if(RNG == 0)
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
