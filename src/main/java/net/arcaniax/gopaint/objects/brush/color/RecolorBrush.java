package net.arcaniax.gopaint.objects.brush.color;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.Vector3;
import net.arcaniax.gopaint.objects.brush.Brush;
import net.arcaniax.gopaint.objects.brush.ColorBrush;
import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.arcaniax.gopaint.objects.player.AbstractPlayerBrush;
import net.arcaniax.gopaint.utils.ColorPalette;
import net.arcaniax.gopaint.utils.Sphere;
import net.arcaniax.gopaint.utils.Surface;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class RecolorBrush extends ColorBrush {

    public RecolorBrush() {
            super(new BrushSettings[] {
                    BrushSettings.SIZE,
                    BrushSettings.FALLOFF_STRENGTH
            });
    }

    @Override
    public void paintRight(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        if(!playerBrush.isMaskEnabled()) return;

        List<Block> blocks = Sphere.getBlocksInRadius(loc, playerBrush.getBrushSize());
        for (Block b : blocks) {
            List<Material> materials = playerBrush.getBlocks().isEmpty() ? ColorPalette.getMaterialList(b.getType()) :
                    playerBrush.getBlocks();
            if(materials == null) continue;
            if(!materials.contains(b.getType())) continue;
            int index = materials.indexOf(b.getType()) - 1;
            if(index <= -1) continue;
            Material material = materials.get(materials.indexOf(b.getType()) - 1);
            addBlockToList(playerBrush, b, loc, material, p, session);
        }
    }

    @Override
    public void paintLeft(AbstractPlayerBrush playerBrush, Location loc, Player p, EditSession session) {
        if(!playerBrush.isMaskEnabled()) return;
        List<Block> blocks = Sphere.getBlocksInRadius(loc, playerBrush.getBrushSize());
        for (Block b : blocks) {
            List<Material> materials = playerBrush.getBlocks().isEmpty() ? ColorPalette.getMaterialList(b.getType()) :
                    playerBrush.getBlocks();
            if(materials == null) continue;
            if(!materials.contains(b.getType())) continue;
            if(materials.indexOf(b.getType()) == materials.size() -1) continue;

            Material material = materials.get(materials.indexOf(b.getType()) + 1);
            addBlockToList(playerBrush, b, loc, material, p, session);
        }
    }


    public void addBlockToList(AbstractPlayerBrush playerBrush, Block block, Location location,
                               Material material, Player player, EditSession session) {
        if ((!playerBrush.isSurfaceModeEnabled()) || Surface.isOnSurface(block.getLocation(), player.getLocation())) {
                Random r = new Random();
                double rate = (block
                        .getLocation()
                        .distance(location) - ((double) playerBrush.getBrushSize() / 2.0) * ((100.0 - (double) playerBrush.getFalloffStrength()) / 100.0)) / (((double) playerBrush.getBrushSize() / 2.0) - ((double) playerBrush.getBrushSize() / 2.0) * ((100.0 - (double) playerBrush.getFalloffStrength()) / 100.0));
                if (!(r.nextDouble() <= rate)) {

                    Vector3 vector3 = Vector3.at(block.getX(), block.getY(), block.getZ());
                    if (isGmask(session, vector3.toBlockPoint())) {
                        try {
                            session.setBlock(
                                    block.getX(), block.getY(), block.getZ(),
                                    BukkitAdapter.asBlockType(material).getDefaultState()
                            );

                        } catch (Exception ignored) {
                        }
                    }
                }
            }
    }

    @Override
    public String getName() {
        return "Recolor Brush";
    }

    @Override
    public String getDescription() {
        return "___&7Click to select______" + "&8Brights (Left click) or Darken (Right click) blocks";
    }

    @Override
    public String getSkin() {
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ4ZDdkMWUwM2UxYWYxNDViMDEyNWFiODQxMjg1NjcyYjQyMTI2NWRhMmFiOTE1MDE1ZjkwNTg0MzhiYTJkOCJ9fX0=";
    }

}
