package com.harleylizard.slowcooking.mixins;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.battles.runner.ShowdownService;
import com.harleylizard.slowcooking.common.drop.SpeciesDrop;
import kotlin.Unit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PokemonSpecies.class)
public final class PokemonSpeciesMixin {

    @Inject(method = "lambda$3$lambda$2", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;Ljava/lang/Object;)V"), remap = false)
    private static void slowcooking$init(ShowdownService it, CallbackInfoReturnable<Unit> cir) {
        SpeciesDrop.Companion.getSpeciesDrop().load(PokemonSpecies.INSTANCE);
    }
}
