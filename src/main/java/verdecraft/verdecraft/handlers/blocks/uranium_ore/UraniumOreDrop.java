package verdecraft.verdecraft.handlers.blocks.uranium_ore;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

import java.util.Map;

public class UraniumOreDrop implements Listener
{
    Verdecraft plugin;
    public UraniumOreDrop(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    void DropUranium(BlockBreakEvent event)
    {
        if(event.getBlock().getType().equals(BlockManager.UraniumOre.getType()))
        {
            ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
            boolean silktouch = false;
            if(tool.getEnchantments() != null)
            {
                Map<Enchantment, Integer> enchanments = tool.getEnchantments();
                if(enchanments.containsKey(Enchantment.SILK_TOUCH))
                {
                    silktouch = true;
                }
            }
            if(silktouch)
            {
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),BlockManager.UraniumOre);
            }
            else
            {
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),ItemManager.Uranium);
            }
        }
    }

    @EventHandler
    void UraniumExplodeByEntity(EntityExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.UraniumOre.getType()))
            {
                block.setType(Material.AIR);
                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        block.getLocation().createExplosion(5,true);
                    }
                }.runTaskLater(plugin,3);
            }
        }
    }

    @EventHandler
    void UraniumExplode(BlockExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.UraniumOre.getType()))
            {
                block.setType(Material.AIR);
            }
        }
    }
}
