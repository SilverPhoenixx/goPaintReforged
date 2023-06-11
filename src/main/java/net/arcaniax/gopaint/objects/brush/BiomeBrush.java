package net.arcaniax.gopaint.objects.brush;

import net.arcaniax.gopaint.objects.brush.settings.BrushSettings;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_19_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.List;

public abstract class BiomeBrush extends Brush {

    public BiomeBrush(final BrushSettings[] settings) {
        super(settings);
    }

    public void update(Player player, List<Pair<Integer, Integer>> chunks) {

        for(Pair<Integer, Integer> chunkCoords : chunks) {
            Chunk bukkitChunk = player.getWorld().getChunkAt(chunkCoords.getLeft(), chunkCoords.getRight());
            net.minecraft.world.level.chunk.Chunk  minecraftChunk = ((CraftChunk) bukkitChunk).getHandle();
            ClientboundLevelChunkWithLightPacket packet = new ClientboundLevelChunkWithLightPacket(minecraftChunk,
                    minecraftChunk.D().l_(), null, null,
                    true);


            for(Player worldPlayer : player.getWorld().getPlayers())
            ((CraftPlayer) worldPlayer).getHandle().b.a(packet);
        }
}

}
