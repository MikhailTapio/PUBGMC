package com.toma.pubgmc.api.interfaces;

import com.toma.pubgmc.api.Game;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface BotSpawner {

    BlockPos getSpawnPosition(World world, Game game);
}
