package com.gladurbad.medusa.processors.impl;

import com.gladurbad.medusa.network.Packet;
import com.gladurbad.medusa.playerdata.PlayerData;
import com.gladurbad.medusa.processors.Processor;
import com.gladurbad.medusa.util.AABB;
import com.gladurbad.medusa.util.MathUtil;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.flying.WrappedPacketInFlying;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.util.*;

public class CollisionProcessor implements Processor {

    private Map<Material, List<AABB>> collidingBlocks = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void onHandle(Packet packet, PlayerData data) {
        WrappedPacketInFlying flying = new WrappedPacketInFlying(packet);

        AABB aabb = new AABB(flying.getX() - 0.3, flying.getY(), flying.getZ() - 0.3,
                flying.getX() + 0.3, flying.getY() + 1.8, flying.getZ() + 0.3);

        int startX = Location.locToBlock(flying.getX() - 2);
        int endX = Location.locToBlock(flying.getX() + 2);
        int startY = Location.locToBlock(flying.getY() - 2);
        int endY = Location.locToBlock(flying.getY() + 3);
        int startZ = Location.locToBlock(flying.getZ() - 2);
        int endZ = Location.locToBlock(flying.getZ() + 2);


        for(int x = startX ; x < endX ; x++) {
            for(int y = startY ; y < endY ; y++) {
                for(int z = startZ ; z < endZ ; z++) {
                    BlockPosition blockPosition = new BlockPosition(x,
                            y, z);
                    WorldServer world = ((CraftWorld)data.getPlayer().getWorld()).getHandle();
                    IBlockData blockData = world.getType(blockPosition);
                    Block block = blockData.getBlock();
                }
            }
        }
    }

    @Override
    public List<Byte> packetTypes() {
        return Arrays.asList(PacketType.Client.POSITION, PacketType.Client.POSITION_LOOK);
    }
}
