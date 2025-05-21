package com.harleylizard.slowcooking.mixins;

import com.harleylizard.slowcooking.common.SlowcookingItemTags;
import com.harleylizard.slowcooking.common.flavour.Flavour;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public final class ItemStackMixin {

    @Inject(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 6, shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void slowcooking$e(Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, List list, MutableComponent mutableComponent, Consumer consumer) {
        var stack = ((ItemStack) (Object) this);
        if (SlowcookingItemTags.INSTANCE.isIngredient(stack)) {
            consumer.accept(Component.translatable("slowcooking.tooltip.ingredient", Flavour.Companion.tooltip(stack).getString()).withStyle(ChatFormatting.BLUE));
        }
    }

}
