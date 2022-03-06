@file:Suppress("Unused")

package com.oyosite.ticon.cyberlib.block

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.block.CyberBlock.Companion.register
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object Registry {
    val BLOCKS = mutableListOf<CyberBlock>()

    val DEBUG_BLOCK = DebugBlock().register()

    val CYBER_FORGE_TAG = TagKey.of(Registry.ITEM_KEY, Identifier(MODID, "valid_cyberforge"))!!

    fun registerBlocks() = BLOCKS.forEach{
        Registry.register(Registry.BLOCK, it.id, it)
        Registry.register(Registry.ITEM, it.id, BlockItem(it, it.itemSettings))
    }
}
