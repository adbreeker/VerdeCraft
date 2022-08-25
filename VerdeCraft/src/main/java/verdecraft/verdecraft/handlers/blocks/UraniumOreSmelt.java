package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.BlockManager;

public class UraniumOreSmelt implements Listener
{
    Verdecraft plugin;
    public UraniumOreSmelt(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    void UraniumSmelt(FurnaceStartSmeltEvent event)
    {
        if(event.getSource().getType().equals(BlockManager.UraniumOre.getType()))
        {
            event.getBlock().setType(Material.AIR);
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    event.getBlock().setType(Material.AIR);
                    event.getBlock().getLocation().createExplosion(5,true);
                    event.getBlock().setType(Material.AIR);
                }
            }.runTaskLater(plugin,1);
        }
    }
}
