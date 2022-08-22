package verdecraft.verdecraft.easter_egg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.util.Random;

public class HFW_Handler implements Listener
{
    Verdecraft plugin;
    public HFW_Handler(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onHellFireWandUse(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.LEFT_CLICK_AIR)
        {
            if(event.getItem() != null)
            {
                if(event.getItem().getItemMeta() != null)
                {
                    if(event.getItem().getItemMeta().equals(EasterEggItems.HellFireWand.getItemMeta()))
                    {
                        Location player_location = event.getPlayer().getLocation();
                        int radius = 32;
                        for(int x = (radius * -1); x<radius; x++)
                        {
                            for(int y = -20; y<20; y++)
                            {
                                for(int z = (radius * -1); z<radius; z++)
                                {
                                    Block block_to_change = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,player_location.getBlockY()+y,player_location.getBlockZ()+z);
                                    if(block_to_change.getType() != Material.AIR)
                                    {
                                        if(block_to_change.getType() == Material.OAK_LOG || block_to_change.getType() == Material.BIRCH_LOG || block_to_change.getType() == Material.OAK_LEAVES || block_to_change.getType() == Material.BIRCH_LEAVES)
                                        {
                                            if(block_to_change.getType() == Material.OAK_LOG || block_to_change.getType() == Material.BIRCH_LOG)
                                            {
                                                block_to_change.setType(Material.CRIMSON_STEM);
                                            }
                                            else
                                            {
                                                block_to_change.setType(Material.NETHER_WART_BLOCK);
                                            }
                                        }
                                        else
                                        {
                                            Random random = new Random();
                                            int choice = random.nextInt(21);
                                            if(choice <= 10)
                                            {
                                                block_to_change.setType(Material.NETHERRACK);
                                            }
                                            if(choice == 11)
                                            {
                                                block_to_change.setType(Material.LAVA);
                                            }
                                            if(choice == 12)
                                            {
                                                block_to_change.setType(Material.NETHER_GOLD_ORE);
                                            }
                                            if(choice == 13)
                                            {
                                                block_to_change.setType(Material.FIRE);
                                            }
                                            if(choice == 14)
                                            {
                                                block_to_change.setType(Material.GRAVEL);
                                            }
                                            if(choice == 15)
                                            {
                                                block_to_change.setType(Material.NETHER_QUARTZ_ORE);
                                            }
                                            if(choice == 16 || choice == 17)
                                            {
                                                block_to_change.setType(Material.CRIMSON_NYLIUM);
                                            }
                                            if(choice > 17)
                                            {
                                                block_to_change.setType(Material.MAGMA_BLOCK);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        TemperatureConfig.modifyTemp(300);
                        event.getPlayer().sendMessage("§6This is the true power of hell");
                    }
                }
            }
        }
        if(event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            if(event.getItem() != null)
            {
                if (event.getItem().getItemMeta().displayName() != null)
                {
                    if (event.getItem().getItemMeta().equals(EasterEggItems.HellFireWand.getItemMeta()))
                    {
                        event.getPlayer().launchProjectile(Fireball.class);
                    }
                }
            }
        }
    }
}
