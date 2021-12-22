package com.oyosite.ticon.cyberlib.block

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.block.Registry.BLOCKS
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block

open class CyberBlock(id: String, settings: Settings, val itemSettings: FabricItemSettings = FabricItemSettings()) : Block(settings) {
    val id = if(id.contains(":"))id else "$MODID:$id"
    companion object{
        fun <T : CyberBlock> T.register(): T {BLOCKS.add(this); return this;}
    }
}