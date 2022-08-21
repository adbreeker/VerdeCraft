package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import verdecraft.verdecraft.files.TemperatureConfig;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TempCheck implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            double temperature = TemperatureConfig.get().getDouble("Temperature");
            double temp_rounded = BigDecimal.valueOf(temperature).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
            player.sendMessage("Actual temperature is: " + Double.toString(temp_rounded) + "°C");
        }
        return true;
    }
}
