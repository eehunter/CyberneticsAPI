package com.oyosite.ticon.cyberlib.item

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.item.Registry.ITEMS
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Item

@Suppress("LeakingThis")
open class CyberItem(id: String, settings: FabricItemSettings) : Item(settings) {
    val id = if(id.contains(":"))id else "$MODID:$id"
    companion object{
        fun <T : CyberItem> T.register() : T {ITEMS.add(this); return this}
    }
}