package com.oyosite.ticon.cyberlib.data

import com.google.gson.JsonObject
import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.registry.CyberlibRegistries
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener
import net.minecraft.resource.ResourceManager
import net.minecraft.util.Identifier
import net.minecraft.util.JsonHelper
import net.minecraft.util.registry.Registry

import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets


object UpgradeResourceReloadListener: SimpleSynchronousResourceReloadListener {
    override fun getFabricId(): Identifier = Identifier(MODID,"cyberware_upgrades")
    override fun reload(manager: ResourceManager) {
        manager.findResources("cyberware_upgrades"){it.endsWith(".json")}.forEach {
            try{
                val stream = manager.getResource(it).inputStream
                val reader = BufferedReader(InputStreamReader(stream, StandardCharsets.UTF_8))
                val builder = StringBuilder()
                var str = ""; while (reader.readLine().also { s -> str = s?:"" } != null) builder.append(str)
                val json: JsonObject = JsonHelper.deserialize(builder.toString())
                Registry.register(CyberlibRegistries.UPGRADE, it, CLSerializableDataTypes.CYBERWARE_UPGRADE_LEVELS.read(json["levels"]))
            }catch(e: Exception) { e.printStackTrace() }
        }
    }
}