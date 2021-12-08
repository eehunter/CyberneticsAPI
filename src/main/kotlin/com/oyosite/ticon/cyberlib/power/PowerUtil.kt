package com.oyosite.ticon.cyberlib.power

import io.github.apace100.apoli.component.PowerHolderComponent
import io.github.apace100.apoli.power.Power
import io.github.apace100.apoli.power.PowerType
import io.github.apace100.apoli.power.PowerTypeRegistry
import io.github.apace100.apoli.power.PowerTypes
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier

fun getPowersForItem(item: ItemStack, slot: Identifier, entity: LivingEntity): List<PowerType<*>>{
    val nbt = item.getSubNbt("cyberdata") ?: return listOf()
    if (!nbt.contains("powers")) return listOf()
    val p = nbt.getList("powers", NbtElement.STRING_TYPE.toInt())
    val powers = List(p.size) { Identifier(p.getString(it)) }
    return powers.filter { PowerTypeRegistry.contains(it) }.map { PowerTypeRegistry.get(it) }
}
fun canAddPower(item: ItemStack, slot: Identifier, entity: LivingEntity): Boolean = !item.isEmpty && getPowersForItem(item, slot, entity).isNotEmpty()
fun addPowerForItem(item: ItemStack, slot: Identifier, entity: LivingEntity){
    if (item.isEmpty) return
    val powers = getPowersForItem(item, slot, entity)
    val powerComp = PowerHolderComponent.KEY.get(entity)
    powers.forEach { if (!powerComp.hasPower(it, slot)) powerComp.addPower(it, slot) }
}
fun removePowerForItem(item: ItemStack, slot: Identifier, entity: LivingEntity) = PowerHolderComponent.KEY.get(entity).removeAllPowersFromSource(slot)
