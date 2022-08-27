package verdecraft.verdecraft.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import verdecraft.verdecraft.Verdecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockManager
{
    public static ItemStack NuclearReactor, Cable, Cable6, UraniumOre;
    public static void init_blocks()
    {
        createNuclearReactor();
        createCable();
        createUraniumOre();
    }

    // nuclear reactor ------------------------------------------------------------------------------------------------------------------------------- nuclear reactor

    private static void createNuclearReactor()
    {
        ItemStack item = new ItemStack(Material.RED_NETHER_BRICKS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Nuclear Reactor");
        item.setItemMeta(meta);
        NuclearReactor = item;

        //Recipes
        Bukkit.getServer().removeRecipe(NamespacedKey.minecraft("red_nether_bricks"));
        ShapedRecipe new_nuclear_reactor = new ShapedRecipe(NamespacedKey.minecraft("nuclear_reactor"),NuclearReactor);
        new_nuclear_reactor.shape("IBI", "GCG", "IBI");
        new_nuclear_reactor.setIngredient('B',ItemManager.Battery);
        new_nuclear_reactor.setIngredient('I',Material.IRON_BLOCK);
        new_nuclear_reactor.setIngredient('G',Material.GLASS);
        new_nuclear_reactor.setIngredient('C',Material.COPPER_INGOT);
        Bukkit.getServer().addRecipe(new_nuclear_reactor);
    }

    public static boolean isNuclearReactor(Block potential)
    {
        Block upper = potential.getWorld().getBlockAt(potential.getX(), potential.getY()+1, potential.getZ());
        if(potential.getType().equals(BlockManager.NuclearReactor.getType()) && upper.getState() instanceof TileState)
        {
            TileState state = (TileState) upper.getState();
            PersistentDataContainer container = state.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(Verdecraft.getPlugin(Verdecraft.class), "reactor-fuel");
            if (container.has(key, PersistentDataType.INTEGER))
            {
                return true;
            }
        }
        return false;
    }


    // cable ---------------------------------------------------------------------------------------------------------------------------------------------------- cable

    private static void createCable()
    {
        ItemStack item = new ItemStack(Material.RED_NETHER_BRICK_WALL,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Cable");
        item.setItemMeta(meta);
        Cable = item;

        ItemStack itemx6 = new ItemStack(item);
        itemx6.setAmount(6);
        Cable6 = itemx6;

        //Recipes
        Bukkit.getServer().removeRecipe(NamespacedKey.minecraft("red_nether_brick_wall"));
        ShapedRecipe new_cable = new ShapedRecipe(NamespacedKey.minecraft("cable"),Cable6);
        new_cable.shape("   ", "CRC", "   ");
        new_cable.setIngredient('C',Material.COPPER_INGOT);
        new_cable.setIngredient('R',Material.REDSTONE);
        Bukkit.getServer().addRecipe(new_cable);
    }

    // uranium ore ----------------------------------------------------------------------------------------------------------------------------------------- uranium ore

    private static void createUraniumOre()
    {
        ItemStack item = new ItemStack(Material.DEEPSLATE_COAL_ORE,1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aUranium Ore");
        List <String> lore = new ArrayList<>();
        lore.add("You better not burn it");
        lore.add("");
        meta.setLore(lore);
        item.setItemMeta(meta);
        UraniumOre = item;
    }


}
