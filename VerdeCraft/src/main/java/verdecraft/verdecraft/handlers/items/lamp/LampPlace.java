package verdecraft.verdecraft.handlers.items.lamp;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

import java.util.List;

public class LampPlace implements Listener
{
    Verdecraft plugin;
    public LampPlace(Verdecraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLampPlace(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(event.getItem() != null)
            {
                if(event.getItem().getItemMeta().displayName() != null)
                {
                    if(event.getItem().getItemMeta().displayName().equals(ItemManager.ElectricLamp.getItemMeta().displayName()) )
                    {
                        event.setCancelled(true);
                        event.getPlayer().sendMessage("§4You can not place an electric lamp");
                    }
                }
            }
        }
    }


}
