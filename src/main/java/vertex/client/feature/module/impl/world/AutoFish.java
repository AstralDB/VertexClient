/*
 * Copyright (c) 2022 Coffee Client, 0x150 and contributors. All rights reserved.
 */

package vertex.client.feature.module.impl.world;

import vertex.client.VertexMain;
import vertex.client.feature.config.DoubleSetting;
import vertex.client.feature.module.Module;
import vertex.client.feature.module.ModuleType;
import vertex.client.helper.event.EventType;
import vertex.client.helper.event.Events;
import vertex.client.helper.event.events.PacketEvent;
import vertex.client.helper.util.Utils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;

import java.util.Random;

public class AutoFish extends Module {
    static int delay = 0;
    final DoubleSetting lazytime = this.config.create(new DoubleSetting.Builder(1).min(1)
            .max(40)
            .name("Random")
            .description("the randomness added to fishing times")
            .get());

    public AutoFish() {
        super("AutoFish", "Automatically catches fish for you", ModuleType.WORLD);
        Events.registerEventHandler(EventType.PACKET_RECEIVE, packete -> {
            PacketEvent event = (PacketEvent) packete;
            if (event.getPacket() instanceof PlaySoundS2CPacket packet) {
                if (packet.getSound().equals(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH)) {
                    new Thread(() -> {
                        Utils.sleep(lazyRoundTime() * 100L);
                        click();
                    }).start();
                }
            }
        });
    }

    @Override
    public void tick() {
        int fishingrod = getFishingRod();
        if (fishingrod == -1) {
            this.setEnabled(false);
            Utils.Logging.message("No Valid Fishing rod found, disabling.");
            return;
        } else {
            VertexMain.client.player.getInventory().selectedSlot = fishingrod;
        }
        delay++;
        if (delay > lazyRoundTime()) {
            delay = 0;
        } else {
            return;
        }
        if (VertexMain.client.player.fishHook == null || VertexMain.client.player.fishHook.isRemoved()) {
            new Thread(() -> {
                Utils.sleep(lazyRoundTime() * 50L);
                click();
            }).start();
        }
    }

    @Override
    public void enable() {
    }

    @Override
    public void disable() {
    }

    @Override
    public String getContext() {
        return null;
    }

    @Override
    public void onWorldRender(MatrixStack matrices) {

    }

    @Override
    public void onHudRender() {

    }

    private void click() {
        VertexMain.client.player.networkHandler.sendPacket(new PlayerInteractItemC2SPacket(Hand.MAIN_HAND,
                Utils.increaseAndCloseUpdateManager(VertexMain.client.world)
        ));
    }

    public int getFishingRod() {
        if (VertexMain.client.player.getMainHandStack().getItem().equals(Items.FISHING_ROD)) {
            return VertexMain.client.player.getInventory().selectedSlot;
        }
        for (int i = 0; i < 9; i++) {
            if (VertexMain.client.player.getInventory().getStack(36 + i).getItem().equals(Items.FISHING_ROD)) {
                return i;
            }
        }
        return -1;
    }

    private int lazyRoundTime() {
        return (int) Math.round(lazytime.getValue()) + new Random().nextInt(10);
    }
}
