package com.harleylizard.slowcooking.mixins;

import com.harleylizard.slowcooking.common.flavour.FlavourProfile;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public final class BlockMixin {

    @Inject(method = "popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V", at = @At("HEAD"))
    private static void slowcooking$popResource(Level level, BlockPos blockPos, ItemStack itemStack, CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) LocalRef<ItemStack> ref) {
        ref.set(FlavourProfile.Companion.apply(ref.get()));
    }
}
