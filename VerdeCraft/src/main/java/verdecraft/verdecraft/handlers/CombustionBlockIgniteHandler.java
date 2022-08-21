package verdecraft.verdecraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

public class CombustionBlockIgniteHandler implements Listener
{
    public CombustionBlockIgniteHandler(Verdecraft plugin){Bukkit.getPluginManager().registerEvents(this, plugin);}

    @EventHandler
    public void CombustionMakers(BlockIgniteEvent ignite)
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
        return;
    }
}
