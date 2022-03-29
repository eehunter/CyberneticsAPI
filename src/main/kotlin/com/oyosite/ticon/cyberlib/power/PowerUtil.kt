package com.oyosite.ticon.cyberlib.power

import com.oyosite.ticon.cyberlib.util.cyberwareUpgrades
import io.github.apace100.apoli.component.PowerHolderComponent
import io.github.apace100.apoli.power.Power
import io.github.apace100.apoli.power.PowerType
import io.github.apace100.apoli.power.PowerTypeRegistry
import io.github.apace100.apoli.power.PowerTypes
import io.github.apace100.apoli.power.factory.PowerFactories
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier

fun getPowersForItem(item: ItemStack, slot: Identifier, entity: LivingEntity): List<PowerType<*>>{
    val powers = mutableListOf<Identifier>()
    item.cyberwareUpgrades.forEach { powers.addAll(it.powers) }
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
