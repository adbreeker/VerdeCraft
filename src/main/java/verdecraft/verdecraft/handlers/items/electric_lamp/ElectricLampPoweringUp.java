package verdecraft.verdecraft.handlers.items.electric_lamp;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

public class ElectricLampPoweringUp implements Listener
{
    Verdecraft plugin;
    public ElectricLampPoweringUp(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPoweringByCrafting(PrepareItemCraftEvent event)
    {
        int lamps = 0;
        int batteries = 0;
        int air = 0;
        ItemStack lamp = ItemManager.ElectricLamp;
        for(ItemStack item : event.getInventory().getMatrix())
        {
            if(item != null)
            {
                if(ItemManager.isElectricLamp(item))
                {
                    lamps++;
                    lamp = item;
                }
                if(item.getItemMeta() != null)
                {
                    if(item.getItemMeta().equals(ItemManager.Battery.getItemMeta()))
                    {
                        batteries++;
                    }
                }
            }
            else
            {
                air++;
            }
        }
        if(lamps == 1 && (lamps+batteries+air) == event.getInventory().getMatrix().length)
        {
            if(batteries == 0)
            {
                event.getInventory().setResult(lamp);
            }
            else
            {
                ItemManager.changeSerialNumber();
                ItemStack lamp_view = ItemManager.ElectricLamp;
                ItemManager.setElectricLampPower(lamp_view, ItemManager.getElectricLampPower(lamp)+batteries*400);

                event.getInventory().setResult(lamp_view);
            }
        }
    }
}
