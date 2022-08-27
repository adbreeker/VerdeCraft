package verdecraft.verdecraft.handlers.items.nuclear_waste;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;

import java.util.ArrayList;
import java.util.List;

public class MakeNuclearWasteCloud
{
    Verdecraft plugin;
    public MakeNuclearWasteCloud(Verdecraft plugin, Location loc, int radius, int radiation)
    {
        this.plugin = plugin;
        makeCloud(loc, radius, radiation);
    }

    public static List<PotionEffect> NuclearWastePotionEffects()
    {
        List<PotionEffect> effects = new ArrayList<>();
        effects.add(new PotionEffect(PotionEffectType.HUNGER, 20*30, 3, true, true));
        effects.add(new PotionEffect(PotionEffectType.CONFUSION, 20*10, 0, true, true));
        effects.add(new PotionEffect(PotionEffectType.GLOWING, 20*30, 0, true, true));
        effects.add(new PotionEffect(PotionEffectType.POISON, 20*5, 6, true, true));

        return  effects;
    }

    public void makeCloud(Location l, float r, int radiation)
    {
        final float radius = r;
        Location location = l;
        AreaEffectCloud cloud = (AreaEffectCloud) location.getWorld().spawnEntity(location, EntityType.AREA_EFFECT_CLOUD);
        cloud.setColor(Color.GREEN);
        cloud.setDuration(30*20);
        for(PotionEffect effect : NuclearWastePotionEffects())
        {
            cloud.addCustomEffect(effect, true);
        }
        if(radiation > 0)
        {
            new BukkitRunnable()
            {
                float actualradius = cloud.getRadius();
                public void run()
                {
                    for(Entity entity : l.getNearbyEntities(radiation,radiation/2,radiation))
                    {
                        if(entity instanceof LivingEntity)
                        {
                            LivingEntity toContaminate = (LivingEntity) entity;
                            for(PotionEffect effect : NuclearWastePotionEffects())
                            {
                                toContaminate.addPotionEffect(effect);
                            }
                        }
                    }
                }
            }.runTaskLater(plugin,20*5);
        }
        new BukkitRunnable()
        {
            float actualradius = cloud.getRadius();
            public void run()
            {
                if(actualradius >= radius)
                {
                    cancel();
                }
                else
                {
                    actualradius+= radius/600;
                    cloud.setRadius(actualradius);
                }
            }
        }.runTaskTimer(plugin,5,1);
    }
}


