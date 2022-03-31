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

open class AugmentItem(settings: FabricItemSettings) : Item(settings) {
    constructor(settings: FabricItemSettings.()->FabricItemSettings = {this}) : this(FabricItemSettings().settings())

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        val upgrades = stack.cyberwareUpgrades
        if(upgrades.isEmpty())tooltip.add(TranslatableText("tooltip.no_upgrades"))
        else {
            tooltip.add(TranslatableText("tooltip.upgrades"))
            upgrades.forEach { tooltip.add(LiteralText("- ").append(TranslatableText(it.translationKey))) }
        }
    }
}