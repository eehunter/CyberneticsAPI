package com.oyosite.ticon.cyberlib.block

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.client.CyberForgeScreenHandler
import com.oyosite.ticon.cyberlib.client.CyberLayerScreenHandler
import com.oyosite.ticon.cyberlib.power.CyberwareLayerPower
import io.github.apace100.apoli.component.PowerHolderComponent
import io.github.apace100.apoli.power.PowerTypeRegistry
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class DebugBlock : CyberBlock("debug_block", FabricBlockSettings.of(Material.METAL, MapColor.MAGENTA)){
    private object SHF : ExtendedScreenHandlerFactory{
        private fun powers(player: PlayerEntity) = PowerHolderComponent.KEY.get(player).getPowers(CyberwareLayerPower::class.java)
        private fun defaultPowerId(player: PlayerEntity) = PowerTypeRegistry.getId(powers(player)[0].type)
        fun canOpen(player: PlayerEntity) = powers(player).size > 0
        override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler = CyberLayerScreenHandler(syncId, inv, defaultPowerId(inv.player))
        override fun getDisplayName(): Text = TranslatableText("menu.cyberlib.cyber_layer.name")
        override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) { buf.writeIdentifier(defaultPowerId(player)) }
    }
    private object CFSHF : NamedScreenHandlerFactory {
        var pos : CachedBlockPosition? = null
        private val cachedPos get() = pos!!
        operator fun invoke(world: World, pos: BlockPos, forceLoad: Boolean = true) = this(CachedBlockPosition(world, pos, forceLoad))
        operator fun invoke(newPos: CachedBlockPosition): CFSHF{ pos = newPos; return this }
        override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler = CyberForgeScreenHandler(syncId, inv, cachedPos)
        override fun getDisplayName(): Text = TranslatableText("menu.cyberlib.cyberforge.name")
    }
    override fun createScreenHandlerFactory(state: BlockState, world: World, pos: BlockPos): NamedScreenHandlerFactory = SHF
    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val screenHandlerFactory = if(player.isSneaking) if (player.getStackInHand(hand).isEmpty && SHF.canOpen(player)) SHF else null else CFSHF(world, pos)
        if (screenHandlerFactory != null) player.openHandledScreen(screenHandlerFactory)
        return ActionResult.SUCCESS
    }
}