package verdecraft.verdecraft.handlers.items.mining_helmet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

public class MiningHelmetCrafting implements Listener
{
    Verdecraft plugin;
    public MiningHelmetCrafting(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onMiningHelmetCrafting(PrepareItemCraftEvent event)
    {
        int lamps = 0;
        int helmets = 0;
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
                if(item.getType() == Material.GOLDEN_HELMET)
                {
                    if(item.getDurability() == 0)
                    {
                        helmets++;
                    }
                }
            }
            else
            {
                air++;
            }
        }
        if(lamps == 1 && helmets == 1 && (lamps+helmets+air) == event.getInventory().getMatrix().length)
        {
            ItemStack result = ItemManager.MiningHelmet;
            ItemManager.setMiningHelmetPower(result, ItemManager.getElectricLampPower(lamp));
            event.getInventory().setResult(result);
        }
    }
}
