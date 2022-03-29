package com.oyosite.ticon.cyberlib.util

import com.oyosite.ticon.cyberlib.data.CyberwareUpgradeLevel
import com.oyosite.ticon.cyberlib.registry.CyberlibRegistries
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier

fun <T> MutableList<T>.push(item: T): T {
    add(item)
    return item
}

val ItemStack.cyberwareUpgrades: List<CyberwareUpgradeLevel> get() {
    val cyberdata = getSubNbt("cyberdata")?:return listOf()
    val upgradesNBT = cyberdata.getCompound("upgrades")?:return listOf()
    val upgrades = mutableListOf<CyberwareUpgradeLevel>()
    for(upg in upgradesNBT.keys) upgrades.add((CyberlibRegistries.UPGRADE[Identifier(upg)]?:continue).getOrNull(upgradesNBT.getInt(upg))?:continue)
    return upgrades
}
val ItemStack.upgradePoints: Double get() = cyberwareUpgrades.fold(0.0){r,t->r+t.up}
val ItemStack.maxUpgradePoints: Double get() {
    val cyberdata = getSubNbt("cyberdata")?:return 0.0
    if(!cyberdata.contains("up",NbtElement.DOUBLE_TYPE.toInt())) return 4.0
    return cyberdata.getDouble("up")
}