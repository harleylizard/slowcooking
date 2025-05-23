package com.harleylizard.slowcooking.mixins;

import com.cobblemon.mod.common.api.drop.ItemDropEntry;
import com.harleylizard.slowcooking.common.flavour.FlavourProfile;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemDropEntry.class)
public final class ItemDropEntryMixin {

    @Inject(method = "drop", at = @At(value = "INVOKE", target = "Lkotlin/jvm/internal/Intrinsics;areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z", ordinal = 0))
    public void slowcooking$drop(LivingEntity entity, ServerLevel world, Vec3 pos, ServerPlayer player, CallbackInfo ci, @Local LocalRef<ItemStack> ref) {
        ref.set(FlavourProfile.Companion.apply(ref.get()));
    }
}
