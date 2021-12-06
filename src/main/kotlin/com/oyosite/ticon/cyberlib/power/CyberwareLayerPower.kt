package com.oyosite.ticon.cyberlib.power

import io.github.apace100.apoli.power.Power
import io.github.apace100.apoli.power.PowerType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

class CyberwareLayerPower(type: PowerType<*>, val entity: LivingEntity, val slots: List<String>) : Power(type, entity), Inventory {
    val items = Array<ItemStack>(slots.size){ItemStack.EMPTY}


    override fun clear() { for (i in items.indices) items[i] = ItemStack.EMPTY }

    override fun size(): Int = slots.size

    override fun isEmpty(): Boolean = !items.all(ItemStack::isEmpty)

    override fun getStack(slot: Int): ItemStack = items[slot]

    fun getStack(slot: String): ItemStack = getStack(slots.indexOf(slot))

    override fun removeStack(slot: Int, amount: Int): ItemStack = items[slot].split(amount)

    override fun removeStack(slot: Int): ItemStack { val s = items[slot]; items[slot] = ItemStack.EMPTY; return s }

    override fun setStack(slot: Int, stack: ItemStack?) { items[slot] = stack ?: ItemStack.EMPTY }

    override fun markDirty() {}

    override fun canPlayerUse(player: PlayerEntity?): Boolean = true

    
}