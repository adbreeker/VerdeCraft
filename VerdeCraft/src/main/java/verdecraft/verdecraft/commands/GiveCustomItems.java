package verdecraft.verdecraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.easter_egg.EasterEggItems;
import verdecraft.verdecraft.inventories.CustomItemsHolder;
import verdecraft.verdecraft.items.ItemManager;

public class GiveCustomItems implements CommandExecutor
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
        if (command.getName().equalsIgnoreCase("give_custom_items"))
        {
            CustomItemsHolder holder = new CustomItemsHolder();
            player.openInventory(holder.getInventory());
        }

        return true;
    }
}
