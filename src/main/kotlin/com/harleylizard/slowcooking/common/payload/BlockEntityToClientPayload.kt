package com.harleylizard.slowcooking.common.payload

import com.harleylizard.slowcooking.common.Slowcooking.Companion.resourceLocation
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

@JvmRecord
data class BlockEntityToClientPayload(
    val type: BlockEntityType<out BlockEntity>, val blockPos: BlockPos, val tag: CompoundTag) : CustomPacketPayload {

    override fun type() = Companion.type

    companion object {
        val type = CustomPacketPayload.Type<BlockEntityToClientPayload>("block_entity_to_client".resourceLocation)

        val codec: StreamCodec<FriendlyByteBuf, BlockEntityToClientPayload> = object : StreamCodec<FriendlyByteBuf, BlockEntityToClientPayload> {
            override fun decode(buf: FriendlyByteBuf): BlockEntityToClientPayload {
                val key = BuiltInRegistries.BLOCK_ENTITY_TYPE.getHolder(buf.readVarInt()).get().key()
                val type = BuiltInRegistries.BLOCK_ENTITY_TYPE.get(key)!!
                val blockPos = buf.readBlockPos()
                val tag = buf.readNbt()!!
                return BlockEntityToClientPayload(type, blockPos, tag)
            }

            override fun encode(buf: FriendlyByteBuf, payload: BlockEntityToClientPayload) {
                buf.writeVarInt(BuiltInRegistries.BLOCK_ENTITY_TYPE.getId(payload.type))
                buf.writeBlockPos(payload.blockPos)
                buf.writeNbt(payload.tag)
            }
        }

        fun BlockEntity.sync() {
            level?.takeUnless { it.isClientSide }?.let {
                for (player in PlayerLookup.tracking(this)) {
                    val tag = getUpdateTag((level as ServerLevel).registryAccess())
                    ServerPlayNetworking.send(player, BlockEntityToClientPayload(type, blockPos, tag))
                }
            }
        }

    }
}