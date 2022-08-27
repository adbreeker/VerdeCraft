package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.MakeNuclearReactorWork;

public class DisableRedstoneOnLamps implements Listener
{
    Verdecraft plugin;
    public DisableRedstoneOnLamps(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onRedstonePowerLamp(BlockRedstoneEvent event)
    {
        if(event.getBlock().getType() == Material.REDSTONE_LAMP)
        {
            event.setNewCurrent(0);
        }
    }

    @EventHandler
    public void onLampPlacing(BlockPlaceEvent event)
    {
        if(event.getBlock().getType() == Material.REDSTONE_LAMP)
        {
            MakeNuclearReactorWork.setLampLight(event.getBlock(), false);
        }
    }
}
