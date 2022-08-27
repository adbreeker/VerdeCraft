package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import verdecraft.verdecraft.inventories.CustomItemsHolder;

public class VerdeItems implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("verde_items"))
        {
            CustomItemsHolder holder = new CustomItemsHolder();
            player.openInventory(holder.getInventory());
        }

        return true;
    }
}
