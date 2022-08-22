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
            if (time >= 0 && time <= 51)
            {
                sendTemperatureMassage(player);
            }
        }
    }

    public void sendTemperatureMassage(Player player)
    {
        double temperature = TemperatureConfig.get().getDouble("Temperature");
        if(temperature < 20)
        {
            player.sendMessage("§2Today temperature is in norm");
        }
        if(temperature >= 20 && temperature < 30)
        {
            player.sendMessage("§aToday temperature is perfect for summer vacation");
        }
        if(temperature >= 30 && temperature < 45)
        {
            player.sendMessage("§cToday temperature is to hot");
        }
        if(temperature >= 45)
        {
            player.sendMessage("§4Today temperature is apocalyptic!");
        }
    }
}
