package verdecraft.verdecraft.handlers.blocks;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.items.BlockManager;

public class NuclearReactorDestroy implements Listener
{
    Verdecraft plugin;
    public NuclearReactorDestroy(Verdecraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNuclearReactorDestroy(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Block upper = block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ());
        Block lower = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ());

        if(block.getType().equals(BlockManager.NuclearReactor.getType()) && upper.getState() instanceof TileState)
        {
            TileState state = (TileState) upper.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
            if(container.has(key,PersistentDataType.INTEGER))
            {
                upper.setType(Material.AIR);
                block.getWorld().createExplosion(block.getLocation(),15,true);
                BlockLocations.deleteNuclearReactor(block.getLocation());
            }
        }

        if(lower.getType().equals(BlockManager.NuclearReactor.getType()) && block.getState() instanceof TileState)
        {
            TileState state = (TileState) block.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
            if(container.has(key,PersistentDataType.INTEGER))
            {
                block.setType(Material.AIR);
                lower.getWorld().createExplosion(lower.getLocation(),15,true);
                BlockLocations.deleteNuclearReactor(lower.getLocation());
            }
        }
    }

    @EventHandler
    public void onNuclearReactorExplode(BlockExplodeEvent event)
    {
        for(Block block : event.blockList())
        {
            if(block.getType().equals(BlockManager.NuclearReactor.getType()))
            {
                Block upper = block.getWorld().getBlockAt(block.getX(), block.getY()+1, block.getZ());
                upper.setType(Material.AIR);
                BlockLocations.deleteNuclearReactor(block.getLocation());

            }

            if(block.getState() instanceof TileState)
            {
                Block lower = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ());
                TileState state = (TileState) block.getState();
                PersistentDataContainer container = state.getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
                if(container.has(key,PersistentDataType.INTEGER))
                {
                    lower.setType(Material.AIR);
                    BlockLocations.deleteNuclearReactor(lower.getLocation());
                }
            }
        }

    }

}
