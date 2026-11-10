package com.example.alienskins.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Stretches every player upward (from the feet) to give the alien a tall build. */
@Mixin(AvatarRenderer.class)
public abstract class AvatarRendererMixin {

    // 1.0 = normal height. Change this number to make the alien taller or shorter.
    private static final float ALIEN_HEIGHT = 1.4f;

    @Inject(method = "scale(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;)V",
            at = @At("RETURN"))
    private void alienskins$tall(AvatarRenderState state, PoseStack poseStack, CallbackInfo ci) {
        poseStack.scale(1.0f, ALIEN_HEIGHT, 1.0f);
    }
}
