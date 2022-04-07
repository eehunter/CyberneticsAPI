package com.oyosite.ticon.cyberlib.item

import com.oyosite.ticon.cyberlib.util.cyberwareUpgrades
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.world.World

@Suppress("unused")
open class AugmentItem(settings: FabricItemSettings) : Item(settings) {
    constructor(settings: FabricItemSettings.()->FabricItemSettings = {this}) : this(FabricItemSettings().settings())

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val upgrades = stack.cyberwareUpgrades
        if(upgrades.isEmpty())tooltip.add(TranslatableText("tooltip.cyberlib.no_upgrades"))
        else {
            tooltip.add(TranslatableText("tooltip.cyberlib.upgrades"))
            upgrades.forEach { if(it.translationKey.isNotEmpty())tooltip.add(LiteralText("- ").append(TranslatableText(it.translationKey))) }
        }
    }
}