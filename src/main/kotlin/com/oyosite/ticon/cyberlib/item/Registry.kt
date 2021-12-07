package com.oyosite.ticon.cyberlib.item

import net.minecraft.util.registry.Registry

val ITEMS = mutableListOf<CyberItem>()



fun registerItems() = ITEMS.forEach{ Registry.register(Registry.ITEM, it.id, it) }