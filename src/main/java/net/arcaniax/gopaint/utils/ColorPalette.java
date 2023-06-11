package net.arcaniax.gopaint.utils;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class ColorPalette {

    private static List<Material> brown = new ArrayList<>();
    private static List<Material> red = new ArrayList<>();
    private static List<Material> green = new ArrayList<>();
    private static List<Material> blue = new ArrayList<>();
    private static List<Material> gray = new ArrayList<>();

    static  {
        brown.add(Material.END_STONE_BRICKS);
        brown.add(Material.END_STONE);
        brown.add(Material.SANDSTONE);
        brown.add(Material.SAND);
        brown.add(Material.BIRCH_PLANKS);
        brown.add(Material.STRIPPED_BIRCH_WOOD);
        brown.add(Material.BEEHIVE);
        brown.add(Material.OAK_PLANKS);
        brown.add(Material.STRIPPED_JUNGLE_WOOD);
        brown.add(Material.JUNGLE_PLANKS);
        brown.add(Material.BROWN_MUSHROOM_BLOCK);
        brown.add(Material.PACKED_MUD);
        brown.add(Material.MUD_BRICKS);
        brown.add(Material.ROOTED_DIRT);
        brown.add(Material.DIRT);
        brown.add(Material.COARSE_DIRT);
        brown.add(Material.BROWN_CONCRETE_POWDER);
        brown.add(Material.BROWN_WOOL);
        brown.add(Material.BROWN_CONCRETE);
        brown.add(Material.STRIPPED_SPRUCE_WOOD);
        brown.add(Material.SPRUCE_PLANKS);
        brown.add(Material.BARREL);
        brown.add(Material.OAK_WOOD);
        brown.add(Material.MANGROVE_WOOD);
        brown.add(Material.STRIPPED_DARK_OAK_WOOD);
        brown.add(Material.BROWN_TERRACOTTA);
        brown.add(Material.DARK_OAK_PLANKS);
        brown.add(Material.SPRUCE_WOOD);
        brown.add(Material.SOUL_SAND);
        brown.add(Material.SOUL_SOIL);
        brown.add(Material.GRAY_TERRACOTTA);

        blue.add(Material.LIGHT_BLUE_TERRACOTTA);
        blue.add(Material.ICE);
        blue.add(Material.PACKED_ICE);
        blue.add(Material.BLUE_ICE);
        blue.add(Material.LIGHT_BLUE_WOOL);
        blue.add(Material.LIGHT_BLUE_CONCRETE_POWDER);
        blue.add(Material.DIAMOND_BLOCK);
        blue.add(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        blue.add(Material.LIGHT_BLUE_CONCRETE);
        blue.add(Material.PRISMARINE);
        blue.add(Material.WAXED_OXIDIZED_COPPER);
        blue.add(Material.PRISMARINE_BRICKS);
        blue.add(Material.CYAN_CONCRETE_POWDER);
        blue.add(Material.CYAN_WOOL);
        blue.add(Material.STRIPPED_WARPED_HYPHAE);
        blue.add(Material.WARPED_PLANKS);
        blue.add(Material.DARK_PRISMARINE);
        blue.add(Material.CYAN_CONCRETE);
        blue.add(Material.TUBE_CORAL_BLOCK);
        blue.add(Material.BLUE_CONCRETE_POWDER);
        blue.add(Material.BLUE_WOOL);
        blue.add(Material.LAPIS_BLOCK);
        blue.add(Material.BLUE_GLAZED_TERRACOTTA);
        blue.add(Material.BLUE_CONCRETE);
        blue.add(Material.BLUE_TERRACOTTA);

        red.add(Material.RED_CONCRETE_POWDER);
        red.add(Material.RED_GLAZED_TERRACOTTA);
        red.add(Material.REDSTONE_BLOCK);
        red.add(Material.RED_MUSHROOM_BLOCK);
        red.add(Material.FIRE_CORAL_BLOCK);
        red.add(Material.RED_WOOL);
        red.add(Material.RED_CONCRETE);
        red.add(Material.CRIMSON_NYLIUM);
        red.add(Material.NETHER_WART_BLOCK);
        red.add(Material.CRIMSON_HYPHAE);
        red.add(Material.BLACK_GLAZED_TERRACOTTA);
        red.add(Material.RED_NETHER_BRICKS);
        red.add(Material.NETHERRACK);
        red.add(Material.MANGROVE_PLANKS);
        red.add(Material.STRIPPED_MANGROVE_WOOD);
        red.add(Material.RED_TERRACOTTA);
        red.add(Material.BRICKS);
        red.add(Material.GRANITE);
        red.add(Material.POLISHED_GRANITE);

        green.add(Material.SLIME_BLOCK);
        green.add(Material.EMERALD_BLOCK);
        green.add(Material.LIME_GLAZED_TERRACOTTA);
        green.add(Material.LIME_CONCRETE_POWDER);
        green.add(Material.LIME_WOOL);
        green.add(Material.LIME_CONCRETE);
        green.add(Material.MELON);
        green.add(Material.GRASS_BLOCK);
        green.add(Material.GREEN_CONCRETE_POWDER);
        green.add(Material.MOSS_BLOCK);
        green.add(Material.GREEN_WOOL);
        green.add(Material.GREEN_GLAZED_TERRACOTTA);
        green.add(Material.GREEN_CONCRETE);
        green.add(Material.GREEN_TERRACOTTA);
        green.add(Material.DRIED_KELP_BLOCK);

        gray.add(Material.SNOW_BLOCK);
        gray.add(Material.WHITE_WOOL);
        gray.add(Material.SMOOTH_QUARTZ);
        gray.add(Material.QUARTZ_BRICKS);
        gray.add(Material.WHITE_CONCRETE_POWDER);
        gray.add(Material.CALCITE);
        gray.add(Material.IRON_BLOCK);
        gray.add(Material.BIRCH_WOOD);
        gray.add(Material.POLISHED_DIORITE);
        gray.add(Material.DIORITE);
        gray.add(Material.CLAY);
        gray.add(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        gray.add(Material.LIGHT_GRAY_CONCRETE_POWDER);
        gray.add(Material.LIGHT_GRAY_WOOL);
        gray.add(Material.DEAD_FIRE_CORAL_BLOCK);
        gray.add(Material.DEAD_TUBE_CORAL_BLOCK);
        gray.add(Material.DEAD_HORN_CORAL_BLOCK);
        gray.add(Material.DEAD_BUBBLE_CORAL_BLOCK);
        gray.add(Material.DEAD_BRAIN_CORAL_BLOCK);
        gray.add(Material.GRAVEL);
        gray.add(Material.ANDESITE);
        gray.add(Material.STONE);
        gray.add(Material.IRON_ORE);
        gray.add(Material.COPPER_ORE);
        gray.add(Material.POLISHED_ANDESITE);
        gray.add(Material.COBBLESTONE);
        gray.add(Material.LIGHT_GRAY_CONCRETE);
        gray.add(Material.STONE_BRICKS);
        gray.add(Material.CHISELED_STONE_BRICKS);
        gray.add(Material.CRACKED_STONE_BRICKS);
        gray.add(Material.MOSSY_STONE_BRICKS);
        gray.add(Material.TUFF);
        gray.add(Material.COAL_ORE);
        gray.add(Material.DEEPSLATE_IRON_ORE);
        gray.add(Material.ACACIA_WOOD);
        gray.add(Material.DEEPSLATE_COPPER_ORE);
        gray.add(Material.CYAN_TERRACOTTA);
        gray.add(Material.GRAY_GLAZED_TERRACOTTA);
        gray.add(Material.BEDROCK);
        gray.add(Material.GRAY_CONCRETE_POWDER);
        gray.add(Material.COBBLED_DEEPSLATE);
        gray.add(Material.DEEPSLATE_COAL_ORE);
        gray.add(Material.SMOOTH_BASALT);
        gray.add(Material.POLISHED_DEEPSLATE);
        gray.add(Material.DEEPSLATE_BRICKS);
        gray.add(Material.GRAY_WOOL);
        gray.add(Material.CRACKED_DEEPSLATE_BRICKS);
        gray.add(Material.NETHERITE_BLOCK);
        gray.add(Material.MUD);
        gray.add(Material.GRAY_CONCRETE);
        gray.add(Material.DEEPSLATE_TILES);
        gray.add(Material.CHISELED_DEEPSLATE);
        gray.add(Material.CRACKED_DEEPSLATE_TILES);
        gray.add(Material.POLISHED_BLACKSTONE);
        gray.add(Material.POLISHED_BLACKSTONE_BRICKS);
        gray.add(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS);
    }

    public static List<Material> getMaterialList(Material material) {
        if(red.contains(material)) return red;
        if(green.contains(material)) return green;
        if(blue.contains(material)) return blue;
        if(gray.contains(material)) return gray;
        if(brown.contains(material)) return brown;
        return null;
    }

    public List<Material> getBlue() {
        return blue;
    }

    public List<Material> getBrown() {
        return brown;
    }

    public List<Material> getGray() {
        return gray;
    }

    public List<Material> getGreen() {
        return green;
    }

    public List<Material> getRed() {
        return red;
    }

}
