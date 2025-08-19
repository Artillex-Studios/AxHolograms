package com.artillexstudios.axholograms.api.serializer;

import com.artillexstudios.axapi.serializers.Serializer;
import com.artillexstudios.axapi.utils.Location;
import com.artillexstudios.axapi.utils.World;

import java.util.regex.Pattern;

public class LocationSerializer implements Serializer<Location, String> {
    public static final LocationSerializer INSTANCE = new LocationSerializer();
    private static final Pattern SPLIT_PATTERN = Pattern.compile(";");

    @Override
    public String serialize(Location object) {
        return object.getWorld().getName() + ";" + object.getX() + ";" + object.getY() + ";" + object.getZ() + ";" + object.getYaw() + ";" + object.getPitch();
    }

    @Override
    public Location deserialize(String value) {
        String[] split = SPLIT_PATTERN.split(value);
        return new Location(new World(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]), Float.parseFloat(split[5]));
    }
}
