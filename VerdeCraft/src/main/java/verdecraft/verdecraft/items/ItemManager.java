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
    public static ItemStack ElectricLamp, Battery, Uranium, UraniumCell, MiningHelmet, NuclearWaste;
    public static void init_items()
    {
        createBattery();
        createElectricLamp();
        createUranium();
        createUraniumCell();
        createMiningHelmet();
        createNuclearWaste();
    }

    // electric Lamp ------------------------------------------------------------------------------------------------------------------------- electric lamp
    private static void createElectricLamp()
    {
        ItemStack item = new ItemStack(Material.LANTERN, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Electric Lamp");
        List <String> lore = new ArrayList<>();
        lore.add("§aLamp power: 1200/2400");
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
        lore.add("§aLamp power: 1200/2400");
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

    public static boolean isElectricLamp(ItemStack probably_lamp)
    {
        if(probably_lamp.getItemMeta() != null)
        {
            if(probably_lamp.getItemMeta().displayName() != null)
            {
                if(probably_lamp.getItemMeta().displayName().equals(ItemManager.ElectricLamp.getItemMeta().displayName()))
                {
                    ItemMeta meta = probably_lamp.getItemMeta();
                    if(meta.getLore() != null)
                    {
                        List<String> lore = meta.getLore();
                        String check = lore.get(2).split(":")[0];
                        if(check.equals("§8Lamp serial number"))
                        {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public static int getElectricLampPower(ItemStack lamp)
    {
        ItemMeta meta = lamp.getItemMeta();
        List<String> lore = meta.getLore();
        int actual_energy = Integer.parseInt(lore.get(0).split(" ")[2].split("/")[0]);
        return actual_energy;
    }

    public static void changeElectricLampPower(ItemStack lamp, int power)
    {
        ItemMeta meta = lamp.getItemMeta();
        List<String> lore = meta.getLore();
        int actual_energy = getElectricLampPower(lamp);

        int new_energy = actual_energy + power;
        if (new_energy > 2400)
        {
            new_energy = 2400;
        }
        if(new_energy <= 0)
        {
            new_energy = 0;
            lore.set(0, "§4Lamp power: " + new_energy + "/2400");
            meta.setLore(lore);
            lamp.setItemMeta(meta);
            return;
        }
        lore.set(0, "§aLamp power: " + new_energy + "/2400");
        meta.setLore(lore);
        lamp.setItemMeta(meta);

    }

    public static void setElectricLampPower(ItemStack lamp, int power)
    {
        ItemMeta meta = lamp.getItemMeta();
        List<String> lore = meta.getLore();
            int new_energy = power;
            if (new_energy > 2400)
            {
                new_energy = 2400;
            }
            if(new_energy <= 0)
            {
                new_energy = 0;
                lore.set(0, "§4Lamp power: " + new_energy + "/2400");
                meta.setLore(lore);
                lamp.setItemMeta(meta);
                return;
            }
            lore.set(0, "§aLamp power: " + new_energy + "/2400");
            meta.setLore(lore);
            lamp.setItemMeta(meta);
    }

    // battery ----------------------------------------------------------------------------------------------------------------------------------------- battery

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

    // uranium ------------------------------------------------------------------------------------------------------------------------------------------ uranium

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

    // uranium cell ----------------------------------------------------------------------------------------------------------------------------------------- uranium cell

    private static void createUraniumCell()
    {
        ItemStack item = new ItemStack(Material.PHANTOM_MEMBRANE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aUranium cell");
        List <String> lore = new ArrayList<>();
        lore.add("§7Nuclear reactor fuel");
        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);
        UraniumCell = item;

        //Recipes
        ShapedRecipe new_uranium_cell_recipe = new ShapedRecipe(NamespacedKey.minecraft("uranium_cell"),UraniumCell);
        new_uranium_cell_recipe.shape("SSS", "GUG", "SSS");
        new_uranium_cell_recipe.setIngredient('G',Material.GLASS);
        new_uranium_cell_recipe.setIngredient('U',Uranium);
        new_uranium_cell_recipe.setIngredient('S',Material.SMOOTH_STONE_SLAB);
        Bukkit.getServer().addRecipe(new_uranium_cell_recipe);
    }

    // mining helmet ---------------------------------------------------------------------------------------------------------------------------------------- mining helmet

    private static void createMiningHelmet()
    {
        ItemStack item = new ItemStack(Material.TURTLE_HELMET, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Mining helmet");
        List <String> lore = new ArrayList<>();
        lore.add("§aHelmet power: 1200/2400");
        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);
        MiningHelmet = item;

        Bukkit.getServer().removeRecipe(NamespacedKey.minecraft("turtle_helmet"));
    }


    public static int getMiningHelmetPower(ItemStack helmet)
    {
        ItemMeta meta = helmet.getItemMeta();
        List<String> lore = meta.getLore();
        int actual_energy = Integer.parseInt(lore.get(0).split(" ")[2].split("/")[0]);
        return actual_energy;
    }

    public static void changeMiningHelmetPower(ItemStack helmet, int power)
    {
        ItemMeta meta = helmet.getItemMeta();
        List<String> lore = meta.getLore();
        int actual_energy = getMiningHelmetPower(helmet);
        int new_energy = actual_energy + power;
        if (new_energy > 2400)
        {
            new_energy = 2400;
        }
        if(new_energy <= 0)
        {
            new_energy = 0;
            lore.set(0, "§4Helmet power: " + new_energy + "/2400");
            meta.setLore(lore);
            helmet.setItemMeta(meta);
            return;
        }
        lore.set(0, "§aHelmet power: " + new_energy + "/2400");
        meta.setLore(lore);
        helmet.setItemMeta(meta);

    }

    public static void setMiningHelmetPower(ItemStack helmet, int power)
    {
        ItemMeta meta = helmet.getItemMeta();
        List<String> lore = meta.getLore();
        int new_energy = power;
        if (new_energy > 2400)
        {
            new_energy = 2400;
        }
        if(new_energy <= 0)
        {
            new_energy = 0;
            lore.set(0, "§4Helmet power: " + new_energy + "/2400");
            meta.setLore(lore);
            helmet.setItemMeta(meta);
            return;
        }
        lore.set(0, "§aHelmet power: " + new_energy + "/2400");
        meta.setLore(lore);
        helmet.setItemMeta(meta);
    }

    // nuclear waste ---------------------------------------------------------------------------------------------------------------------- nuclear waste

    private static void createNuclearWaste()
    {
        ItemStack item = new ItemStack(Material.STRUCTURE_VOID, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aNuclear Waste");
        List <String> lore = new ArrayList<>();
        lore.add("Remember to properly store nuclear wastes");
        lore.add("throwing them somewhere may cause some trouble");
        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);
        NuclearWaste = item;
    }
}
