package verdecraft.verdecraft.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import verdecraft.verdecraft.Verdecraft;
import verdecraft.verdecraft.files.TemperatureConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TemperatureAnomalies extends BukkitRunnable
{
    Verdecraft plugin;
    public TemperatureAnomalies(Verdecraft plugin) {this.plugin = plugin;}

    @Override
    public void run()
    {
        int seconds_to_next_anomalies = 20*300;
        double modifier = TemperatureConfig.get().getDouble("Modifier");
        int modifier_int = (int)modifier;
        int how_many_times = modifier_int - 1;
        int time_to_deley = seconds_to_next_anomalies/modifier_int;

        for(Player player : Bukkit.getServer().getOnlinePlayers())
        {
            //Grass disappearance if temperature above 30°C
            if(TemperatureConfig.get().getDouble("Temperature")>=30)
            {
                grassDry(player.getLocation());
                for(int i = 1; i<=how_many_times; i++)
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            grassDry(player.getLocation());
                        }
                    }.runTaskLater(plugin,time_to_deley*i);
                }
            }

            //Ice melt if temperature above 35°C
            if(TemperatureConfig.get().getDouble("Temperature")>=35)
            {
                meltBlocks(player.getLocation());
                for(int i = 1; i<=how_many_times; i++)
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            meltBlocks(player.getLocation());
                        }
                    }.runTaskLater(plugin,time_to_deley*i);
                }
            }

            //Fire if temperature above 40°C
            if(TemperatureConfig.get().getDouble("Temperature")>=40)
            {
                randomFire(player.getLocation());
                for(int i = 1; i<=how_many_times; i++)
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            randomFire(player.getLocation());
                        }
                    }.runTaskLater(plugin,time_to_deley*i);
                }


            }

            //Drying water if temperature above 50°C
            if(TemperatureConfig.get().getDouble("Temperature")>=50)
            {
                waterDry(player.getLocation(), modifier);
                for(int i = 1; i<=how_many_times; i++)
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            waterDry(player.getLocation(), modifier);
                        }
                    }.runTaskLater(plugin,time_to_deley*i);
                }
            }

            //Sand instead of dirt if temperature above 60°C
            if(TemperatureConfig.get().getDouble("Temperature")>=60)
            {
                desertMaker(player.getLocation());
                for(int i = 1; i<=how_many_times; i++)
                {
                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            desertMaker(player.getLocation());
                        }
                    }.runTaskLater(plugin,time_to_deley*i);
                }
            }


        }
    }

    private void grassDry(Location player_location)
    {
        List<Block> blocks = new ArrayList<>();
        int radius = 15;
        for(int x = (radius * -1); x<radius; x++)
        {
            for(int y = (radius * -1); y<radius; y++)
            {
                for(int z = (radius * -1); z<radius; z++)
                {
                    Block block_to_change = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,player_location.getBlockY()+y,player_location.getBlockZ()+z);
                    if(block_to_change.getType() == Material.GRASS_BLOCK || block_to_change.getType() == Material.FARMLAND || block_to_change.getType() == Material.PODZOL)
                    {
                        blocks.add(block_to_change);
                    }
                }
            }
        }
        for(int i=0; i<10; i++)
        {
            if(blocks.size()>0)
            {
                Random random = new Random();
                int pick_one = random.ints(0,blocks.size()).findFirst().getAsInt();
                Block chosen = blocks.get(pick_one);
                chosen.setType(Material.DIRT);
                blocks.remove(pick_one);
            }

        }
    }

    public void meltBlocks(Location player_location)
    {
        List<Block> blocks = new ArrayList<>();
        int radius = 15;
        for(int x = (radius * -1); x<radius; x++)
        {
            for(int y = (radius * -1); y<radius; y++)
            {
                for(int z = (radius * -1); z<radius; z++)
                {
                    Block block_to_change = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,player_location.getBlockY()+y,player_location.getBlockZ()+z);
                    if(block_to_change.getType() == Material.ICE || block_to_change.getType() == Material.FROSTED_ICE || block_to_change.getType() == Material.PACKED_ICE || block_to_change.getType() == Material.BLUE_ICE)
                    {
                        blocks.add(block_to_change);
                    }
                }
            }
        }
        for(int i=0; i<4; i++)
        {
            if(blocks.size()>0)
            {
                Random random = new Random();
                int pick_one = random.ints(0,blocks.size()).findFirst().getAsInt();
                Block chosen = blocks.get(pick_one);
                chosen.setType(Material.WATER);
                blocks.remove(pick_one);
            }

        }
    }

    public void randomFire(Location player_location)
    {
        for(int i=0; i<1; i++)
        {
            Random random = new Random();
            int x_modify =random.ints(-15,15).findFirst().getAsInt();
            random = new Random();
            int z_modify =random.ints(-15,15).findFirst().getAsInt();
            Location location = player_location.add(x_modify,0,z_modify);
            for(int h = 319; h>=-60; h--)
            {
                location.setY(h-1);
                if(location.getBlock().getType() != Material.AIR && location.getBlock().getType() != Material.SNOW)
                {
                    location.setY(h);
                    location.getBlock().setType(Material.FIRE);
                    break;
                }
            }
        }

    }

    public void waterDry(Location player_location, double modifier)
    {
        List<Block> blocks = new ArrayList<>();
        int radius = 15;
        for(int x = (radius * -1); x<radius; x++)
        {
            for(int y = (radius * -1); y<radius; y++)
            {
                for(int z = (radius * -1); z<radius; z++)
                {
                    Block block_to_change = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,player_location.getBlockY()+y,player_location.getBlockZ()+z);
                    Block block_above = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,player_location.getBlockY()+y+1,player_location.getBlockZ()+z);
                    if(block_to_change.getType() == Material.WATER && block_above.getType() != Material.WATER)
                    {
                        blocks.add(block_to_change);
                    }
                }
            }
        }
        for(int i=0; i<3+(2*modifier); i++)
        {
            if(blocks.size()>0)
            {
                Random random = new Random();
                int pick_one = random.ints(0,blocks.size()).findFirst().getAsInt();
                Block chosen = blocks.get(pick_one);
                chosen.setType(Material.AIR);
                blocks.remove(pick_one);
            }

        }
    }

    public void desertMaker(Location player_location)
    {
        List<Block> blocks = new ArrayList<>();
        int radius = 15;
        for(int x = (radius * -1); x<radius; x++)
        {
            for(int y = (radius * -1); y<radius; y++)
            {
                for(int z = (radius * -1); z<radius; z++)
                {
                    Block block_to_change = player_location.getWorld().getBlockAt(player_location.getBlockX()+x,player_location.getBlockY()+y,player_location.getBlockZ()+z);
                    if(block_to_change.getType() == Material.DIRT)
                    {
                        blocks.add(block_to_change);
                    }
                }
            }
        }
        for(int i=0; i<20; i++)
        {
            if(blocks.size()>0)
            {
                Random random = new Random();
                int pick_one = random.ints(0,blocks.size()).findFirst().getAsInt();
                Block chosen = blocks.get(pick_one);
                chosen.setType(Material.SAND);
                blocks.remove(pick_one);
            }

        }


    }


}
