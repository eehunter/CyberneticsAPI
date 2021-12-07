package com.oyosite.ticon.cyberlib.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item

@Suppress("LeakingThis")
open class CyberItem(val id: String, settings: FabricItemSettings) : Item(settings) {
    init {
        ITEMS.add(this)
    }
}