package austeretony.plantbiomes.common.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class WorldPosition {

    private double x, y, z;

    private MutableBlockPos blockPos = new MutableBlockPos();

    public WorldPosition() {}

    public WorldPosition(WorldPosition position) {
        this.x = position.x;
        this.y = position.y;
        this.z = position.z;
    }

    public WorldPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public WorldPosition setPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public WorldPosition setPosition(BlockPos blockPos) {
        this.x = blockPos.getX();
        this.y = blockPos.getY();
        this.z = blockPos.getZ();
        return this;
    }

    public MutableBlockPos toBlockPos() {
        return this.blockPos.setPos(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object other) {
        WorldPosition otherPos = (WorldPosition) other;
        return otherPos.x == this.x && otherPos.y == this.y && otherPos.z == this.z;
    }
}
