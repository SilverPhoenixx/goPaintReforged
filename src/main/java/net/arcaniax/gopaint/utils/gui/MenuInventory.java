package net.arcaniax.gopaint.utils.gui;

import com.sk89q.worldedit.world.biome.BiomeType;
import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.brush.settings.AbstractSetting;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.blocks.DisabledBlocks;
import net.arcaniax.gopaint.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class MenuInventory extends GoPaintInventory {

    @Override
    public Inventory createInventory(PlayerBrush playerBrush) {

        this.inventory = Bukkit.createInventory(this, 54, "§6goPaint Menu");

        // FILLER
        for (int slot = 0; slot < 54; slot++) {
            this.inventory.setItem(
                    slot,
                    new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                            .setName("§c")
                            .create()
            );
        }

        /*
         Glass Pane for goPaint is enabled/disabled
         */
        Material material = playerBrush.isEnabled() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                1,
                new ItemBuilder(material)
                        .setName("§7")
                           .create()
        );

        /*
        goPaint Item (Feather)
        */
        String goPaintStatus = playerBrush.isEnabled() ? "§a§lEnabled" : "§c§lDisabled";
        this.inventory.setItem(
                10,
                new ItemBuilder(Material.FEATHER)
                        .setName("§6goPaint Brush")
                        .setList(goPaintStatus, "", "", "§7Left click with item to export", "§7Right click to toggle")
                        .create()
        );


        // Brushes + Chance
        this.inventory.setItem(
                2,
                new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                        .setName("§7")
                        .create()
        );

        /*
        Brush Item with dynamic description
        */

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7Shift click to select");
        lore.add("§7Click to cycle brush");
        lore.add("");
        lore.add("");
        if(playerBrush.isBiome()) {
            GoPaintPlugin.getBrushManager().getBiomeBrushLore(lore, playerBrush.getBrush().getName());
        } else {
            GoPaintPlugin.getBrushManager().getColorBrushLore(lore, playerBrush.getBrush().getName());
        }

        this.inventory.setItem(
                11,
                new ItemBuilder(Material.PLAYER_HEAD)
                        .setName("§6Selected Brush type")
                        .setCustomHead(playerBrush.getBrush().getSkin())
                        .setList(lore)
                        .create()
        );

        // Mask toggle
        Material maskStatusMaterial = playerBrush.isMaskEnabled() ? Material.LIME_STAINED_GLASS_PANE :
                Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                6,
                new ItemBuilder(maskStatusMaterial)
                        .setName("§7")
                        .create()
        );

        String maskStatus = playerBrush.isMaskEnabled() ? "§a§lEnabled" : "§c§lDisabled";
        Material maskMaterial = playerBrush.isMaskEnabled() ? Material.JACK_O_LANTERN : Material.PUMPKIN;
        this.inventory.setItem(
                15,
                new ItemBuilder(maskMaterial)
                        .setName("§6Mask")
                        .setList( maskStatus, "", "", "§7Click to toggle")
                        .create()
        );

        // Mask Item Pane
        this.inventory.setItem(
                7,
                new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                        .setName("§7")
                        .create()
        );

        Material maskItem = playerBrush.getMask();
        this.inventory.setItem(
                16,
                new ItemBuilder(maskItem)
                        .setName("§6Mask Item")
                        .setList("§7Click with other block to change")
                        .create()
        );



        String surfaceModeStatus = playerBrush.isSurfaceModeEnabled() ? "§a§lEnabled" : "§c§lDisabled";
        this.inventory.setItem(
                12,
                new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
                        .setName("§6Surface Mode")
                        .setList(surfaceModeStatus, "", "", "§7Click to toggle")
                        .create()
        );

        // Surface Mode toggle
        Material surfaceMaterial = playerBrush.isSurfaceModeEnabled() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                3,
                new ItemBuilder(surfaceMaterial)
                        .setName("§7")
                        .create()
        );


                /*
         Glass Pane for goPaint is enabled/disabled
         */
        Material biomeMaterial = playerBrush.isBiome() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                4,
                new ItemBuilder(biomeMaterial)
                        .setName("§7")
                        .create()
        );

        /*
        Biome Item (GRASS_BLOCK)
        */
        String biomeStatus = playerBrush.isBiome() ? "§a§lEnabled" : "§c§lDisabled";
        this.inventory.setItem(
                13,
                new ItemBuilder(Material.GRASS_BLOCK)
                        .setName("§6goPaint Biomes")
                        .setList(biomeStatus, "", "", "§7Right / Left click to toggle")
                        .create()
        );




        int currentSetting = 0;
        // Brush Settings
        for(BrushSettings brushSetting : playerBrush.getBrush().getSettings()) {
            AbstractSetting abstractSetting = brushSetting.getSetting();
            this.inventory.setItem(28 + currentSetting, abstractSetting.getItem(playerBrush));
            this.inventory.setItem(19 + currentSetting,
                    new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE)
                            .setName("§a")
                            .create());
            currentSetting++;
        }


        // Place Block
        for (int x = 37; x <= 43; x++) {
            this.inventory.setItem(
                    x,
                    new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                            .setName("§7")
                            .create()
            );

            this.inventory.setItem(
                    x+9,
                    new ItemBuilder(Material.BARRIER)
                            .setName("§cEmpty Slot")
                            .setList("", "§7Click with a block to set")
                            .create()
            );
        }

        int x = 46;


        if(playerBrush.isBiome()) {
            int size = playerBrush.getBiomeTypes().size();
            for (BiomeType pbMaterial : playerBrush.getBiomeTypes()) {

                int chance = (int) Math.floor(100 / size);
                if (chance > 64) {
                    this.inventory.setItem(
                            x,
                            new ItemBuilder(Material.PAPER)
                                    .setAmount((int) Math.floor(100 / size))
                                    .setName(pbMaterial.getId())
                                    .setList("§aSlot " + (x - 45) + " §7" + (int) Math.floor(100 / size) + "%", "§7Left click " +
                                            "with a block to change", "§7Right click to clear").create()
                    );
                } else {
                    this.inventory.setItem(
                            x,
                            new ItemBuilder(Material.PAPER)
                                    .setAmount((int) Math.floor(100 / size))
                                    .setName(pbMaterial.getId())
                                    .setList("§aSlot " + (x - 45) + " §7" + (int) Math.floor(100 / size) + "%", "§7Left click " +
                                            "with a block to change", "§7Right click to clear").create()

                    );
                }
                x++;
            }
        } else {
            for (Material pbMaterial : playerBrush.getBlocks()) {
                int size = playerBrush.getBlocks().size();
                int chance = (int) Math.floor(100 / size);
                if (chance > 64) {
                    this.inventory.setItem(
                            x,
                            new ItemBuilder(pbMaterial)
                                    .setAmount((int) Math.floor(100 / size))
                                    .setName("§aSlot " + (x - 45) + " §7" + (int) Math.floor(100 / size) + "%")
                                    .setList("§7Left click with a block to change", "§7Right click to clear").create()
                    );
                } else {
                    this.inventory.setItem(
                            x,
                            new ItemBuilder(pbMaterial)
                                    .setAmount((int) Math.floor(100 / size))
                                    .setName("§aSlot " + (x - 45) + " §7" + (int) Math.floor(100 / size) + "%")
                                    .setList("§7Left click with a block to change", "§7Right click to clear").create()

                    );
                }
                x++;
            }
        }

        return this.inventory;
    }

    @Override
    public void interactInventory(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        PlayerBrush playerBrush = GoPaintPlugin.getBrushManager().getPlayerBrush(player);

        switch (event.getRawSlot()) {
            case 1:
            case 10: {
                switch (event.getClick()) {
                    case LEFT: {
                        if (event.getCursor() == null) {
                            return;
                        }
                        if (event.getCursor().getType().isBlock()) {
                            return;
                        }
                        if (event.getCursor().getType().equals(Material.FEATHER)) {
                            return;
                        }
                        playerBrush.export(event.getCursor());
                        break;
                    }
                    case RIGHT: {
                        playerBrush.toggleEnabled();
                        break;
                    }
                }
                break;
            }

            case 2:
            case 11: {
                switch (event.getClick()) {
                    case LEFT: {
                        if(playerBrush.isBiome()) {
                            playerBrush.cycleBiomeBrush();
                        } else {
                            playerBrush.cycleColorBrush();
                        }
                        break;
                    }
                    case RIGHT: {
                        if(playerBrush.isBiome()) {
                            playerBrush.cycleBiomeBrushBackwards();
                        } else {
                            playerBrush.cycleColorBrushBackwards();
                        }
                        break;
                    }
                    case SHIFT_RIGHT:
                    case SHIFT_LEFT: {
                        if(!playerBrush.isBiome())
                        player.openInventory(new BrushesInventory().createInventory(playerBrush));
                        break;
                    }
                }
                break;
            }
            case 3:
            case 12: {
                playerBrush.toggleSurfaceMode();
                break;
            }
            case 4:
            case 13: {
                playerBrush.toggleBiome();
                break;
            }
            case 6:
            case 15: {
                playerBrush.toggleMask();
                break;
            }
            case 7:
            case 16: {
                if (event.getClick().equals(ClickType.LEFT)) {
                    if (event.getCursor() != null && event.getCursor().getType().isBlock() && event
                            .getCursor()
                            .getType()
                            .isSolid() && (!DisabledBlocks.isDisabled(event.getCursor().getType()))) {
                        playerBrush.setMask(event.getCursor().getType());
                    }
                }
                break;
            }
        }

        if ((event.getRawSlot() >= 19 && event.getRawSlot() <= 25) || (event.getRawSlot() >= 28 && event.getRawSlot() <= 34)) {
            int slot;
            if (event.getRawSlot() >= 19 && event.getRawSlot() <= 25) {
                slot = event.getRawSlot() - 18;
            } else {
                slot = event.getRawSlot() - 27;
            }
            slot -= 1;

            BrushSettings brushSettings = playerBrush.getBrush().getSetting(slot);
            if(brushSettings == null) return;

            AbstractSetting setting = brushSettings.getSetting();
            setting.interact(playerBrush, event.getClick());

        } else if ((event.getRawSlot() >= 37 && event.getRawSlot() <= 43) || (event.getRawSlot() >= 46 && event.getRawSlot() <= 52)) {


            int slot;
            if (event.getRawSlot() >= 37 && event.getRawSlot() <= 43) {
                slot = event.getRawSlot() - 36;
            } else {
                slot = event.getRawSlot() - 45;
            }

            if(playerBrush.isBiome()) {
                switch (event.getClick()) {
                    case RIGHT -> {
                        playerBrush.removeBiome(slot);
                    }
                    case LEFT -> {
                        player.openInventory(new BiomeInventory().createInventory(playerBrush));
                    }
                }
                return;
            }

            switch (event.getClick()) {
                case LEFT: {
                    if (event.getCursor() != null && event.getCursor().getType().isBlock() && event
                            .getCursor()
                            .getType()
                            .isSolid() && (!DisabledBlocks
                            .isDisabled(event.getCursor().getType()))) {
                        playerBrush.addBlock(
                                event.getCursor().getType(),
                                slot
                        );
                    }
                    break;
                }
                case RIGHT: {
                    playerBrush.removeBlock(slot);
                    break;
                }
            }
        }
    }

}
