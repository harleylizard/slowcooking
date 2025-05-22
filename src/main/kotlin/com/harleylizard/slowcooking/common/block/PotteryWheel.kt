package com.harleylizard.slowcooking.common.block

import com.harleylizard.slowcooking.common.SlowcookingBlockEntities
import com.harleylizard.slowcooking.common.Util
import com.harleylizard.slowcooking.common.blockentity.PotteryWheelBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.BooleanProperty
import net.minecraft.world.phys.BlockHitResult

class PotteryWheel(properties: Properties) : Block(properties), EntityBlock {

    init {
        registerDefaultState(stateDefinition.any()
            .setValue(hasClay, false).setValue(BlockStateProperties.POWERED, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(hasClay).add(BlockStateProperties.POWERED)
    }

    override fun useWithoutItem(blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, blockHitResult: BlockHitResult): InteractionResult {
        if (blockState.hasClay) {
            val client = level.isClientSide
            if (!client) {
                if (player.isCrouching) {
                    if (level.setBlock(blockPos, blockState.setValue(hasClay, false), UPDATE_ALL)) {
                        Util.takeBack(player, Items.CLAY, blockPos)
                        level.playSound(null, blockPos, SoundEvents.MUD_HIT, SoundSource.BLOCKS)
                    }
                } else if (blockState.powered) {
                    level.getBlockEntity(blockPos, SlowcookingBlockEntities.potteryWheel).ifPresent { entity ->
                        entity.use()
                        level.playSound(null, blockPos, SoundEvents.MUD_HIT, SoundSource.BLOCKS)
                    }
                }
            }
            return InteractionResult.sidedSuccess(client)
        }
        return super.useWithoutItem(blockState, level, blockPos, player, blockHitResult)
    }

    override fun useItemOn(itemStack: ItemStack, blockState: BlockState, level: Level, blockPos: BlockPos, player: Player, interactionHand: InteractionHand, blockHitResult: BlockHitResult): ItemInteractionResult {
        if (itemStack.`is`(Items.CLAY)) {
            val client = level.isClientSide
            if (!client && !blockState.hasClay && level.setBlock(blockPos, blockState.setValue(hasClay, true), UPDATE_ALL)) {
                itemStack.shrink(1)
                level.playSound(null, blockPos, SoundEvents.MUD_STEP, SoundSource.BLOCKS)
                level.getBlockEntity(blockPos, SlowcookingBlockEntities.potteryWheel).ifPresent { entity -> entity.set() }
            }
            return ItemInteractionResult.sidedSuccess(client)
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    }

    override fun neighborChanged(blockState: BlockState, level: Level, blockPos: BlockPos, block: Block, blockPos2: BlockPos, bl: Boolean) {
        animate(level, blockPos, blockState)
        super.neighborChanged(blockState, level, blockPos, block, blockPos2, bl)
    }

    override fun updateShape(blockState: BlockState, direction: Direction, blockState2: BlockState, levelAccessor: LevelAccessor, blockPos: BlockPos, blockPos2: BlockPos): BlockState {
        animate(levelAccessor, blockPos, blockState)
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2)
    }

    private fun animate(accessor: LevelAccessor, blockPos: BlockPos, blockState: BlockState) {
        accessor.setBlock(blockPos, blockState.setValue(BlockStateProperties.POWERED, accessor.hasNeighborSignal(blockPos)), UPDATE_ALL)
        accessor.getBlockEntity(blockPos, SlowcookingBlockEntities.potteryWheel).ifPresent { entity ->
            entity.animation.animate(entity)
        }
    }

    override fun newBlockEntity(blockPos: BlockPos, blockState: BlockState): BlockEntity? {
        return SlowcookingBlockEntities.potteryWheel.create(blockPos, blockState)
    }

    override fun <T : BlockEntity?> getTicker(level: Level, blockState: BlockState, blockEntityType: BlockEntityType<T>): BlockEntityTicker<T>? {
        return if (level.isClientSide)
            BaseEntityBlock.createTickerHelper(blockEntityType, SlowcookingBlockEntities.potteryWheel, PotteryWheelBlockEntity::clientTick) else
            BaseEntityBlock.createTickerHelper(blockEntityType, SlowcookingBlockEntities.potteryWheel, PotteryWheelBlockEntity::serverTick)
    }

    companion object {
        val hasClay: BooleanProperty = BooleanProperty.create("has_clay")

        val BlockState.hasClay: Boolean get() = getValue(Companion.hasClay)

        val BlockState.powered: Boolean get() = getValue(BlockStateProperties.POWERED)

    }
}