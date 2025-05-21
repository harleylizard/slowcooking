package com.harleylizard.slowcooking.mixins;

import com.harleylizard.slowcooking.common.flavour.FlavourProfile;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeTab.ItemDisplayBuilder.class)
public final class ItemDisplayBuilderMixin {

    @Inject(method = "accept", at = @At("HEAD"))
    public void slowcooking$accept(ItemStack itemStack, CreativeModeTab.TabVisibility tabVisibility, CallbackInfo ci, @Local(ordinal = 0, argsOnly = true) LocalRef<ItemStack> ref) {
        ref.set(FlavourProfile.Companion.apply(ref.get()));
    }
}
