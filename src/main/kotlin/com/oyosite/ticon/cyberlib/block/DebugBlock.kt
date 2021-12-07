package com.oyosite.ticon.cyberlib.block

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.MapColor
import net.minecraft.block.Material

class DebugBlock : CyberBlock("debug_block", FabricBlockSettings.of(Material.METAL, MapColor.MAGENTA))