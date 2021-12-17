@file:Suppress("SameParameterValue")

package com.oyosite.ticon.cyberlib.action

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.data.SDKotlin
import io.github.apace100.apoli.power.factory.action.ActionFactory
import io.github.apace100.apoli.registry.ApoliRegistries.ITEM_ACTION
import io.github.apace100.calio.data.SerializableData
import io.github.apace100.calio.data.SerializableDataTypes
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.BiConsumer

object ActionRegistry {
    fun register(){
        register(ITEM_ACTION, "$MODID:nbt_merge", SDKotlin()("data", SerializableDataTypes.NBT)) { data, ws -> ws.right.orCreateNbt.copyFrom(data["data"]) }
    }

    private fun <T> register(registry: Registry<ActionFactory<T>>, id: String, sd: SerializableData, effect: (SerializableData.Instance, T) -> Unit){
        with(ActionFactory(Identifier(id), sd, BiConsumer(effect))){ Registry.register(registry, serializerId, this) }
    }
}