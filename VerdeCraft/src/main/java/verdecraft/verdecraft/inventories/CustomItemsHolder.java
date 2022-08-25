package verdecraft.verdecraft.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import verdecraft.verdecraft.easter_egg.EasterEggItems;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

public class CustomItemsHolder implements InventoryHolder
{
    private Inventory inv;

    public CustomItemsHolder()
    {
        inv = Bukkit.createInventory(this, 45,"Verdecraft Items");
        init();
    }

    private void init()
    {
        // classic VerdeCraft items
        inv.addItem(BlockManager.NuclearReactor);
        inv.addItem(BlockManager.Cable);
        inv.addItem(BlockManager.UraniumOre);
        inv.addItem(ItemManager.Uranium);
        inv.addItem(ItemManager.UraniumCell);
        inv.addItem(ItemManager.NuclearWaste);
        inv.addItem(ItemManager.Battery);
        inv.addItem(new ItemStack(Material.CLOCK,1));
        inv.addItem(ItemManager.ElectricLamp);
        ItemManager.changeSerialNumber();
        inv.addItem(ItemManager.MiningHelmet);


        //easter egg items
        inv.setItem(inv.getSize()-1, EasterEggItems.HellFireWand);
        inv.setItem(inv.getSize()-2, EasterEggItems.MidasWill);
    }

    @Override
    public Inventory getInventory()
    {
        return inv;
    }
}
