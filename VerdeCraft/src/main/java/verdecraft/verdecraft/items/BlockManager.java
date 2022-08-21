package verdecraft.verdecraft.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockManager
{
    public static ItemStack NuclearReactor;
    public static void init_blocks()
    {
        createNuclearReactor();
    }

    private static void createNuclearReactor()
    {
        ItemStack item = new ItemStack(Material.DEEPSLATE_COAL_ORE, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Nuclear Reactor");
        item.setItemMeta(meta);
        NuclearReactor = item;

        //Recipes
        ShapedRecipe new_nuclear_reactor = new ShapedRecipe(NamespacedKey.minecraft("nuclear_reactor"),NuclearReactor);
        new_nuclear_reactor.shape("IBI", "GCG", "IBI");
        new_nuclear_reactor.setIngredient('B',ItemManager.Battery);
        new_nuclear_reactor.setIngredient('I',Material.IRON_BLOCK);
        new_nuclear_reactor.setIngredient('G',Material.GLASS);
        new_nuclear_reactor.setIngredient('C',Material.COPPER_INGOT);
        Bukkit.getServer().addRecipe(new_nuclear_reactor);
    }

}
