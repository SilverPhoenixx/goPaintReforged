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
package net.arcaniax.gopaint.objects.brush;

import com.fastasyncworldedit.core.Fawe;
import com.fastasyncworldedit.core.queue.implementation.QueueHandler;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extension.platform.Capability;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.biome.BiomeTypes;
import com.sk89q.worldedit.world.block.BlockState;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.objects.player.AbstractPlayerBrush;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
public abstract class Brush {

    protected BrushSettings[] settings;

    public Brush(BrushSettings[] settings) {
        this.settings = settings;
    }

    public abstract void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session);
    public void paintLeft(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        paintRight(playerBrush, loc, p, session);
    }

    public void interact(PlayerInteractEvent event, AbstractPlayerBrush playerBrush, Location location) {
            Player player = event.getPlayer();


        QueueHandler queue = Fawe.instance().getQueueHandler();
                    switch (event.getAction()) {
                        case LEFT_CLICK_AIR:
                        case LEFT_CLICK_BLOCK: {
                            queue.async(() -> {
                                LocalSession localSession =
                                        WorldEdit.getInstance().getSessionManager().get(WorldEditPlugin.getInstance().wrapPlayer(player));
                                EditSession editsession = localSession.createEditSession(BukkitAdapter.adapt(player));
                                playerBrush.getBrush().paintLeft(playerBrush, location, player, editsession);
                                localSession.remember(editsession);
                            });
                            break;
                        }
                        case RIGHT_CLICK_AIR:
                        case RIGHT_CLICK_BLOCK: {
                            queue.async(() -> {
                                LocalSession localSession =
                                        WorldEdit.getInstance().getSessionManager().get(WorldEditPlugin.getInstance().wrapPlayer(player));
                                EditSession editsession = localSession.createEditSession(BukkitAdapter.adapt(player));
                                playerBrush.getBrush().paintRight(playerBrush, location, player, editsession);
                                localSession.remember(editsession);
                            });
                            break;
                        }
                    }
    }

    public boolean isGmask(EditSession session, BlockVector3 v) {
        return session.getActor().getSession().getMask() == null || session.getActor().getSession().getMask().test(v);
    }
    public abstract String getName();
    public abstract String getDescription();
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
