@file:Suppress("Unused")

package com.oyosite.ticon.cyberlib.block

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.block.CyberBlock.Companion.register
import net.minecraft.item.BlockItem
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey

object Registry {
    val BLOCKS = mutableListOf<CyberBlock>()

    val DEBUG_BLOCK = DebugBlock().register()

    val CYBER_FORGE_TAG = TagKey.of(RegistryKeys.BLOCK, Identifier(MODID, "valid_cyberforge"))!!

    fun registerBlocks() = BLOCKS.forEach{
        Registry.register(Registries.BLOCK, it.id, it)
        Registry.register(Registries.ITEM, it.id, BlockItem(it, it.itemSettings))
    }
}
