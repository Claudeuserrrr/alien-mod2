package com.example.alienskins;

import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.ClientReceiveSoundEvent;
import de.maxhenkel.voicechat.api.events.ClientSoundEvent;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import net.minecraft.client.Minecraft;

/** Loaded by Simple Voice Chat (if installed) through the "voicechat" entrypoint. */
public class AlienVoicechatPlugin implements VoicechatPlugin {

    @Override
    public String getPluginId() {
        return "alienskins";
    }

    @Override
    public void registerEvents(EventRegistration registration) {
        // Other players talking (proximity, group, and static audio)
        registration.registerEvent(ClientReceiveSoundEvent.class, this::onReceive);
        registration.registerEvent(ClientReceiveSoundEvent.EntitySound.class, this::onReceive);
        registration.registerEvent(ClientReceiveSoundEvent.LocationalSound.class, this::onReceive);
        registration.registerEvent(ClientReceiveSoundEvent.StaticSound.class, this::onReceive);
        // You talking
        registration.registerEvent(ClientSoundEvent.class, this::onOwnMic);
    }

    private void onReceive(ClientReceiveSoundEvent event) {
        short[] audio = event.getRawAudio();
        if (audio != null && audio.length > 0) {
            SpeakingTracker.markSpeaking(event.getId());
        } else {
            SpeakingTracker.clear(event.getId()); // empty frame = they stopped talking
        }
    }

    private void onOwnMic(ClientSoundEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            SpeakingTracker.markSpeaking(mc.player.getUUID());
        }
    }
}
