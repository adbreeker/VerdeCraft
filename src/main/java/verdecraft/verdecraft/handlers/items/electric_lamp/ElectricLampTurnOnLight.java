package verdecraft.verdecraft.handlers.items.electric_lamp;

import org.bukkit.Material;
import org.bukkit.block.Block;
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

public class ElectricLampTurnOnLight implements Listener
{
    Verdecraft plugin;
    public ElectricLampTurnOnLight(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onLampTurnOn(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.LEFT_CLICK_AIR)
        {
            if(event.getItem() != null)
            {
                if(ItemManager.isElectricLamp(event.getItem()))
                {
                    if(ItemManager.getElectricLampPower(event.getItem()) > 0)
                    {
                        LampTurnedOn(event.getItem(), event.getPlayer());
                    }
                }
            }
        }
    }

    void LampTurnedOn(ItemStack lamp, Player player)
    {
        ItemManager.changeMiningHelmetPower(lamp, -1);
        Block block_to_light = player.getLocation().getWorld().getBlockAt(player.getLocation().getBlockX(),player.getLocation().getBlockY()+1,player.getLocation().getBlockZ());
        Block upper = player.getLocation().getWorld().getBlockAt(player.getLocation().getBlockX(),player.getLocation().getBlockY()+2,player.getLocation().getBlockZ());
        Block lower = player.getLocation().getWorld().getBlockAt(player.getLocation().getBlockX(),player.getLocation().getBlockY(),player.getLocation().getBlockZ());

        if(block_to_light.getType() == Material.AIR || block_to_light.getType() == Material.LIGHT)
        {
            block_to_light.setType(Material.LIGHT);
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(block_to_light.getWorld().getBlockAt(block_to_light.getLocation()).getType() == Material.LIGHT)
                    {
                        block_to_light.setType(Material.AIR);
                    }
                }
            }.runTaskLater(plugin,5);
        }
        else
        {
            if(upper.getType() == Material.AIR || block_to_light.getType() == Material.LIGHT)
            {
                upper.setType(Material.LIGHT);
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        if(upper.getWorld().getBlockAt(upper.getLocation()).getType() == Material.LIGHT)
                        {
                            upper.setType(Material.AIR);
                        }
                    }
                }.runTaskLater(plugin,5);
            }
            else
            {
                if(lower.getType() == Material.AIR || block_to_light.getType() == Material.LIGHT)
                {
                    lower.setType(Material.LIGHT);
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            if(lower.getWorld().getBlockAt(lower.getLocation()).getType() == Material.LIGHT)
                            {
                                lower.setType(Material.AIR);
                            }
                        }
                    }.runTaskLater(plugin,5);
                }
            }
        }

        if(ItemManager.getElectricLampPower(lamp)>0)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(player.getInventory().getItemInMainHand().equals(lamp) || player.getInventory().getItemInOffHand().equals(lamp))
                    {

                        LampTurnedOn(lamp, player);
                    }
                }
            }.runTaskLater(plugin,5);
        }
    }

}
