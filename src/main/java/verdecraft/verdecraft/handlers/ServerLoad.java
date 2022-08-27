package verdecraft.verdecraft.handlers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.BlockLocations;
import verdecraft.verdecraft.handlers.blocks.solar_panel.MakeSolarPanelWork;
import verdecraft.verdecraft.handlers.blocks.nuclear_reactor.MakeNuclearReactorWork;
import verdecraft.verdecraft.items.BlockManager;

import java.util.ArrayList;
import java.util.List;

public class ServerLoad implements Listener
{
    Verdecraft plugin;
    public ServerLoad(Verdecraft plugin) {this.plugin = plugin;}

    @EventHandler
    public void onServerLoad(ServerLoadEvent event)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                List<Location> check_reactors = new ArrayList<>((List<Location>) BlockLocations.get().getList("NuclearReactors"));
                for(Location NuclearReactor : check_reactors)
                {
                    Block reactor = NuclearReactor.getBlock();
                    Block reactor_chest = reactor.getWorld().getBlockAt(reactor.getX(), reactor.getY()+1, reactor.getZ());
                    if(BlockManager.isNuclearReactor(reactor))
                    {
                        System.out.println("wznawiam prace reaktora");
                        new MakeNuclearReactorWork(plugin,reactor,reactor_chest);
                    }
                    else
                    {
                        System.out.println("usuwam nieistniejacy reaktor");
                        BlockLocations.deleteNuclearReactor(NuclearReactor);
                    }
                }
                List<Location> check_solars = new ArrayList<>((List<Location>) BlockLocations.get().getList("SolarPanels"));
                for(Location SolarPanel : check_solars)
                {
                    Block solar = SolarPanel.getBlock();
                    if(solar.getType() == Material.DAYLIGHT_DETECTOR)
                    {
                        System.out.println("wznawiam prace solara");
                        new MakeSolarPanelWork(plugin, solar);
                    }
                    else
                    {
                        System.out.println("usuwam nieistniejacy solar");
                        BlockLocations.deleteSolarPanel(SolarPanel);
                    }
                }
            }
        }.runTaskLater(plugin,20*10);

    }

}
