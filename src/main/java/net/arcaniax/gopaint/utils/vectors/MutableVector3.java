/*
 * goPaint is designed to simplify painting inside of Minecraft.
 * Copyright (C) Arcaniax-Development
 * Copyright (C) Arcaniax team and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.arcaniax.gopaint.utils.vectors;

import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Location;

/**
 * A mutable vector in three-dimensional space.
 */
public class MutableVector3 extends Vector3 {

    private double x;
    private double y;
    private double z;

    /**
     * Default constructor.
     */
    public MutableVector3() {
    }

    /**
     * Constructs a mutable vector from a Bukkit Location.
     *
     * @param location The Bukkit Location to create the vector from.
     */
    public MutableVector3(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    /**
     * Constructs a mutable vector with the specified coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public MutableVector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a mutable vector from floats.
     *
     * @param x The x-coordinate as a float.
     * @param y The y-coordinate as a float.
     * @param z The z-coordinate as a float.
     */
    public MutableVector3(float x, float y, float z) {
        this(x, y, (double) z);
    }

    /**
     * Constructs a mutable vector from another vector.
     *
     * @param other The vector to copy from.
     */
    public MutableVector3(Vector3 other) {
        this(other.getX(), other.getY(), other.getZ());
    }

    /**
     * Adds a value to the x-coordinate of the vector.
     *
     * @param x The value to add.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 addX(double x) {
        this.x += x;
        return this;
    }

    /**
     * Adds a value to the y-coordinate of the vector.
     *
     * @param y The value to add.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 addY(double y) {
        this.y += y;
        return this;
    }

    /**
     * Adds a value to the z-coordinate of the vector.
     *
     * @param z The value to add.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 addZ(double z) {
        this.z += z;
        return this;
    }

    /**
     * Adds values to all coordinates of the vector.
     *
     * @param x The value to add to the x-coordinate.
     * @param y The value to add to the y-coordinate.
     * @param z The value to add to the z-coordinate.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * Adds another mutable vector to this vector.
     *
     * @param vector3 The vector to add.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 add(MutableVector3 vector3) {
        this.x += vector3.getX();
        this.y += vector3.getY();
        this.z += vector3.getZ();
        return this;
    }

    /**
     * Subtracts a value from the x-coordinate of the vector.
     *
     * @param x The value to subtract.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 subtractX(double x) {
        this.x -= x;
        return this;
    }

    /**
     * Subtracts a value from the y-coordinate of the vector.
     *
     * @param y The value to subtract.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 subtractY(double y) {
        this.y -= y;
        return this;
    }

    /**
     * Subtracts a value from the z-coordinate of the vector.
     *
     * @param z The value to subtract.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 subtractZ(double z) {
        this.z -= z;
        return this;
    }

    /**
     * Subtracts values from all coordinates of the vector.
     *
     * @param x The value to subtract from the x-coordinate.
     * @param y The value to subtract from the y-coordinate.
     * @param z The value to subtract from the z-coordinate.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * Sets the x-coordinate of the vector.
     *
     * @param x The new value for the x-coordinate.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 setX(double x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the y-coordinate of the vector.
     *
     * @param y The new value for the y-coordinate.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 setY(double y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the z-coordinate of the vector.
     *
     * @param z The new value for the z-coordinate.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 setZ(double z) {
        this.z = z;
        return this;
    }

    /**
     * Sets all coordinates of the vector.
     *
     * @param x The new value for the x-coordinate.
     * @param y The new value for the y-coordinate.
     * @param z The new value for the z-coordinate.
     * @return The modified vector for method chaining.
     */
    public MutableVector3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Creates a clone of this mutable vector.
     *
     * @return A new mutable vector with the same coordinates as this one.
     */
    public MutableVector3 clone() {
        return new MutableVector3(this.x, this.y, this.z);
    }

    /**
     * Gets the x-coordinate of the vector.
     *
     * @return The x-coordinate.
     */
    @Override
    public double getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the vector.
     *
     * @return The y-coordinate.
     */
    @Override
    public double getY() {
        return y;
    }

    /**
     * Gets the z-coordinate of the vector.
     *
     * @return The z-coordinate.
     */
    @Override
    public double getZ() {
        return z;
    }

    /**
     * Gets the chunk x-coordinate of the vector.
     *
     * @return The chunk x-coordinate.
     */
    public int getChunkX() {
        return (int) this.x >> 4;
    }

    /**
     * Gets the chunk z-coordinate of the vector.
     *
     * @return The chunk z-coordinate.
     */
    public int getChunkZ() {
        return (int) this.z >> 4;
    }
}
