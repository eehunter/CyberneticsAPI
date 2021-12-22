@file:Suppress("SameParameterValue")

package com.oyosite.ticon.cyberlib.action

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.data.SDKotlin
import com.oyosite.ticon.cyberlib.util.MCPair
import io.github.apace100.apoli.power.factory.action.ActionFactory
import io.github.apace100.apoli.registry.ApoliRegistries.ITEM_ACTION
import io.github.apace100.calio.data.SerializableData
import io.github.apace100.calio.data.SerializableDataTypes
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtElement
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import java.util.function.BiConsumer

object ActionRegistry {
    fun register(){
        register(ITEM_ACTION, "$MODID:nbt_merge", SDKotlin("data", SerializableDataTypes.NBT)) { data, ws -> ws.right.orCreateNbt.copyFrom(data["data"]) }
        register(ITEM_ACTION, "$MODID:nbt_edit", SDKotlin("data", SerializableDataTypes.NBT)("path", SerializableDataTypes.STRING)("create_path", SerializableDataTypes.BOOLEAN, true), ::nbtEdit)
    }
    // As of writing this comment, this method is entirely untested. Use at your own risk.
    private fun nbtEdit(data: SerializableData.Instance, ws: MCPair<World, ItemStack>){
        val createPath = data.getBoolean("create_path")
        val stack = ws.right
        val path = data.getString("path").split(".")
        var nbt = if(createPath)stack.orCreateNbt!! else stack.nbt?:return
        path.forEach {
            nbt = if(nbt.contains(it, NbtElement.COMPOUND_TYPE.toInt())) nbt.getCompound(it)
            else if (createPath) { nbt.put(it, NbtCompound()); nbt.getCompound(it) }
            else return
        }
        nbt.copyFrom(data.get("data"))
    }

    private fun <T> register(registry: Registry<ActionFactory<T>>, id: String, sd: SerializableData, effect: (SerializableData.Instance, T) -> Unit){
        with(ActionFactory(Identifier(id), sd, BiConsumer(effect))){ Registry.register(registry, serializerId, this) }
    }
}