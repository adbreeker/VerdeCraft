package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TempSet implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            try
            {
                Player player = (Player) sender;
                TemperatureConfig.setTemp(Double.parseDouble(args[0]));
                double temperature = TemperatureConfig.get().getDouble("Temperature");
                double temp_rounded = BigDecimal.valueOf(temperature).setScale(3, RoundingMode.HALF_DOWN).doubleValue();
                player.sendMessage("Temperature set to: " + Double.toString(temp_rounded) + "°C");
            }
            catch (NumberFormatException ex)
            {
                Player player = (Player) sender;
                player.sendMessage("Wrong temperature value");
            }
        }
        return true;
    }
}
