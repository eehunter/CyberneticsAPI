package com.oyosite.ticon.cyberlib.registry

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.data.CyberwareUpgradeLevel
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier

object CyberlibRegistryKeys {
    val UPGRADE: RegistryKey<Registry<List<CyberwareUpgradeLevel>>> = RegistryKey.ofRegistry(Identifier("$MODID:cyberware_upgrade"))

}