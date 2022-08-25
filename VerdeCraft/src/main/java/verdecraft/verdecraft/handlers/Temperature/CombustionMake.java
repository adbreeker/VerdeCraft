package verdecraft.verdecraft.handlers.Temperature;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

public class CombustionMake implements Listener
{
    public CombustionMake(Verdecraft plugin){Bukkit.getPluginManager().registerEvents(this, plugin);}

    @EventHandler
    public void OnBlockPlace(BlockPlaceEvent block)
    {

        Material m = block.getBlock().getType();
        if (m == Material.TORCH || m == Material.WALL_TORCH || m == Material.SOUL_TORCH || m == Material.SOUL_WALL_TORCH || m == Material.LANTERN || m == Material.SOUL_LANTERN)
        {
            TemperatureConfig.modifyTemp(0.001);
        }
        if (m == Material.CAMPFIRE || m == Material.SOUL_CAMPFIRE || m == Material.GLOWSTONE)
        {
            TemperatureConfig.modifyTemp(0.01);
        }
        if (m == Material.MAGMA_BLOCK)
        {
            TemperatureConfig.modifyTemp(0.05);
        }
    }

    @EventHandler
    public void OnBlockIgnite(BlockIgniteEvent ignite)
    {
        if(ignite.getCause().equals(BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) || ignite.getCause().equals(BlockIgniteEvent.IgniteCause.FIREBALL))
        {
            TemperatureConfig.modifyTemp(0.01);
        }
        if(ignite.getCause().equals(BlockIgniteEvent.IgniteCause.EXPLOSION))
        {
            TemperatureConfig.modifyTemp(0.005);
        }
        if(ignite.getCause().equals(BlockIgniteEvent.IgniteCause.SPREAD))
        {
            TemperatureConfig.modifyTemp(0.00025);
        }
    }

    @EventHandler
    public void OnFurnaceBurn(FurnaceBurnEvent event)
    {
        if(event.getFuel().getType() == Material.COAL || event.getFuel().getType() == Material.CHARCOAL)
        {
            TemperatureConfig.modifyTemp(0.02);
            return;
        }
        if(event.getFuel().getType() == Material.COAL_BLOCK || event.getFuel().getType() == Material.LAVA_BUCKET || event.getFuel().getType() == Material.BLAZE_ROD)
        {
            TemperatureConfig.modifyTemp(0.1);
            return;
        }
        TemperatureConfig.modifyTemp(0.01);
    }
}
