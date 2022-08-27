package verdecraft.verdecraft.handlers.blocks.solar_panel;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.DaylightDetector;
import org.bukkit.block.Furnace;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.MakeNuclearReactorWork;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MakeSolarPanelWork
{
    Verdecraft plugin;
    public MakeSolarPanelWork(Verdecraft plugin, Block solar)
    {
        this.plugin = plugin;
        SolarWorking(solar);
    }

    public void SolarWorking(Block solar)
    {
        if(solar.getType() == Material.DAYLIGHT_DETECTOR)
        {
            Block lower = solar.getWorld().getBlockAt(solar.getX(), solar.getY()-1, solar.getZ());
            if(solar.getWorld().getTime() >= 3000 && solar.getWorld().getTime() <= 9000 && solar.getLightLevel() == 15)
            {
                if(lower.getType() == Material.FURNACE || lower.getType() == Material.BLAST_FURNACE || lower.getType() == Material.SMOKER)
                {
                    MakeNuclearReactorWork.fireFurnace(lower);
                }
                if(lower.getType() == Material.REDSTONE_LAMP)
                {
                    MakeNuclearReactorWork.setLampLight(lower, true);
                }
                Random random = new Random();
                int RNG = random.nextInt(10000);
                if(RNG == 0)
                {
                    solar.getWorld().dropItemNaturally(solar.getLocation(), ItemManager.Battery);
                }
            }
            else
            {
                if(lower.getType() == Material.REDSTONE_LAMP)
                {
                    MakeNuclearReactorWork.setLampLight(lower, false);
                }
            }


            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    SolarWorking(solar.getWorld().getBlockAt(solar.getLocation()));
                }
            }.runTaskLater(plugin,5);
        }
    }
}
