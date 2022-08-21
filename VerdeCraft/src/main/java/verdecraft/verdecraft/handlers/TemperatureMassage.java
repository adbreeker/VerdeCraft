package verdecraft.verdecraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TemperatureMassage extends BukkitRunnable
{
    public TemperatureMassage(Verdecraft plugin) { this.plugin = plugin;}
    Verdecraft plugin;

    @Override
    public void run()
    {
        for(Player player : Bukkit.getServer().getOnlinePlayers())
        {
            long time = player.getWorld().getTime();
            if (time >= 0 && time < 51)
            {
                sendMassage(player);
            }
        }
    }

    public void sendMassage(Player player)
    {
        double temperature = TemperatureConfig.get().getDouble("Temperature");
        double temp_rounded = BigDecimal.valueOf(temperature).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
        player.sendMessage("Actual temperature is: " + Double.toString(temp_rounded) + "°C");
    }
}
