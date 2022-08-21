package verdecraft.verdecraft.easter_egg;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EasterEggItems
{
    public static ItemStack HellFireWand;
    public static void init_items()
    {
        createHellFireWand();
    }

    private static void createHellFireWand()
    {
        ItemStack item = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6HellFire Wand");
        List<String> lore = new ArrayList<>();
        lore.add("§4This burning artifact");
        lore.add("§4is able to set world on fire");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.FIRE_ASPECT, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        HellFireWand = item;

        //Recipe
        ShapedRecipe HellFireWandRecipe = new ShapedRecipe(NamespacedKey.minecraft("hellfire_wand"),HellFireWand);
        HellFireWandRecipe.shape(" N ", "SBS", " F ");
        HellFireWandRecipe.setIngredient('N',Material.NETHERITE_INGOT);
        HellFireWandRecipe.setIngredient('S',Material.BLAZE_SPAWN_EGG);
        HellFireWandRecipe.setIngredient('B',Material.BLAZE_ROD);
        HellFireWandRecipe.setIngredient('F',Material.FIRE_CHARGE);
        Bukkit.getServer().addRecipe(HellFireWandRecipe);

    }
}
