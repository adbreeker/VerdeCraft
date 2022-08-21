package verdecraft.verdecraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

public class CombustionBlockHandler implements Listener
{
    public CombustionBlockHandler(Verdecraft plugin){Bukkit.getPluginManager().registerEvents(this, plugin);}

    @EventHandler
    public void CombustionMakers(BlockPlaceEvent block)
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
        return;
    }
}
