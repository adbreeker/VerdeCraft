package verdecraft.verdecraft.handlers;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.handlers.items.nuclear_waste.MakeNuclearWasteCloud;

public class AnimalsContaminatedByNuclearWastes implements Listener
{
    Verdecraft plugin;
    public AnimalsContaminatedByNuclearWastes(Verdecraft plugin){this.plugin = plugin;}

    @EventHandler
    public void onAnimalsContaminated(EntityPotionEffectEvent event)
    {
        if(event.getEntity() instanceof LivingEntity)
        {
            LivingEntity living = (LivingEntity) event.getEntity();
            int contaminated = 0;
            for(PotionEffect effect : MakeNuclearWasteCloud.NuclearWastePotionEffects())
            {
                if(living.getActivePotionEffects().contains(effect))
                {
                    contaminated++;
                }
            }
            if(contaminated >=3)
            {
                PersistentDataContainer container = event.getEntity().getPersistentDataContainer();
                NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class),"contaminated");
                container.set(key, PersistentDataType.INTEGER,1);
            }
        }
    }
}
