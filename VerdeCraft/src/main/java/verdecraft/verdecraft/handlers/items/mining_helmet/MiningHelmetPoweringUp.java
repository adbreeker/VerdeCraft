package verdecraft.verdecraft.handlers.items.mining_helmet;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

public class MiningHelmetPoweringUp implements Listener
{
    Verdecraft plugin;
    public MiningHelmetPoweringUp(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPoweringByCrafting(PrepareItemCraftEvent event)
    {
        int helmets = 0;
        int batteries = 0;
        int air = 0;
        ItemStack helmet = ItemManager.MiningHelmet;
        for(ItemStack item : event.getInventory().getMatrix())
        {
            if(item != null)
            {
                if(item.getType() == Material.TURTLE_HELMET)
                {
                    helmets++;
                    helmet = item;
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
        if(helmets == 1 && (helmets+batteries+air) == event.getInventory().getMatrix().length)
        {
            if(batteries == 0)
            {
                event.getInventory().setResult(helmet);
            }
            else
            {
                ItemStack helmet_view = ItemManager.MiningHelmet;
                ItemManager.setMiningHelmetPower(helmet_view, ItemManager.getElectricLampPower(helmet)+batteries*400);
                helmet_view.setDurability(helmet.getDurability());

                event.getInventory().setResult(helmet_view);
            }
        }
    }
}
