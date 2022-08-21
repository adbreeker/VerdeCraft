package verdecraft.verdecraft.handlers.items.lamp;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

import java.util.List;

public class LampPoweringUp implements Listener
{
    Verdecraft plugin;
    public LampPoweringUp(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onBatteryUse(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            if(event.getItem() != null)
            {
                if(event.getItem().getItemMeta() != null)
                {
                    if(event.getItem().getItemMeta().equals(ItemManager.Battery.getItemMeta()))
                    {
                        if(event.getPlayer().getInventory().getItemInOffHand().getItemMeta() != null)
                        {
                            if(event.getPlayer().getInventory().getItemInOffHand().getItemMeta().displayName() != null)
                            {
                                if(event.getPlayer().getInventory().getItemInOffHand().getItemMeta().displayName().equals(ItemManager.ElectricLamp.getItemMeta().displayName()))
                                {
                                    ItemStack item = event.getPlayer().getInventory().getItemInOffHand();
                                    ItemMeta meta = item.getItemMeta();
                                    List<String> lore = meta.getLore();
                                    int actual_energy = Integer.parseInt(lore.get(0).split(" ")[2].split("/")[0]);
                                    if(actual_energy < 595)
                                    {
                                        event.getItem().setAmount(event.getItem().getAmount()-1);
                                        int new_energy = actual_energy + 100;
                                        if(new_energy > 600)
                                        {
                                            new_energy = 600;
                                        }
                                        lore.set(0,"§aLamp power: "+ new_energy + "/600");
                                        meta.setLore(lore);
                                        item.setItemMeta(meta);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
