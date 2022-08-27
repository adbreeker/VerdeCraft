package verdecraft.verdecraft.handlers.blocks.solar_panel;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;

public class SolarPanelPlace implements Listener
{
    Verdecraft plugin;
    public SolarPanelPlace(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onSolarPanelPlace(BlockPlaceEvent event)
    {
        if(event.getBlock().getType() == Material.DAYLIGHT_DETECTOR)
        {
            BlockLocations.addSolarPanel(event.getBlock().getLocation());
            new MakeSolarPanelWork(plugin, event.getBlock());
        }
    }
}
