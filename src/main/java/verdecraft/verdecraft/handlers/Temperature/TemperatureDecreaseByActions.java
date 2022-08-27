package verdecraft.verdecraft.handlers.Temperature;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.StructureGrowEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.util.ArrayList;
import java.util.List;

public class TemperatureDecreaseByActions implements Listener
{
    Verdecraft plugin;
    public TemperatureDecreaseByActions(Verdecraft plugin) {this.plugin = plugin;}

    public static List<Material> getSaplings()
    {
        List<Material> saplings = new ArrayList<>();
        saplings.add(Material.OAK_SAPLING);
        saplings.add(Material.BIRCH_SAPLING);
        saplings.add(Material.SPRUCE_SAPLING);
        saplings.add(Material.JUNGLE_SAPLING);
        saplings.add(Material.ACACIA_SAPLING);
        saplings.add(Material.DARK_OAK_SAPLING);
        return saplings;
    }

    @EventHandler
    public void onGreenBlocksPlacing(BlockPlaceEvent event)
    {
        if(getSaplings().contains(event.getBlock().getType()))
        {
            TemperatureConfig.modifyTemp(-0.003);
        }
    }

    @EventHandler
    public void onTreeGrowing(StructureGrowEvent event)
    {
        TemperatureConfig.modifyTemp(-0.005);
    }

    @EventHandler
    public void onGreenBlocksRemoving(BlockBreakEvent event)
    {
        if(getSaplings().contains(event.getBlock().getType()))
        {
            TemperatureConfig.modifyTemp(+0.003);
        }
    }
}
