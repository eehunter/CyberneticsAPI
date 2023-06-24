package com.oyosite.ticon.cyberlib.item

import com.oyosite.ticon.cyberlib.util.cyberwareUpgrades
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.Text
import net.minecraft.world.World

@Suppress("unused")
open class AugmentItem(settings: FabricItemSettings) : Item(settings) {
    constructor(settings: FabricItemSettings.()->FabricItemSettings = {this}) : this(FabricItemSettings().settings())

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val upgrades = stack.cyberwareUpgrades
        if(upgrades.isEmpty())tooltip.add(Text.translatable("tooltip.cyberlib.no_upgrades"))
        else {
            tooltip.add(Text.translatable("tooltip.cyberlib.upgrades"))
            upgrades.forEach { if(it.translationKey.isNotEmpty())tooltip.add(Text.literal("- ").append(Text.translatable(it.translationKey))) }
        }
    }
}