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
package net.arcaniax.gopaint.paint.brush;

import com.fastasyncworldedit.core.Fawe;
import com.fastasyncworldedit.core.queue.implementation.QueueHandler;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.block.BlockState;
import net.arcaniax.gopaint.paint.settings.BrushSettings;
import net.arcaniax.gopaint.paint.brush.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
public abstract class Brush {

    // Array of brush settings
    protected BrushSettings[] settings;

    /**
     * Constructor for the Brush class.
     *
     * @param settings An array of BrushSettings for the brush.
     * @throws Exception If there are more than 8 settings provided.
     */
    public Brush(BrushSettings[] settings) throws Exception {
        this.settings = settings;

        // Check if the number of settings is within the limit
        if (settings.length >= 8) {
            throw new IllegalArgumentException("You can only add 9 settings to a brush [in: " + this.getClass().getName() + "]");
        }
    }

    /**
     * Paint using the brush when the player interacts with the right mouse button.
     *
     * @param playerBrush   The player's brush settings.
     * @param clickedVector The block that was clicked.
     * @param player        The player who is using the brush.
     * @param editSession   The EditSession for performing block placement.
     */
    public abstract void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player,
                                    EditSession editSession);

    /**
     * Paint using the brush when the player interacts with the left mouse button.
     * By default, it delegates to the `paintRight` method.
     *
     * @param playerBrush   The player's brush settings.
     * @param clickedVector The block that was clicked.
     * @param player        The player who is using the brush.
     * @param editSession   The EditSession for performing block placement.
     */
    public void paintLeft(AbstractPlayerBrush playerBrush, MutableVector3 clickedVector, Player player, EditSession editSession) {
        paintRight(playerBrush, clickedVector, player, editSession);
    }

    /**
     * Handle player interaction events with the brush.
     *
     * @param event     The PlayerInteractEvent triggered by the player.
     * @param playerBrush The player's brush settings.
     * @param location  The location where the player interacted.
     */
    public void interact(PlayerInteractEvent event, AbstractPlayerBrush playerBrush, Location location) {
        Player player = event.getPlayer();

        QueueHandler queue = Fawe.instance().getQueueHandler();
        switch (event.getAction()) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> {
                // Handle left-click interaction
                queue.async(() -> {
                    LocalSession localSession =
                            WorldEdit.getInstance().getSessionManager().get(WorldEditPlugin.getInstance().wrapPlayer(player));
                    EditSession editsession = localSession.createEditSession(BukkitAdapter.adapt(player));
                    playerBrush.getBrush().paintLeft(playerBrush, new MutableVector3(location), player, editsession);
                    localSession.remember(editsession);
                });
            }
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                // Handle right-click interaction
                queue.async(() -> {
                    LocalSession localSession =
                            WorldEdit.getInstance().getSessionManager().get(WorldEditPlugin.getInstance().wrapPlayer(player));
                    EditSession editsession = localSession.createEditSession(BukkitAdapter.adapt(player));
                    playerBrush.getBrush().paintRight(playerBrush, new MutableVector3(location), player, editsession);
                    localSession.remember(editsession);
                });
            }
        }
    }

    /**
     * Check if a block can be placed at a given location.
     *
     * @param editSession    The EditSession to check block placement.
     * @param blockVector  The vector representing the block's location.
     * @param playerBrush    The player's brush settings.
     * @param clickedVector  The vector representing the location clicked by the player.
     * @return True if the block can be placed, otherwise false.
     */
    public boolean canPlace(EditSession editSession, MutableVector3 blockVector, AbstractPlayerBrush playerBrush,
                            MutableVector3 clickedVector) {
        BlockState block = editSession.getBlock(blockVector.getBlockX(), blockVector.getBlockY(),
                blockVector.getBlockZ()
        );

        if (block.isAir() || block.getMaterial().isLiquid()) {
            return false;
        }

        if (playerBrush.isSurfaceModeEnabled() && !Surface.isOnSurface(blockVector.clone(),
                clickedVector, editSession
        )) {
            return false; // Skip blocks that don't meet surface mode condition
        }

        if ((playerBrush.isMaskEnabled() && block.getBlockType() != playerBrush.getMask())
                && !isGmask(editSession, blockVector)) {
            return false; // Skip blocks that don't meet the mask condition
        }

        return true;
    }

    /**
     * Check if a mask is applied in the given EditSession.
     *
     * @param session     The EditSession to check for a mask.
     * @param blockVector The vector representing the block location.
     * @return True if a mask is applied, otherwise false.
     */
    public boolean isGmask(EditSession session, MutableVector3 blockVector) {
        return session.getActor().getSession().getMask() == null || session.getActor().getSession().getMask().test(blockVector.toBlockPoint());
    }

    /**
     * Get the name of the brush.
     *
     * @return The name of the brush.
     */
    public abstract String getName();

    /**
     * Get an array of descriptions for the brush.
     *
     * @return An array of descriptions for the brush.
     */
    public abstract String[] getDescription();

    /**
     * Get the skin for the brush.
     *
     * @return The skin of the brush.
     */
    public abstract String getSkin();

    /**
     * Get all settings associated with the brush.
     *
     * @return An array of brush settings.
     */
    public BrushSettings[] getSettings() {
        return this.settings;
    }

    /**
     * Get a specific setting by its index.
     *
     * @param index The index of the setting to retrieve.
     * @return The BrushSetting at the specified index.
     */
    public BrushSettings getSetting(int index) {
        if (index < 0) return null;
        if (index >= this.settings.length) return null;
        return this.settings[index];
    }
}
