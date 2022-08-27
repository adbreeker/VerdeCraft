package verdecraft.verdecraft.handlers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import verdecraft.verdecraft.Verdecraft;

public class EntityDrop implements Listener
{
    Verdecraft plugin;
    public EntityDrop(Verdecraft plugin){this.plugin = plugin;}

    @EventHandler
    public static void onEntityDrop(EntityDropItemEvent event)
    {
        if(event.getEntityType() == EntityType.TURTLE)
        {
            if(event.getItemDrop().getItemStack().getType() == Material.SCUTE)
            {
                event.getItemDrop().setItemStack(new ItemStack(Material.COD, 1));
            }
        }
    }

    @EventHandler
    public static void onEntityDeath(EntityDeathEvent event)
    {
        if(event.getEntityType() == EntityType.RABBIT)
        {
            for(ItemStack item : event.getDrops())
            {
                if(item.getType() == Material.RABBIT_FOOT)
                {
                    item.setType(Material.CARROT);
                }
            }
        }
        if(event.getEntityType() == EntityType.PHANTOM)
        {
            for(ItemStack item : event.getDrops())
            {
                if(item.getType() == Material.PHANTOM_MEMBRANE)
                {
                    item.setType(Material.FEATHER);
                }
            }
            event.getDrops().add(new ItemStack(Material.INK_SAC,1));
        }
        if(event.getEntity().getPersistentDataContainer() != null)
        {
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"contaminated");
            if(event.getEntity().getPersistentDataContainer().has(key, PersistentDataType.INTEGER))
            {
                event.getDrops().clear();
                event.getDrops().add(new ItemStack(Material.ROTTEN_FLESH,1));
            }
        }
    }
}
