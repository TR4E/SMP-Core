package me.trae.smp.utility;

import org.bukkit.Location;

public class UtilLocation {

    public static Location toCenter(final Location loc, final DirectionType directionType) {
        return new Location(loc.getWorld(), Double.parseDouble(loc.getBlockX() + ".5"), loc.getBlockY(), Double.parseDouble(loc.getBlockZ() + ".5"), directionType.getYaw(), directionType.getPitch());
    }

    public static boolean isBadLocation(final Location loc) {
        return (String.valueOf(loc.getX()).equalsIgnoreCase("NaN") || String.valueOf(loc.getY()).equalsIgnoreCase("NaN") || String.valueOf(loc.getZ()).equalsIgnoreCase("NaN"));
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