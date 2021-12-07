package com.oyosite.ticon.cyberlib.power

import io.github.apace100.apoli.power.Power
import io.github.apace100.apoli.power.PowerType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier


class CyberwareLayerPower(type: PowerType<*>, entity: LivingEntity, val slots: List<String>, val slotPos: List<Pair<Int, Int>>) : Power(type, entity), Inventory {
    private val items = Array<ItemStack>(slots.size){ItemStack.EMPTY}


    override fun clear() { for (i in items.indices) items[i] = ItemStack.EMPTY }

    override fun size(): Int = slots.size

    override fun isEmpty(): Boolean = !items.all(ItemStack::isEmpty)

    override fun getStack(slot: Int): ItemStack = items[slot]

    fun getStack(slot: String): ItemStack = getStack(slots.indexOf(slot))

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        if (amount >= items[slot].count) removePowerForItem(items[slot],Identifier(slots[slot]),entity)
        return items[slot].split(amount)
    }

    override fun removeStack(slot: Int): ItemStack { val s = items[slot]; items[slot] = ItemStack.EMPTY; removePowerForItem(s, Identifier(slots[slot]), entity); return s }

    override fun setStack(slot: Int, stack: ItemStack?) { items[slot] = stack ?: ItemStack.EMPTY }

    override fun markDirty() {}

    override fun canPlayerUse(player: PlayerEntity?): Boolean = true

    override fun toTag(): NbtCompound {
        val tag = NbtCompound()
        slots.forEachIndexed{i, s -> tag.put(s, items[i].writeNbt(NbtCompound()))}
        return tag
    }

    override fun fromTag(e: NbtElement) {
        val tag = e as NbtCompound
        slots.forEachIndexed{i, s -> if (tag.contains(s)) {items[i] = ItemStack.fromNbt(tag.getCompound(s)); addPowerForItem(items[i], Identifier(s), entity)}}
    }
}