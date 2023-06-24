package com.oyosite.ticon.cyberlib.registry

import com.oyosite.ticon.cyberlib.data.CyberwareUpgradeLevel
import net.minecraft.util.Identifier

object CyberwareUpgradeRegistry {
    private val identifiedUpgrades: MutableMap<Identifier, List<CyberwareUpgradeLevel>> = mutableMapOf()

    fun clear() = identifiedUpgrades.clear()

    fun register(id: Identifier, upgrade: List<CyberwareUpgradeLevel>){
        if(identifiedUpgrades.containsKey(id)) throw IllegalArgumentException("Cyberware upgrade with id '$id' already exists.")
        identifiedUpgrades[id] = upgrade
    }

    operator fun set(id: Identifier, upgrade: List<CyberwareUpgradeLevel>) = update(id, upgrade)

    fun update(id: Identifier, upgrade: List<CyberwareUpgradeLevel>){
        if(identifiedUpgrades.containsKey(id)) remove(id)
        register(id, upgrade)
    }

    val identifiers: Set<Identifier> get() = identifiedUpgrades.keys
    val entries get() = identifiedUpgrades.entries
    val values get() = identifiedUpgrades.values


    operator fun get(id: Identifier) = identifiedUpgrades[id]//?: throw IllegalArgumentException("Could not get upgrade with id '$id'")
    operator fun contains(id: Identifier) = identifiedUpgrades.containsKey(id)
    operator fun contains(upgrade: List<CyberwareUpgradeLevel>) = identifiedUpgrades.containsValue(upgrade)


    fun remove(id: Identifier) { identifiedUpgrades.remove(id) }
    operator fun minusAssign(id: Identifier) = remove(id)


}