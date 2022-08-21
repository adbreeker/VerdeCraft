package verdecraft.verdecraft.handlers.items.lamp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

import java.util.List;

public class LampTurnOnLight implements Listener
{
    Verdecraft plugin;
    public LampTurnOnLight(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onLampTurnOn(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.LEFT_CLICK_AIR)
        {
            if(event.getItem() != null)
            {
                if(event.getItem().getItemMeta().displayName() != null)
                {
                    if(event.getItem().getItemMeta().displayName().equals(ItemManager.ElectricLamp.getItemMeta().displayName()) )
                    {
                        ItemStack item = event.getItem();
                        ItemMeta meta = item.getItemMeta();
                        List<String> lore = meta.getLore();
                        int actual_energy = Integer.parseInt(lore.get(0).split(" ")[2].split("/")[0]);
                        if(actual_energy > 0)
                        {
                            LampTurnedOn(item, event.getPlayer());
                        }
                    }
                }
            }
        }
    }

    void LampTurnedOn(ItemStack item, Player player)
    {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        int actual_energy = Integer.parseInt(lore.get(0).split(" ")[2].split("/")[0]);
        int new_energy = actual_energy - 1;
        if(new_energy > 0){lore.set(0,"§aLamp power: "+ new_energy + "/600");}
        else{lore.set(0,"§4Lamp power: "+ new_energy + "/600");}
        meta.setLore(lore);
        item.setItemMeta(meta);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40,1));

        if(new_energy>0)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(player.getInventory().getItemInMainHand().equals(item) || player.getInventory().getItemInOffHand().equals(item))
                    {

                        LampTurnedOn(item, player);
                    }
                }
            }.runTaskLater(plugin,20);
        }
    }
}
