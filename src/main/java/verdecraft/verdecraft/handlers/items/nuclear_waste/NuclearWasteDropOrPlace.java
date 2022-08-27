package verdecraft.verdecraft.handlers.items.nuclear_waste;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;
import verdecraft.verdecraft.items.ItemManager;

public class NuclearWasteDropOrPlace implements Listener
{
    Verdecraft plugin;
    public NuclearWasteDropOrPlace(Verdecraft plugin){this.plugin = plugin;}

    @EventHandler
    public  void onNuclearWasteDrop(PlayerDropItemEvent event)
    {
        if(event.getItemDrop().getItemStack().equals(ItemManager.NuclearWaste))
        {
            Item item = event.getItemDrop();
            BukkitTask task = new BukkitRunnable()
            {
                int hight = event.getItemDrop().getLocation().getBlockY();
                @Override
                public void run()
                {
                    if(item.getLocation().getBlockY() == hight)
                    {
                        new MakeNuclearWasteCloud(plugin, item.getLocation(), 15, 30);
                        event.getPlayer().sendMessage("Throwing nuclear wastes may cause some serious climate troubles");
                        TemperatureConfig.modifyTemp(0.1);
                        cancel();
                    }
                    else
                    {
                        hight = item.getLocation().getBlockY();
                    }
                }
            }.runTaskTimer(plugin, 10,5);
        }
    }

    @EventHandler
    public  void onNuclearWastePlace(BlockPlaceEvent event)
    {
        ItemStack item = event.getItemInHand();
        item.setAmount(1);
        if(item.equals(ItemManager.NuclearWaste))
        {
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), ItemManager.NuclearWaste);
            new MakeNuclearWasteCloud(plugin, event.getBlock().getLocation(), 15, 30);
            event.getPlayer().sendMessage("Throwing nuclear wastes may cause some serious climate troubles");
            TemperatureConfig.modifyTemp(0.1);
        }
    }
}
