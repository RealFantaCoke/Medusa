package com.gladurbad.medusa.processors;

import com.gladurbad.medusa.network.Packet;
import com.gladurbad.medusa.playerdata.PlayerData;
import io.github.retrooper.packetevents.packet.PacketType;

import java.util.List;

public interface Processor {

    void onHandle(Packet packet, PlayerData data);

    List<Byte> packetTypes();
}
