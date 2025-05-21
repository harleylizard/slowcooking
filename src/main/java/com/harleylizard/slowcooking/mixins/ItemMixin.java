package com.harleylizard.slowcooking.mixins;

import com.harleylizard.slowcooking.common.flavour.FlavourProfile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public final class ItemMixin {

    @Inject(method = "getDefaultInstance", at = @At("RETURN"), cancellable = true)
    public void slowcooking$getDefaultInstance(CallbackInfoReturnable<ItemStack> cir) {
        cir.setReturnValue(FlavourProfile.Companion.apply(cir.getReturnValue()));
    }
}
