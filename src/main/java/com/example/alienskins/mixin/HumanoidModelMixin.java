package com.example.alienskins.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Reshapes every player (and the armor drawn on them) into a grey-alien body:
 * big head, narrow torso, thin long arms, thin legs.
 * Only runs when the thing being drawn is a player, so zombies, skeletons etc. are untouched.
 */
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin {

    @Shadow @Final public ModelPart head;
    @Shadow @Final public ModelPart body;
    @Shadow @Final public ModelPart rightArm;
    @Shadow @Final public ModelPart leftArm;
    @Shadow @Final public ModelPart rightLeg;
    @Shadow @Final public ModelPart leftLeg;

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
            at = @At("RETURN"))
    private void alienskins$reshape(HumanoidRenderState state, CallbackInfo ci) {
        if (!(state instanceof AvatarRenderState)) {
            return;
        }

        // Big head (grows upward from the neck)
        head.xScale = 1.5f;
        head.yScale = 1.1f;
        head.zScale = 1.5f;

        // Narrow torso
        body.xScale = 0.75f;
        body.zScale = 0.7f;

        // Long, thin arms, moved in to meet the narrower torso
        rightArm.xScale = 0.5f;
        rightArm.zScale = 0.5f;
        rightArm.yScale = 1.15f;
        rightArm.x = -3.5f;
        leftArm.xScale = 0.5f;
        leftArm.zScale = 0.5f;
        leftArm.yScale = 1.15f;
        leftArm.x = 3.5f;

        // Thin legs (same length so feet stay on the ground)
        rightLeg.xScale = 0.6f;
        rightLeg.zScale = 0.6f;
        rightLeg.x = -1.5f;
        leftLeg.xScale = 0.6f;
        leftLeg.zScale = 0.6f;
        leftLeg.x = 1.5f;
    }
}
