package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ModiCheck implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            double modifier = TemperatureConfig.get().getDouble("Modifier");
            double modifier_rounded = BigDecimal.valueOf(modifier).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
            player.sendMessage("Actual modifier is: " + Double.toString(modifier_rounded));
        }
        return true;
    }
}