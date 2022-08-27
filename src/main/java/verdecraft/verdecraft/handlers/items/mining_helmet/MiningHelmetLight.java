package verdecraft.verdecraft.handlers.items.mining_helmet;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.ItemManager;

import java.awt.*;

public class MiningHelmetLight implements Listener
{
    Verdecraft plugin;
    public MiningHelmetLight(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onMiningHelmetEquip(PlayerArmorChangeEvent event)
    {
        if(event.getNewItem() != null)
        {
            if(event.getNewItem().getType() == Material.TURTLE_HELMET && event.getOldItem().getType() !=Material.TURTLE_HELMET)
            {
                if(event.getNewItem().getItemMeta() != null)
                {
                    if(event.getNewItem().getItemMeta().getLore() != null)
                    {
                        makeLight(event.getNewItem(), event.getPlayer());
                    }
                }
            }
        }
    }

    public void makeLight(ItemStack helmet, Player player)
    {
        ItemManager.changeMiningHelmetPower(helmet, -1);
        player.getInventory().setHelmet(helmet);
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

        if(ItemManager.getMiningHelmetPower(helmet) > 0)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(player.getInventory().getHelmet() != null)
                    {
                        if(player.getInventory().getHelmet().getType() == Material.TURTLE_HELMET)
                        {
                            makeLight(helmet, player);
                        }
                    }
                }
            }.runTaskLater(plugin,5);
        }

    }
}
