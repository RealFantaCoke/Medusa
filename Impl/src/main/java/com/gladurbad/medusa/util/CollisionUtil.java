package com.gladurbad.medusa.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@UtilityClass
public class CollisionUtil {

    private Block getBlockAsync(Location loc) {
        if (loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4))
            return loc.getBlock();
        return null;
    }

    public boolean isOnChosenBlock(Player player, double yLevel, Material... materials) {
        final double expand = 0.31;
        final Location location = player.getLocation();
        for(double x = -expand; x <= expand; x += expand) {
            for(double z = -expand; z <= expand; z+= expand) {
                for(Material material : materials) {
                    if (getBlockAsync(location.clone().add(x, yLevel, z)).getType() == material) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isOnGround(Player player) {
        final double expand = 0.31;
        final Location location = player.getLocation();
        for(double x = -expand; x <= expand; x += expand) {
            for(double z = -expand; z <= expand; z+= expand) {
                if (getBlockAsync(location.clone().add(x, -0.5001, z)).getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOnSolid(Player player) {
        final double expand = 0.31;
        final Location location = player.getLocation();
        for(double x = -expand; x <= expand; x += expand) {
            for(double z = -expand; z <= expand; z+= expand) {
                if (getBlockAsync(location.clone().add(x, -0.5001, z)).getType().isSolid()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isInLiquid(Player player) {
        final double expand = 0.31;
        final Location location = player.getLocation();
        for(double x = -expand; x <= expand; x += expand) {
            for(double z = -expand; z <= expand; z+= expand) {
                if (getBlockAsync(location.clone().add(x, -0.5001, z)).isLiquid()) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isOnGround(Location location, double dropDown) {
        final double expand = 0.31;
        for(double x = -expand; x <= expand; x += expand) {
            for(double z = -expand; z <= expand; z+= expand) {
                if (getBlockAsync(location.clone().add(x, dropDown, z)).getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean blockNearHead(Location location) {
        final double expand = 0.31;
        for(double x = -expand; x <= expand; x += expand) {
            for(double z = -expand; z <= expand; z+= expand) {
                if (getBlockAsync(location.clone().add(x, 2.0, z)).getType() != Material.AIR) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isNearBoat(Player player) {
        for(Entity entity : player.getNearbyEntities(3, 3, 3)) {
            if (entity instanceof Boat) return true;
        }
        return false;
    }

    public boolean isCollidingWithClimbable(Player player) {
        final Location location = player.getLocation();
        final int var1 = MathUtil.floor(location.getX());
        final int var2 = MathUtil.floor(location.getY());
        final int var3 = MathUtil.floor(location.getZ());
        final Block var4 = new Location(location.getWorld(), var1, var2, var3).getBlock();
        return var4.getType() == Material.LADDER || var4.getType() == Material.VINE;
    }
}
