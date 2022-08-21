package verdecraft.verdecraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import javax.swing.*;
import java.util.concurrent.TimeUnit;


public class TorchBurnOut implements Listener
    {
        Verdecraft plugin;
        public TorchBurnOut(Verdecraft plugin)
        {
            this.plugin = plugin;
        }
        @EventHandler
        public void onTorchPlace(BlockPlaceEvent event)
        {
            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if(event.getBlock().getType() == Material.TORCH || event.getBlock().getType() == Material.WALL_TORCH)
                    {
                        event.getBlock().getWorld().dropItem(event.getBlock().getLocation(),new ItemStack(Material.STICK,1));
                        event.getBlock().setType(Material.AIR);
                    }
                    else
                    {
                        return;
                    }
                }
            }.runTaskLater(plugin,20*60*3);
        }

    }


