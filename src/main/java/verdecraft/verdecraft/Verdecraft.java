package verdecraft.verdecraft;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import verdecraft.verdecraft.commands.*;
import verdecraft.verdecraft.easter_egg.HFW_Handler;
import verdecraft.verdecraft.easter_egg.EasterEggItems;
import verdecraft.verdecraft.easter_egg.MW_Handler;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.files.TemperatureConfig;
import verdecraft.verdecraft.handlers.*;
import verdecraft.verdecraft.handlers.Temperature.*;
import verdecraft.verdecraft.handlers.blocks.*;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.NuclearReactorAddFuel;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.NuclearReactorDestroy;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.NuclearReactorPlace;
import verdecraft.verdecraft.handlers.blocks.solar_panel.SolarPanelDestroy;
import verdecraft.verdecraft.handlers.blocks.solar_panel.SolarPanelPlace;
import verdecraft.verdecraft.handlers.blocks.uranium_ore.UraniumOreDrop;
import verdecraft.verdecraft.handlers.blocks.uranium_ore.UraniumOreSmelt;
import verdecraft.verdecraft.handlers.items.*;
import verdecraft.verdecraft.handlers.items.mining_helmet.MiningHelmetCrafting;
import verdecraft.verdecraft.handlers.items.electric_lamp.ElectricLampCrafting;
import verdecraft.verdecraft.handlers.items.electric_lamp.ElectricLampPlace;
import verdecraft.verdecraft.handlers.items.electric_lamp.ElectricLampPoweringUp;
import verdecraft.verdecraft.handlers.items.electric_lamp.ElectricLampTurnOnLight;
import verdecraft.verdecraft.handlers.items.mining_helmet.MiningHelmetLight;
import verdecraft.verdecraft.handlers.items.mining_helmet.MiningHelmetPoweringUp;
import verdecraft.verdecraft.handlers.items.nuclear_waste.NuclearWasteDropOrPlace;
import verdecraft.verdecraft.items.BlockManager;
import verdecraft.verdecraft.items.ItemManager;

import java.util.ArrayList;
import java.util.List;

public final class Verdecraft extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        //files-------------------------------------------------------------------------------------------------
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        TemperatureConfig.setup();
        TemperatureConfig.get().addDefault("Temperature",16.0);
        TemperatureConfig.get().addDefault("Modifier",1);
        TemperatureConfig.get().options().copyDefaults(true);
        TemperatureConfig.save();

        BlockLocations.setup();
        List<Location> defaultLocationReactors = new ArrayList<>();
        List<Location> defaultLocationSolars = new ArrayList<>();
        BlockLocations.get().addDefault("NuclearReactors",defaultLocationReactors);
        BlockLocations.get().addDefault("SolarPanels",defaultLocationSolars);
        BlockLocations.get().options().copyDefaults(true);
        BlockLocations.save();

        //commands----------------------------------------------------------------------------------------------
        getCommand("verde_temp_check").setExecutor(new TempCheck());
        getCommand("verde_temp_set").setExecutor(new TempSet());
        getCommand("verde_time_check").setExecutor(new TimeCheck());
        getCommand("verde_items").setExecutor(new VerdeItems());
        getCommand("verde_modi_check").setExecutor(new ModiCheck());
        getCommand("verde_modi_set").setExecutor(new ModiSet());


        //handlers------------------------------------------------------------------------------------------------
        new CombustionMake(this);
        BukkitTask temperatureAnomalies = new TemperatureAnomalies(this).runTaskTimer(this,20*30,20*300);
        BukkitTask TemperatureMassage = new TemperatureMassage(this).runTaskTimer(this,51,51);
        BukkitTask temperatureTimeDecrease = new TemperatureTimedDecrease(this).runTaskTimer(this,20*600,20*600);
        getServer().getPluginManager().registerEvents(new TorchBurnOut(this),this);
        getServer().getPluginManager().registerEvents(new Clock(this),this);
        getServer().getPluginManager().registerEvents(new ElectricLampTurnOnLight(this),this);
        getServer().getPluginManager().registerEvents(new ElectricLampPlace(this),this);
        getServer().getPluginManager().registerEvents(new ElectricLampCrafting(this),this);
        getServer().getPluginManager().registerEvents(new ElectricLampPoweringUp(this),this);
        getServer().getPluginManager().registerEvents(new NuclearReactorPlace(this),this);
        getServer().getPluginManager().registerEvents(new NuclearReactorDestroy(this),this);
        getServer().getPluginManager().registerEvents(new ServerLoad(this),this);
        getServer().getPluginManager().registerEvents(new NuclearReactorAddFuel(this),this);
        getServer().getPluginManager().registerEvents(new CableDestroy(this),this);
        getServer().getPluginManager().registerEvents(new UraniumOreDrop(this),this);
        getServer().getPluginManager().registerEvents(new MiningHelmetCrafting(this),this);
        getServer().getPluginManager().registerEvents(new MiningHelmetPoweringUp(this),this);
        getServer().getPluginManager().registerEvents(new MiningHelmetLight(this),this);
        getServer().getPluginManager().registerEvents(new UraniumOreSmelt(this),this);
        getServer().getPluginManager().registerEvents(new EntityDrop(this),this);
        getServer().getPluginManager().registerEvents(new NuclearWasteDropOrPlace(this),this);
        getServer().getPluginManager().registerEvents(new AnimalsContaminatedByNuclearWastes(this),this);
        getServer().getPluginManager().registerEvents(new SolarPanelPlace(this),this);
        getServer().getPluginManager().registerEvents(new SolarPanelDestroy(this),this);
        getServer().getPluginManager().registerEvents(new TemperatureDecreaseByActions(this),this);
        getServer().getPluginManager().registerEvents(new DisableRedstoneOnLamps(this),this);

        //items------------------------------------------------------------------------------------------------
        ItemManager.init_items();
        BlockManager.init_blocks();

        //easter_egg----------------------------------------------------------------------------------------------
        EasterEggItems.init_items();
        getServer().getPluginManager().registerEvents(new HFW_Handler(this),this);
        getServer().getPluginManager().registerEvents(new MW_Handler(this),this);
    }

}
