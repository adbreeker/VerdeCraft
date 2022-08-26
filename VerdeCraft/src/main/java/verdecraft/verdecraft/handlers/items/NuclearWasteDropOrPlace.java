package verdecraft.verdecraft.handlers.items;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;
import verdecraft.verdecraft.items.ItemManager;

public class NuclearWasteDropOrPlace implements Listener
{
    Verdecraft plugin;
    public NuclearWasteDropOrPlace(Verdecraft plugin){this.plugin = plugin;}

    public static PotionMeta NuclearWastePotionMeta()
    {
        ItemStack potion = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) potion.getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 20*30, 3, true, true), true);
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*30, 0, true, true), true);
        potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 20*5, 6, true, true), true);
        potionMeta.setColor(Color.GREEN);

        return  potionMeta;
    }

    @EventHandler
    public  void onNuclearWasteDrop(PlayerDropItemEvent event)
    {
        if(event.getItemDrop().getItemStack().equals(ItemManager.NuclearWaste))
        {
            Item item = event.getItemDrop();
            BukkitTask task = new BukkitRunnable()
            {
                int hight = event.getItemDrop().getLocation().getBlockY();
                @Override
                public void run()
                {
                    if(item.getLocation().getBlockY() == hight)
                    {
                        ItemStack potion = new ItemStack(Material.LINGERING_POTION);
                        potion.setItemMeta(NuclearWastePotionMeta());
                        ThrownPotion thrownPotion = event.getPlayer().launchProjectile(LingeringPotion.class);
                        thrownPotion.setItem(potion);
                        thrownPotion.spawnAt(item.getLocation());
                        event.getPlayer().sendMessage("Throwing nuclear wastes may cause some serious temperature troubles");
                        TemperatureConfig.modifyTemp(0.1);
                        cancel();
                    }
                    else
                    {
                        hight = item.getLocation().getBlockY();
                    }
                }
            }.runTaskTimer(plugin, 10,5);
        }
    }

    @EventHandler
    public  void onNuclearWastePlace(BlockPlaceEvent event)
    {
        ItemStack item = event.getItemInHand();
        item.setAmount(1);
        if(item.equals(ItemManager.NuclearWaste))
        {
            event.getBlock().setType(Material.AIR);
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), ItemManager.NuclearWaste);
            ItemStack potion = new ItemStack(Material.LINGERING_POTION);
            potion.setItemMeta(NuclearWastePotionMeta());
            ThrownPotion thrownPotion = event.getPlayer().launchProjectile(LingeringPotion.class);
            thrownPotion.setItem(potion);
            thrownPotion.spawnAt(event.getBlock().getLocation());
            event.getPlayer().sendMessage("Throwing nuclear wastes may cause some serious temperature troubles");
            TemperatureConfig.modifyTemp(0.1);
        }
    }

    @EventHandler
    public void onNuclearWasteCloudAppear(EntitySpawnEvent event)
    {
        if(event.getEntityType() == EntityType.AREA_EFFECT_CLOUD)
        {
            AreaEffectCloud cloud = (AreaEffectCloud) event.getEntity();
            if(cloud.getCustomEffects().equals(NuclearWastePotionMeta().getCustomEffects()))
            {
                ((AreaEffectCloud) event.getEntity()).setDuration(30*20);
                new BukkitRunnable()
                {
                    float radius = cloud.getRadius();
                    public void run()
                    {
                        if(radius >= 25)
                        {
                            cancel();
                            new BukkitRunnable()
                            {
                                public void run()
                                {
                                    if(radius <= 7)
                                    {
                                        cancel();
                                    }
                                    else
                                    {
                                        radius-= 0.1;
                                        ((AreaEffectCloud) event.getEntity()).setRadius(radius);
                                    }
                                }
                            }.runTaskTimer(plugin,20*5,1);
                        }
                        else
                        {
                            radius+= 0.1;
                            ((AreaEffectCloud) event.getEntity()).setRadius(radius);
                        }
                    }
                }.runTaskTimer(plugin,5,1);
            }
        }
    }
}
