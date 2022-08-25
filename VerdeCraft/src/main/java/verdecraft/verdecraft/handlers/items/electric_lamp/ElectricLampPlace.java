package verdecraft.verdecraft.handlers.items.electric_lamp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

public class ElectricLampPlace implements Listener
{
    Verdecraft plugin;
    public ElectricLampPlace(Verdecraft plugin)
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
