package verdecraft.verdecraft.handlers.Temperature;

import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

public class TemperatureTimedDecrease extends BukkitRunnable
{
    Verdecraft plugin;
    public TemperatureTimedDecrease(Verdecraft plugin) { this.plugin = plugin;}

    @Override
    public void run()
    {
        if(TemperatureConfig.get().getDouble("Temperature")>=10.1)
        {
            TemperatureConfig.modifyTemp(-0.1);
        }
        else
        {
            TemperatureConfig.setTemp(10.0);
        }

    }
}
