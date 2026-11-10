package com.example.alienskins;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Remembers who spoke recently. Contains no voice chat classes on purpose. */
public final class SpeakingTracker {
    private static final Map<UUID, Long> LAST_SPOKE = new ConcurrentHashMap<>();
    private static final long TALK_TIMEOUT_MS = 300;  // how long after the last audio they still count as talking
    private static final long FLAP_MS = 140;          // how fast the mouth opens and closes

    private SpeakingTracker() {}

    public static void markSpeaking(UUID id) {
        LAST_SPOKE.put(id, System.currentTimeMillis());
    }

    public static void clear(UUID id) {
        LAST_SPOKE.remove(id);
    }

    public static boolean isSpeaking(UUID id) {
        Long t = LAST_SPOKE.get(id);
        return t != null && System.currentTimeMillis() - t < TALK_TIMEOUT_MS;
    }

    /** True on the "mouth open" half of the flap while the player is talking. */
    public static boolean isMouthOpen(UUID id) {
        return isSpeaking(id) && ((System.currentTimeMillis() / FLAP_MS) & 1L) == 0L;
    }
}
