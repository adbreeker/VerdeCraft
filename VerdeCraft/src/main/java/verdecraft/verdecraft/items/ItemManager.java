package verdecraft.verdecraft.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemManager
{
    public static ItemStack ElectricLamp, Battery, Uranium;
    public static void init_items()
    {
        createBattery();
        createElectricLamp();
        createUranium();
    }

    //Electric Lamp -------------------------------------------------------------------------------------------------------------------------
    private static void createElectricLamp()
    {
        ItemStack item = new ItemStack(Material.LANTERN, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Electric Lamp");
        List <String> lore = new ArrayList<>();
        lore.add("§aLamp power: 300/600");
        lore.add("");
        lore.add("§8Lamp serial number:");
        Random random = new Random();
        lore.add("§8"+String.valueOf(random.nextInt()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        ElectricLamp = item;


        //Recipes
        ShapedRecipe new_electric_lamp_recipe = new ShapedRecipe(NamespacedKey.minecraft("electric_lamp"),ElectricLamp);
        new_electric_lamp_recipe.shape("BBB", "RLR", "CFC");
        new_electric_lamp_recipe.setIngredient('B',Battery);
        new_electric_lamp_recipe.setIngredient('R',Material.REDSTONE);
        new_electric_lamp_recipe.setIngredient('L',Material.LANTERN);
        new_electric_lamp_recipe.setIngredient('C',Material.COPPER_INGOT);
        new_electric_lamp_recipe.setIngredient('F',Material.FLINT_AND_STEEL);
        Bukkit.getServer().addRecipe(new_electric_lamp_recipe);

    }

    public static void changeSerialNumber()
    {
        ItemStack item = ElectricLamp;
        ItemMeta meta = item.getItemMeta();
        List <String> lore = new ArrayList<>();
        lore.add("§aLamp power: 300/600");
        lore.add("");
        lore.add("§8Lamp serial number:");
        Random random = new Random();
        lore.add("§8"+String.valueOf(random.nextInt()));
        meta.setLore(lore);
        item.setItemMeta(meta);
        ElectricLamp = item;
    }

    public static void recreateRecipe()
    {
        Bukkit.getServer().removeRecipe(NamespacedKey.minecraft("electric_lamp"));
        ShapedRecipe new_electric_lamp_recipe = new ShapedRecipe(NamespacedKey.minecraft("electric_lamp"),ElectricLamp);
        new_electric_lamp_recipe.shape("BBB", "RLR", "CFC");
        new_electric_lamp_recipe.setIngredient('B',Battery);
        new_electric_lamp_recipe.setIngredient('R',Material.REDSTONE);
        new_electric_lamp_recipe.setIngredient('L',Material.LANTERN);
        new_electric_lamp_recipe.setIngredient('C',Material.COPPER_INGOT);
        new_electric_lamp_recipe.setIngredient('F',Material.FLINT_AND_STEEL);
        Bukkit.getServer().addRecipe(new_electric_lamp_recipe);

    }

    // batteries --------------------------------------------------------------------------------------------------------------------------------------

    private static void createBattery()
    {
        ItemStack item = new ItemStack(Material.RABBIT_FOOT, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Battery");
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        Battery = item;
    }

    // uranium ------------------------------------------------------------------------------------------------------------------------------------------

    private static void createUranium()
    {
        ItemStack item = new ItemStack(Material.SCUTE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aUranium");
        List <String> lore = new ArrayList<>();
        lore.add("§7Watch out, its radioactive");
        lore.add("");
        meta.setLore(lore);
        meta.addEnchant(Enchantment.LUCK, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        Uranium = item;
    }
}
