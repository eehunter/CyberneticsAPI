package com.oyosite.ticon.cyberlib.block

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.client.CyberLayerScreenHandler
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory
import net.minecraft.block.BlockState
import net.minecraft.block.MapColor
import net.minecraft.block.Material
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
        override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler = CyberLayerScreenHandler(syncId, inv, Identifier(MODID, "generic_cyber_layer_power"))
        override fun getDisplayName(): Text = TranslatableText("menu.cyber_layer_menu.name")
        override fun writeScreenOpeningData(player: ServerPlayerEntity, buf: PacketByteBuf) { buf.writeIdentifier(Identifier(MODID, "generic_cyber_layer_power")) }
    }
    override fun createScreenHandlerFactory(state: BlockState, world: World, pos: BlockPos): NamedScreenHandlerFactory = SHF
    override fun onUse(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockHitResult): ActionResult {
        val screenHandlerFactory = state.createScreenHandlerFactory(world, pos)
        player.openHandledScreen(screenHandlerFactory)
        return ActionResult.SUCCESS
    }
}