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
import net.arcaniax.gopaint.paint.brush.settings.BrushSettings;
import net.arcaniax.gopaint.paint.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.math.Surface;
import net.arcaniax.gopaint.utils.vectors.MutableVector3;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
public abstract class Brush {

    protected BrushSettings[] settings;

    public Brush(BrushSettings[] settings) throws Exception {
        this.settings = settings;

        if(settings.length >= 10) throw new IllegalArgumentException("You can only add 9 settings to a brush [in: " + this.getClass().getName() +
                "]");
    }

    public abstract void paintRight(AbstractPlayerBrush playerBrush, MutableVector3 clickedBlock, Player player,
                                    EditSession editSession);
    public void paintLeft(AbstractPlayerBrush playerBrush, MutableVector3 clickedBlock, Player player, EditSession editSession) {
        paintRight(playerBrush, clickedBlock, player, editSession);
    }

    public void interact(PlayerInteractEvent event, AbstractPlayerBrush playerBrush, Location location) {
            Player player = event.getPlayer();


        QueueHandler queue = Fawe.instance().getQueueHandler();
        switch (event.getAction()) {
            case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> {
                queue.async(() -> {
                    LocalSession localSession =
                            WorldEdit.getInstance().getSessionManager().get(WorldEditPlugin.getInstance().wrapPlayer(player));
                    EditSession editsession = localSession.createEditSession(BukkitAdapter.adapt(player));
                    playerBrush.getBrush().paintLeft(playerBrush, new MutableVector3(location), player, editsession);


                    localSession.remember(editsession);
                });
            }
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
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

    public boolean canPlace(EditSession editSession, MutableVector3 blockLocation, AbstractPlayerBrush playerBrush,
                            MutableVector3 clickedVector) {
        BlockState block = editSession.getBlock(blockLocation.getBlockX(), blockLocation.getBlockY(),
                blockLocation.getBlockZ()
        );

        if (playerBrush.isSurfaceModeEnabled() && !Surface.isOnSurface(blockLocation.clone(),
                clickedVector, editSession
        )) {
            return false; // Skip blocks that don't meet surface mode condition
        }

        if (playerBrush.isMaskEnabled() && block.getBlockType() != playerBrush.getMask()) {
            return false; // Skip blocks that don't meet the mask condition
        }
        return true;
    }

    public boolean canPlaceWithAir(EditSession editSession, MutableVector3 blockLocation, AbstractPlayerBrush playerBrush,
                                   MutableVector3 clickedVector) {
        BlockState block = editSession.getBlock(blockLocation.getBlockX(), blockLocation.getBlockY(),
                blockLocation.getBlockZ()
        );

        if(block.isAir()) return false;

        if (playerBrush.isSurfaceModeEnabled() && !Surface.isOnSurface(blockLocation.clone(),
                clickedVector, editSession
        )) {
            return false; // Skip blocks that don't meet surface mode condition
        }

        if (playerBrush.isMaskEnabled() && block.getBlockType() != playerBrush.getMask()) {
            return false; // Skip blocks that don't meet the mask condition
        }
        return true;
    }

    public boolean isGmask(EditSession session, BlockVector3 v) {
        return session.getActor().getSession().getMask() == null || session.getActor().getSession().getMask().test(v);
    }
    public abstract String getName();
    public abstract String[] getDescription();
    public abstract String getSkin();

    public BrushSettings[] getSettings() {
        return this.settings;
    }

    public BrushSettings getSetting(int index) {
        if(index < 0) return null;
        if(index >= this.settings.length) return null;
        return this.settings[index];
    }
}
