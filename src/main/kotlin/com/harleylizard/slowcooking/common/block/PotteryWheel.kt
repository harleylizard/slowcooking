package com.harleylizard.slowcooking.common.block

import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult

class PotteryWheel(properties: Properties) : Block(properties) {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(hasClay, false).setValue(BlockStateProperties.POWERED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(hasClay).add(BlockStateProperties.POWERED)
    }

    override fun useItemOn(itemStack: ItemStack, blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, interactionHand: InteractionHand, blockHitResult: BlockHitResult): ItemInteractionResult {
        if (itemStack.`is`(Items.CLAY)) {
            if (!level.isClientSide && !blockState.getValue(hasClay) && level.setBlock(blockPos, blockState.setValue(hasClay, true), UPDATE_ALL)) {
                level.playSound(null, blockPos, SoundEvents.MUD_STEP, SoundSource.BLOCKS)
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide)
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    }

    override fun neighborChanged(
        blockState: BlockState,
        level: Level,
        blockPos: BlockPos,
        block: Block,
        blockPos2: BlockPos,
        bl: Boolean
    ) {
        level.setBlock(blockPos, blockState.setValue(BlockStateProperties.POWERED, level.hasNeighborSignal(blockPos)), UPDATE_ALL)
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl)
    }

    companion object {
        val hasClay: BooleanProperty = BooleanProperty.create("has_clay")

    }
}