@file:Suppress("LeakingThis")

package com.oyosite.ticon.cyberlib.block

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block

open class CyberBlock(val id: String, settings: Settings, val itemSettings: FabricItemSettings = FabricItemSettings()) : Block(settings) {
    init{ BLOCKS.add(this) }
}