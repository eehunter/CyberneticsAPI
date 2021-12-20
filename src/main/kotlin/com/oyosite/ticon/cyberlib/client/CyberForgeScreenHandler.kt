package com.oyosite.ticon.cyberlib.client

import com.oyosite.ticon.cyberlib.CyberLib.CYBER_FORGE_SCREEN_HANDLER
import com.oyosite.ticon.cyberlib.data.CyberForgeRecipe
import net.minecraft.block.BlockState
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ForgingScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.math.BlockPos

class CyberForgeScreenHandler(syncId: Int, inv: PlayerInventory, private val cachedBlockPos: CachedBlockPosition) : ForgingScreenHandler(CYBER_FORGE_SCREEN_HANDLER, syncId, inv, ScreenHandlerContext.EMPTY) {
    constructor(syncId: Int, inv: PlayerInventory, blockPos: BlockPos = BlockPos(0,0,0)) : this(syncId, inv, CachedBlockPosition(inv.player.world, blockPos, false))

    private var recipe: CyberForgeRecipe? = null
    private val world get() = player.world

    override fun canUse(state: BlockState?): Boolean = true

    override fun canTakeOutput(player: PlayerEntity, present: Boolean): Boolean = recipe?.matches(input, world)?:false && recipe?.blockPredicate?.test(cachedBlockPos)?:false

    override fun onTakeOutput(player: PlayerEntity, stack: ItemStack) {
        stack.onCraft(world, player, stack.count)
        output.unlockLastRecipe(player)
        input.getStack(0).count--
        input.getStack(1).count--
    }

    override fun updateResult() {
        val recipes = world.recipeManager.getAllMatches(CyberForgeRecipe.TYPE, input, world)
        if (recipes.isEmpty()) output.setStack(0, ItemStack.EMPTY)
        else {
            recipe = recipes[0]
            val otpt = recipe!!.craft(input, world)
            output.lastRecipe = recipe
            output.setStack(0, otpt)
        }
    }
}