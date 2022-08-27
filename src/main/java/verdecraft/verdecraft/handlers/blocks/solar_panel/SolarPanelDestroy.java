package verdecraft.verdecraft.handlers.blocks.solar_panel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.handlers.items.nuclear_waste.MakeNuclearWasteCloud;
import verdecraft.verdecraft.items.BlockManager;

public class SolarPanelDestroy implements Listener
{
    Verdecraft plugin;
    public SolarPanelDestroy(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onSolarPanelDestroy(BlockBreakEvent event)
    {
        if(event.getBlock().getType() == Material.DAYLIGHT_DETECTOR)
        {
            BlockLocations.deleteSolarPanel(event.getBlock().getLocation());
        }
    }

    @EventHandler
    public void onSolarPanelExplode(BlockExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType() == Material.DAYLIGHT_DETECTOR)
            {
                BlockLocations.deleteSolarPanel(block.getLocation());
            }
        }

    }

    @EventHandler
    public void onSolarPanelExplodeByEntity(EntityExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType() == Material.DAYLIGHT_DETECTOR)
            {
                BlockLocations.deleteSolarPanel(block.getLocation());
            }
        }
    }
}
