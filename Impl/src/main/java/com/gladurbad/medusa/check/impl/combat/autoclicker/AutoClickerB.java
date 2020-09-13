package com.gladurbad.medusa.check.impl.combat.autoclicker;

import com.gladurbad.medusa.check.Check;
import com.gladurbad.api.check.CheckInfo;
import com.gladurbad.medusa.network.Packet;
import com.gladurbad.medusa.playerdata.PlayerData;
import com.gladurbad.medusa.util.MathUtil;
import com.gladurbad.medusa.util.customtype.EvictingList;
import io.github.retrooper.packetevents.packet.PacketType;

import java.util.ArrayDeque;

@CheckInfo(name = "AutoClicker", type = "B", dev = true)
public class AutoClickerB extends Check {

    private int ticks;
    private ArrayDeque<Double> delays = new ArrayDeque<>();
    private EvictingList<Double> kurtosi = new EvictingList<>(10);

    public AutoClickerB(PlayerData data) {
        super(data);
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isReceiving()) {
            if (packet.getPacketId() == PacketType.Client.ARM_ANIMATION && !data.isDigging()) {
                if (ticks < 10) {
                    delays.add((double) ticks);

                    if (delays.size() >= 20) {
                        final double kurtosis = MathUtil.getKurtosis(delays);
                        kurtosi.add(kurtosis);
                        delays.clear();
                    }

                    if (kurtosi.size() == 10) {
                        final int duplicates = (int) (kurtosi.size() - kurtosi.parallelStream().mapToDouble(value -> value).distinct().count());

                        if (duplicates > 1) {
                            kurtosi.clear();
                            fail();
                        }
                    }
                }
                ticks = 0;
            } else if (isFlyingPacket(packet)) {
                ++ticks;
            }
        }
    }
}
