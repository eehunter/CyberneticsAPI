@file:Suppress("Unused")

package com.oyosite.ticon.cyberlib.block

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.util.registry.Registry


val BLOCKS = mutableListOf<CyberBlock>()

val DEBUG_BLOCK = DebugBlock()

fun registerBlocks() = BLOCKS.forEach{
    Registry.register(Registry.BLOCK, it.id, it)
    Registry.register(Registry.ITEM, it.id, BlockItem(it, it.itemSettings))
}
