package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.BlockManager;

public class CableDestroy implements Listener
{
    Verdecraft plugin;
    public CableDestroy(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onCableDestroy(BlockDropItemEvent event)
    {
        for(Item item : event.getItems())
        {
            if(item.getItemStack().getType().equals(BlockManager.Cable.getType()))
            {
                item.setItemStack(BlockManager.NuclearReactor);
            }
        }
    }
}
