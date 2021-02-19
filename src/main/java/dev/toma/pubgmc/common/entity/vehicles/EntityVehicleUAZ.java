package dev.toma.pubgmc.common.entity.vehicles;

import dev.toma.pubgmc.common.entity.EntityVehicle;
import dev.toma.pubgmc.config.ConfigPMC;
import dev.toma.pubgmc.init.PMCSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityVehicleUAZ extends EntityVehicle {
    private static final Vec3d[] VECTORS = {new Vec3d(2d, 1.5d, 0), new Vec3d(-1.9d, 0.3, -0.6d)};

    public EntityVehicleUAZ(World world) {
        super(world);
        setSize(2f, 1.5f);
    }

    public EntityVehicleUAZ(World world, double x, double y, double z) {
        this(world);
        setPosition(x, y, z);
        maxHealth = ConfigPMC.common.vehicles.uaz.maxHealth.getAsFloat();
        health = ConfigPMC.common.vehicles.uaz.maxHealth.getAsFloat();
        maxSpeed = ConfigPMC.common.vehicles.uaz.maxSpeed.getAsFloat();
        acceleration = ConfigPMC.common.vehicles.uaz.acceleration.getAsFloat();
        maximalTurningModifier = ConfigPMC.common.vehicles.uaz.maxTurningAngle.getAsFloat();
        turnSpeed = ConfigPMC.common.vehicles.uaz.turningSpeed.getAsFloat();
        fuel = this.generateFuel();
    }

    @Override
    public int getMaximumCapacity() {
        return 4;
    }

    @Override
    public double getMountedYOffset() {
        return 0.75d;
    }

    @Override
    public Vec3d getEnginePosition() {
        return VECTORS[0];
    }

    @Override
    public Vec3d getExhaustPosition() {
        return VECTORS[1];
    }

    @Override
    public SoundEvent vehicleSound() {
        return PMCSounds.uaz;
    }

    @Override
    public String[] getTextureVariants() {
        return null;
    }
}
