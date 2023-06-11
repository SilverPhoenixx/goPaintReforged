package net.arcaniax.gopaint.utils.gui;

import com.sk89q.worldedit.world.biome.BiomeType;
import net.arcaniax.gopaint.GoPaintPlugin;
import net.arcaniax.gopaint.objects.brush.settings.AbstractSetting;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.objects.player.PlayerBrush;
import net.arcaniax.gopaint.utils.DisabledBlocks;
import net.arcaniax.gopaint.utils.ItemBuilder;
import net.arcaniax.gopaint.utils.Items;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuInventory extends GoPaintInventory {

    @Override
    public Inventory createInventory(PlayerBrush playerBrush) {

        this.inventory = Bukkit.createInventory(this, 54, "§6goPaint Menu");

        // FILLER
        for (int slot = 0; slot < 54; slot++) {
            this.inventory.setItem(
                    slot,
                    Items.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "§c", "")
            );
        }

        /*
         Glass Pane for goPaint is enabled/disabled
         */
        Material material = playerBrush.isEnabled() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                1,
                Items.create(
                        material,
                        1,
                        "&7",
                        ""
                )
        );

        /*
        goPaint Item (Feather)
        */
        String goPaintStatus = playerBrush.isEnabled() ? "&a&lEnabled" : "&c&lDisabled";
        this.inventory.setItem(
                10,
                Items.create(
                        Material.FEATHER,
                        1,
                        "&6goPaint Brush",
                          goPaintStatus + "______&7Left click with item to export___&7Right click to toggle"
                )
        );


        // Brushes + Chance
        this.inventory.setItem(
                2,
                Items.create(
                        Material.ORANGE_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );

        /*
        Brush Item with dynamic description
        */
        String clicks = "___&7Shift click to select___&7Click to cycle brush______";

        String lore = "";
        if(playerBrush.isBiome()) {
            lore = clicks + GoPaintPlugin.getBrushManager().getBiomeBrushLore(playerBrush.getBrush().getName());
        } else {
            lore = clicks + GoPaintPlugin.getBrushManager().getColorBrushLore(playerBrush.getBrush().getName());
        }

        this.inventory.setItem(
                11,
                Items.createHead(
                        playerBrush.getBrush().getSkin(),
                        1,
                        "&6Selected Brush type",
                        lore
                )
        );

        // Mask toggle
        Material maskStatusMaterial = playerBrush.isMaskEnabled() ? Material.LIME_STAINED_GLASS_PANE :
                Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                6,
                Items.create(
                        maskStatusMaterial,
                        1,
                        "&7",
                        ""
                )
        );

        String maskStatus = playerBrush.isMaskEnabled() ? "&a&lEnabled" : "&c&lDisabled";
        Material maskMaterial = playerBrush.isMaskEnabled() ? Material.JACK_O_LANTERN : Material.PUMPKIN;
        this.inventory.setItem(
                15,
                Items.create(
                        maskMaterial,
                        1,
                        "&6 Mask",
                        maskStatus + "______&7Click to toggle"
                )
        );

        // Mask Item Pane
        this.inventory.setItem(
                7,
                Items.create(
                        Material.ORANGE_STAINED_GLASS_PANE,
                        1,
                        "&7",
                        ""
                )
        );

        Material maskItem = playerBrush.getMask();
        this.inventory.setItem(
                16,
                Items.create(
                        maskItem,
                        1,
                        "&6 Mask Item",
                        "&7Click with other block to change"
                )
        );



        String surfaceModeStatus = playerBrush.isSurfaceModeEnabled() ? "&a&lEnabled" : "&c&lDisabled";
        this.inventory.setItem(
                12,
                Items.create(
                        Material.LIGHT_WEIGHTED_PRESSURE_PLATE,
                        1,
                        "&6Surface Mode",
                         surfaceModeStatus + "______&7Click to toggle"
                )
        );

        // Surface Mode toggle
        Material surfaceMaterial = playerBrush.isSurfaceModeEnabled() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                3,
                Items.create(
                        surfaceMaterial,
                        1,
                        "&7",
                        ""
                )
        );


                /*
         Glass Pane for goPaint is enabled/disabled
         */
        Material biomeMaterial = playerBrush.isBiome() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE;
        this.inventory.setItem(
                4,
                Items.create(
                        biomeMaterial,
                        1,
                        "&7",
                        ""
                )
        );

        /*
        Biome Item (GRASS_BLOCK)
        */
        String biomeStatus = playerBrush.isBiome() ? "&a&lEnabled" : "&c&lDisabled";
        this.inventory.setItem(
                13,
                Items.create(
                        Material.GRASS_BLOCK,
                        1,
                        "&6goPaint Biomes",
                        biomeStatus + "______&7Right / Left click to toggle"
                )
        );




        int currentSetting = 0;
        // Brush Settings
        for(BrushSettings brushSetting : playerBrush.getBrush().getSettings()) {
            AbstractSetting abstractSetting = brushSetting.getSetting();
            this.inventory.setItem(28 + currentSetting, abstractSetting.getItem(playerBrush));
            this.inventory.setItem(19 + currentSetting, Items.create(Material.WHITE_STAINED_GLASS_PANE, 1, "§a", ""));
            currentSetting++;
        }


        // Place Block
        for (int x = 37; x <= 43; x++) {
            this.inventory.setItem(
                    x,
                    Items.create(
                            Material.GRAY_STAINED_GLASS_PANE,
                            1,
                            "&7",
                            ""
                    )
            );

            this.inventory.setItem(
                    x+9,
                    Items.create(
                            Material.BARRIER,
                            1,
                            "&cEmpty Slot",
                            "___&7Click with a block to set"
                    )
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
