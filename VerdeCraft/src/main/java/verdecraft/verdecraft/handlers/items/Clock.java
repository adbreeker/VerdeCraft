package verdecraft.verdecraft.handlers.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Clock implements Listener
{
    Verdecraft plugin;
    public Clock(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onRightClickWithClock(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR) && (event.getItem().getType().equals(Material.CLOCK)))
        {
            //time
            long time = player.getLocation().getWorld().getTime();
            long time_hours = ((time/1000) + 6)%24;
            long time_minutes = Double.valueOf(time%1000/16.7).longValue();
            if(time_minutes < 10)
            {
                player.sendMessage("Actual time is: " + time_hours + ":" + 0 + time_minutes);
            }
            else
            {
                player.sendMessage("Actual time is: " + time_hours + ":" + time_minutes);
            }


            //temperature
            double temperature = TemperatureConfig.get().getDouble("Temperature");
            double temp_rounded = BigDecimal.valueOf(temperature).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
            player.sendMessage("Actual temperature is: " + Double.toString(temp_rounded) + "°C");
        }
    }
}
