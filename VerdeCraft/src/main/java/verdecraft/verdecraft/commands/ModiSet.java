package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ModiSet implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            try
            {
                Player player = (Player) sender;
                TemperatureConfig.setModi(Double.parseDouble(args[0]));
                double modifier = TemperatureConfig.get().getDouble("Modifier");
                double modifier_rounded = BigDecimal.valueOf(modifier).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
                player.sendMessage("Modifier set to: " + Double.toString(modifier_rounded));
            }
            catch (NumberFormatException ex)
            {
                Player player = (Player) sender;
                player.sendMessage("Wrong modifier value");
            }
        }
        return true;
    }
}