package verdecraft.verdecraft.easter_egg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Fireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.util.Random;

public class MW_Handler implements Listener
{
    Verdecraft plugin;
    public MW_Handler(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onMidasWillUse(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_AIR)
        {
            if(event.getItem() != null)
            {
                if(event.getItem().getItemMeta() != null)
                {
                    if(event.getItem().getItemMeta().equals(EasterEggItems.MidasWill.getItemMeta()))
                    {
                        Location player_location = event.getPlayer().getLocation();
                        int radius = 100;
                        for(int x = (radius * -1); x<radius; x++)
                        {
                            for(int y = -60; y<100; y++)
                            {
                                for(int z = (radius * -1); z<radius; z++)
                                {
                                    Block block_to_change = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,y,player_location.getBlockZ()+z);
                                    if(block_to_change.getType() == Material.DIAMOND_ORE || block_to_change.getType() == Material.DEEPSLATE_DIAMOND_ORE)
                                    {
                                        makeSigns(block_to_change.getLocation(), Material.DIAMOND_BLOCK);
                                    }
                                    if(block_to_change.getType() == Material.DEEPSLATE_COAL_ORE)
                                    {
                                        makeSigns(block_to_change.getLocation(), Material.COAL_BLOCK);
                                    }
                                    if(block_to_change.getType() == Material.ANCIENT_DEBRIS)
                                    {
                                        makeSigns(block_to_change.getLocation(), Material.NETHERITE_BLOCK);
                                    }
                                    if(block_to_change.getType() == Material.REINFORCED_DEEPSLATE)
                                    {
                                        makeSigns(block_to_change.getLocation(), Material.SCULK_CATALYST);
                                    }
                                }
                            }
                        }
                        event.getPlayer().sendMessage("§6Look for the Midas's signs");
                    }
                }
            }
        }
    }


    public void makeSigns(Location found, Material material)
    {
        for(int i=1; i<250; i++)
        {
            Block makegold = found.getWorld().getBlockAt(found.getBlockX(), found.getBlockY()+i, found.getBlockZ());
            if(makegold.getLocation().getBlockY() < 200)
            {
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        makegold.setType(material);
                    }
                }.runTaskLater(plugin,i);
            }
        }
    }
}
