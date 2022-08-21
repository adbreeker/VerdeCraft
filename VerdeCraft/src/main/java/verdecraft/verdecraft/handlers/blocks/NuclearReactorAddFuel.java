package verdecraft.verdecraft.handlers.blocks;

import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.MainHand;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

public class NuclearReactorAddFuel implements Listener
{
    Verdecraft plugin;
    public NuclearReactorAddFuel(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onUraniumUse(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            if(event.getHand() != EquipmentSlot.HAND)
            {
                return;
            }
            Block reactor = event.getClickedBlock();
            Block reactor_chest = reactor.getWorld().getBlockAt(reactor.getX(), reactor.getY()+1, reactor.getZ());
            if(reactor.getType().equals(BlockManager.NuclearReactor.getType()) && reactor_chest.getState() instanceof TileState)
            {
                TileState state = (TileState) reactor_chest.getState();
                PersistentDataContainer container = state.getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"reactor-fuel");
                if(container.has(key, PersistentDataType.INTEGER))
                {
                    if(event.getItem() != null)
                    {
                        if(event.getItem().getItemMeta() != null)
                        {
                            if(event.getItem().getItemMeta().equals(ItemManager.Uranium.getItemMeta()))
                            {
                                int reactor_fuel = container.get(key,PersistentDataType.INTEGER);
                                if(reactor_fuel < 5)
                                {
                                    System.out.println("dodaje paliwa");
                                    reactor_fuel += 600;
                                    if(reactor_fuel > 10)
                                    {
                                        reactor_fuel = 10;
                                    }
                                    container.set(key,PersistentDataType.INTEGER,reactor_fuel);
                                    state.update();
                                    event.getItem().setAmount(event.getItem().getAmount()-1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
