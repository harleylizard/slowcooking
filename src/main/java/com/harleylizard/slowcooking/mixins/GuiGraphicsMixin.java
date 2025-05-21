package com.harleylizard.slowcooking.mixins;

import com.harleylizard.slowcooking.client.FlavourProfileTooltip;
import com.harleylizard.slowcooking.common.flavour.FlavourProfile;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Mixin(GuiGraphics.class)
public final class GuiGraphicsMixin {

    @Redirect(method = "renderTooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;Ljava/util/Optional;II)V", at = @At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V"))
    public void slowcooking$e(Optional<TooltipComponent> instance, Consumer<TooltipComponent> action, @Local(ordinal = 1) List<ClientTooltipComponent> list2) {
        instance.ifPresent(component -> {
            if (component instanceof FlavourProfile profile) {
                list2.add(new FlavourProfileTooltip(profile));
            } else {
                action.accept(component);
            }
        });
    }
}
