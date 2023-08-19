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
package net.arcaniax.gopaint.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class ItemBuilder {

    /**
     * Currently created ItemStack
     */
    private final ItemStack itemStack;

    /**
     * ItemMeta for the ItemStack
     */
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    /**
     *
     * @param name sets display name of the ItemStack
     * @return the instance
     */
    public ItemBuilder setName(String name) {
        this.itemMeta.setDisplayName(name);
        return this;
    }

    /**
     *
     * @param damage set item durability
     * @return the instance
     */
    public ItemBuilder setDurability(short damage) {
        this.itemStack.setDurability(damage);
        return this;
    }

    /**
     * Important: Only available for leather armor
     * @param red color between 0-255
     * @param green color between 0-255
     * @param blue color between 0-255
     * @return the instance
     */
    public ItemBuilder setRGB(int red, int green, int blue) {
        if(!(itemMeta instanceof LeatherArmorMeta)) return this;

        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
        leatherArmorMeta.setColor(Color.fromBGR(red, green, blue));
        return this;
    }

    /**
     *
     * @param amount of the current ItemStack
     * @return the instance
     */
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the lore of specified ItemStack
     * @param list of ItemStack (Lore)
     * @return the instance
     */
    public ItemBuilder setList(String ...list) {
        this.itemMeta.setLore(Arrays.asList(list));
        return this;
    }

    /**
     * Sets the lore of specified ItemStack
     * @param list of ItemStack (Lore)
     * @return the instance
     */
    public ItemBuilder setList(ArrayList<String> list) {
        this.itemMeta.setLore(list);
        return this;
    }

    /**
     * Adds enchantment glint to ItemStack
     * (Adding "Arrow Damage" as enchantment and hides all enchantments)
     * @return the instance
     */
    public ItemBuilder addGlow() {
        this.itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
        this.itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    /**
     * Sets the head skin to specified Base64 value
     * Important: Only available for PLAYER_HEAD
     * @param base64 Base64 skin value
     * @return the instance
     */
    public ItemBuilder setCustomHead(String base64) {
        if(!(itemMeta instanceof SkullMeta)) return this;
        if (base64.isEmpty()) return this;

        SkullMeta headMeta = (SkullMeta) itemMeta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", base64));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException|NoSuchFieldException|SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        return this;
    }

    /**
     * Creates the ItemStack with changed ItemMeta
     * @return changed ItemStack
     */
    public ItemStack create() {
        this.itemStack.setItemMeta(itemMeta);
        return this.itemStack;
    }

}
