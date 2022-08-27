package verdecraft.verdecraft.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class TemperatureConfig
{
    private static File file;
    private static FileConfiguration customFile;

    public static void setup()
    {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Verdecraft").getDataFolder(), "temperatureconfig.yml");
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

    public static void setTemp(double value)
    {
        customFile.set("Temperature",value);
        calculateModifier();
        save();
        reload();
    }
    public static void modifyTemp(double value)
    {
        double newtemp = customFile.getDouble("Temperature") + value;
        double newtemp_rounded = BigDecimal.valueOf(newtemp).setScale(4, RoundingMode.HALF_UP).doubleValue();
        if(newtemp_rounded < 10.0)
        {
            newtemp_rounded = 10.0;
        }
        customFile.set("Temperature",newtemp_rounded);
        calculateModifier();
        save();
        reload();
    }

    public static void setModi(double value)
    {
        customFile.set("Modifier",value);
        save();
        reload();
    }
    public static void modifyModi(double value)
    {
        double newtemp = customFile.getDouble("Modifier") + value;
        double newtemp_rounded = BigDecimal.valueOf(newtemp).setScale(4, RoundingMode.HALF_UP).doubleValue();
        customFile.set("Modifier",newtemp_rounded);
        save();
        reload();
    }

    public static void calculateModifier()
    {
        double temp = customFile.getDouble("Temperature");
        double temp_rounded = BigDecimal.valueOf(temp).setScale(1, RoundingMode.HALF_DOWN).doubleValue();
        double modi_modifier = (temp_rounded - 16)/15;
        double new_modifier = 1 + modi_modifier;
        if(new_modifier < 1)
        {
            new_modifier = 1;
        }
        setModi(new_modifier);
    }

    public static void save()
    {
        double temp = customFile.getDouble("Temperature");
        try
        {
            customFile.save(file);
        }
        catch(IOException ex)
        {
            System.out.println("temperature save exception " + temp);
            setTemp(temp);
        }
    }

    public static void reload()
    {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
