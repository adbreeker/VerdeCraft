package verdecraft.verdecraft.handlers.blocks.nuclear_reactor;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.MakeNuclearReactorWork;
import verdecraft.verdecraft.items.BlockManager;

public class NuclearReactorPlace implements Listener
{
    Verdecraft plugin;
    public NuclearReactorPlace(Verdecraft plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onNuclearReactorPlace(BlockPlaceEvent event)
    {
       if(event.getItemInHand().getItemMeta().equals(BlockManager.NuclearReactor.getItemMeta()))
       {
            BlockLocations.addNuclearReactor(event.getBlock().getLocation());
            Block upperblock = event.getBlock().getLocation().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(),event.getBlock().getLocation().getBlockY()+1,event.getBlock().getLocation().getBlockZ());
            upperblock.setType(Material.TRAPPED_CHEST);
            TileState state = (TileState) upperblock.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");

            container.set(key,PersistentDataType.INTEGER,0);
            state.update();

            new MakeNuclearReactorWork(plugin,event.getBlock(),upperblock);
       }
    }

}
