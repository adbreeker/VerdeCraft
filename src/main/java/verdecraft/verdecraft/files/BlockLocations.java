package verdecraft.verdecraft.files;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlockLocations
{
    private static File file;
    private static FileConfiguration customFile;

    public static void setup()
    {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Verdecraft").getDataFolder(), "blocklocations.yml");
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException ex)
            {

            }
        }
        customFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get()
    {
        return customFile;
    }

    public static void addNuclearReactor(Location reactor)
    {
        List<Location> locations = new ArrayList<>();
        locations = (List<Location>) customFile.getList("NuclearReactors");
        if(!locations.contains(reactor))
        {
            locations.add(reactor);
            customFile.set("NuclearReactors", locations);
        }
        save();
        reload();
    }

    public static void deleteNuclearReactor(Location reactor)
    {
        List<Location> locations = new ArrayList<>();
        locations = (List<Location>) customFile.getList("NuclearReactors");
        if(locations.contains(reactor))
        {
            locations.remove(reactor);
        }
        save();
        reload();
    }

    public static void addSolarPanel(Location solar)
    {
        List<Location> locations = new ArrayList<>();
        locations = (List<Location>) customFile.getList("SolarPanels");
        if(!locations.contains(solar))
        {
            locations.add(solar);
            customFile.set("SolarPanels", locations);
        }
        save();
        reload();
    }

    public static void deleteSolarPanel(Location solar)
    {
        List<Location> locations = new ArrayList<>();
        locations = (List<Location>) customFile.getList("SolarPanels");
        if(locations.contains(solar))
        {
            locations.remove(solar);
        }
        save();
        reload();
    }

    public static void save()
    {
        try
        {
            customFile.save(file);
        }
        catch(IOException ex)
        {
            System.out.println("temperature save exception");
        }
    }

    public static void reload()
    {
        customFile = YamlConfiguration.loadConfiguration(file);
    }
}
