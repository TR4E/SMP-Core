package me.trae.smp.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.annotation.Nullable;

public class UtilLocation {

    public static Location toCenter(final Location loc, final DirectionType directionType) {
        return new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), (directionType != null ? directionType.getYaw() : loc.getYaw()), (directionType != null ? directionType.getPitch() : loc.getPitch()));
    }

    public static boolean isBadLocation(final Location loc) {
        return (String.valueOf(loc.getX()).equalsIgnoreCase("NaN") || String.valueOf(loc.getY()).equalsIgnoreCase("NaN") || String.valueOf(loc.getZ()).equalsIgnoreCase("NaN"));
    }

    public static String locationToFile(final Location loc) {
        if (loc.getWorld() != null) {
            return (loc.getWorld().getName() + ", " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ", " + DirectionType.SOUTH.getYaw() + ", " + DirectionType.SOUTH.getPitch());
        }
        return null;
    }

    public static Location fileToLocation(@Nullable final String string) {
        if (string != null) {
            final String[] s = string.split(", ");
            return new Location(Bukkit.getWorld(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]), Float.parseFloat(s[4]), Float.parseFloat(s[5]));
        }
        return null;
    }

    public enum DirectionType {

        NORTH(-180.0F, 0.0F),
        EAST(-90.0F, 0.0F),
        SOUTH(0.0F, 0.0F),
        WEST(90.0F, 0.0F);

        private final float yaw, pitch;

        DirectionType(final float yaw, final float pitch) {
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public final float getYaw() {
            return yaw;
        }

        public final float getPitch() {
            return pitch;
        }
    }
}