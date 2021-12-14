package com.oyosite.ticon.cyberlib.client

import com.oyosite.ticon.cyberlib.CyberLib.CYBER_FORGE_SCREEN_HANDLER
import com.oyosite.ticon.cyberlib.data.CyberForgeRecipe
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.predicate.NbtPredicate
import net.minecraft.screen.ForgingScreenHandler
import net.minecraft.screen.ScreenHandlerContext

class CyberForgeScreenHandler(syncId: Int, inv: PlayerInventory) : ForgingScreenHandler(CYBER_FORGE_SCREEN_HANDLER, syncId, inv, ScreenHandlerContext.EMPTY) {

    private var recipe: CyberForgeRecipe? = null;

    override fun canUse(state: BlockState?): Boolean = true

    override fun canTakeOutput(player: PlayerEntity, present: Boolean): Boolean = recipe?.matches(input, player.world)?:false

    override fun onTakeOutput(player: PlayerEntity, stack: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun updateResult() {
        TODO("Not yet implemented")
    }
}