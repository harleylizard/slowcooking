package com.harleylizard.slowcooking.mixins;

import com.harleylizard.slowcooking.common.SlowcookingComponents;
import com.harleylizard.slowcooking.common.SlowcookingItemTags;
import com.harleylizard.slowcooking.common.flavour.Flavour;
import com.harleylizard.slowcooking.common.flavour.FlavourProfile;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public final class ItemStackMixin {

    @Shadow @Final PatchedDataComponentMap components;

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/core/component/PatchedDataComponentMap;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;verifyComponentsAfterLoad(Lnet/minecraft/world/item/ItemStack;)V"))
    public void slowcooking$init(ItemLike itemLike, int i, PatchedDataComponentMap patchedDataComponentMap, CallbackInfo ci) {
        if (ingredient()) {
            patchedDataComponentMap.set(SlowcookingComponents.INSTANCE.getFlavourProfile(), FlavourProfile.Companion.tags((ItemStack) (Object) this));
        }
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 6, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void slowcooking$getTooltipLines(Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List list, MutableComponent mutableComponent, Consumer consumer) {
        if (ingredient()) {
            consumer.accept(Component.translatable("slowcooking.tooltip.ingredient", Flavour.Companion.tooltip(((ItemStack) (Object) this)).getString()).withStyle(ChatFormatting.BLUE));
        }
    }

    @Inject(method = "getTooltipImage", at = @At("RETURN"), cancellable = true)
    public void slowcooking$getTooltipImage(CallbackInfoReturnable<Optional<TooltipComponent>> cir) {
        var component = SlowcookingComponents.INSTANCE.getFlavourProfile();
        if (components.has(component)) {
            cir.setReturnValue(Optional.ofNullable(components.get(component)));
        }
    }

    @Unique
    private boolean ingredient() {
        return SlowcookingItemTags.INSTANCE.isIngredient(((ItemStack) (Object) this));
    }

}
