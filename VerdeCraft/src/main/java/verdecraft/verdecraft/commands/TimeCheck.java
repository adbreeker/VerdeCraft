package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TimeCheck implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            long time = player.getLocation().getWorld().getTime();
            player.sendMessage("Current time: " + time + ".");
        }
        return true;
    }
}
