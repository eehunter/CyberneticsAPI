@file:Suppress("Unused")

package com.oyosite.ticon.cyberlib.item

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.util.registry.Registry

val ITEMS = mutableListOf<CyberItem>()

val AUGMENT = CyberItem("augment", FabricItemSettings().maxCount(1))

fun registerItems() = ITEMS.forEach{ Registry.register(Registry.ITEM, it.id, it) }