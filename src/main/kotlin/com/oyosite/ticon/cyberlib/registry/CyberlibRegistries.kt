package com.oyosite.ticon.cyberlib.registry

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.data.CyberwareUpgradeLevel
import io.github.apace100.calio.ClassUtil
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object CyberlibRegistries {
    val UPGRADE: Registry<List<CyberwareUpgradeLevel>> = FabricRegistryBuilder.createSimple(ClassUtil.castClass<List<CyberwareUpgradeLevel>>(List::class.java), Identifier(MODID, "cyberware_upgrade")).buildAndRegister()

}