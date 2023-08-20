package net.arcaniax.gopaint.utils.vectors;

import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Location;

public class MutableVector3 extends Vector3 {

    private double x;
    private double y;
    private double z;

    public MutableVector3() {
    }

    public MutableVector3(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }
    public MutableVector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public MutableVector3(float x, float y, float z) {
        this(x, y, (double) z);
    }

    public MutableVector3(Vector3 other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    public MutableVector3 addX(double x) {
        this.x += x;
        return this;
    }

    public MutableVector3 addY(double y) {
        this.y += y;
        return this;
    }

    public MutableVector3 addZ(double z) {
        this.z += z;
        return this;
    }

    public MutableVector3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public MutableVector3 add(MutableVector3 vector3) {
        this.x += vector3.getX();
        this.y += vector3.getY();
        this.z += vector3.getZ();
        return this;
    }

    public MutableVector3 subtractX(double x) {
        this.x -= x;
        return this;
    }

    public MutableVector3 subtractY(double y) {
        this.y -= y;
        return this;
    }

    public MutableVector3 subtractZ(double z) {
        this.z -= z;
        return this;
    }

    public MutableVector3 subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public MutableVector3 setX(double x) {
        this.x = x;
        return this;
    }

    public MutableVector3 setY(double y) {
        this.y = y;
        return this;
    }

    public MutableVector3 setZ(double z) {
        this.z = z;
        return this;
    }

    public MutableVector3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public MutableVector3 clone() {
        return new MutableVector3(this.x, this.y, this.z);
    }



    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    public int getChunkX() {
        return (int) this.x >> 4;
    }

    public int getChunkZ() {
        return (int) this.z >> 4;
    }

}
