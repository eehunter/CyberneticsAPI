package com.oyosite.ticon.cyberlib.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.DyeableItem

@Suppress("unused")
open class DyeableAugmentItem(settings: FabricItemSettings): AugmentItem(settings), DyeableItem {
    constructor(settings: FabricItemSettings.()->FabricItemSettings): this(FabricItemSettings().settings())
}