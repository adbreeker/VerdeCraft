package verdecraft.verdecraft.handlers.items.electric_lamp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

public class ElectricLampCrafting implements Listener
{
    Verdecraft plugin;
    public ElectricLampCrafting(Verdecraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public static void onLampCrafting(CraftItemEvent event)
    {
        if(event.getRecipe().getResult().getItemMeta().displayName().equals(ItemManager.ElectricLamp.getItemMeta().displayName()))
        {
            ItemManager.changeSerialNumber();
            ItemManager.recreateRecipe();
        }
    }

}
