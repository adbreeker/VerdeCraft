package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.BlockManager;

public class CableDestroy implements Listener
{
    Verdecraft plugin;
    public CableDestroy(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onCableItemDrop(BlockDropItemEvent event)
    {
        for(Item item : event.getItems())
        {
            if(item.getItemStack().getType().equals(BlockManager.Cable.getType()))
            {
                item.setItemStack(BlockManager.NuclearReactor);
            }
        }
    }

    @EventHandler
    public void onCableDestroy(BlockDamageEvent event)
    {
        if(event.getBlock().getType().equals(BlockManager.Cable.getType()))
        {
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), BlockManager.Cable);
        }
    }

    @EventHandler
    public void onCableExplode(BlockExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.Cable.getType()))
            {
                block.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onCableExplodeByEntity(BlockExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.Cable.getType()))
            {
                block.setType(Material.AIR);
            }
        }
    }
}
