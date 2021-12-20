package com.oyosite.ticon.cyberlib.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import io.github.apace100.apoli.power.factory.action.ActionFactory
import io.github.apace100.apoli.power.factory.action.ActionTypes
import io.github.apace100.apoli.power.factory.condition.ConditionFactory
import io.github.apace100.apoli.power.factory.condition.ConditionTypes
import io.github.apace100.calio.data.SerializableDataType
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import net.minecraft.util.Pair
import net.minecraft.world.World
import java.util.function.BiConsumer

typealias JFunction<T, R> = java.util.function.Function<T, R>
typealias ApoliCondition<T> = ConditionFactory<T>.Instance
typealias ApoliAction<T> = ActionFactory<T>.Instance
typealias ItemAction = ApoliAction<Pair<World, ItemStack>>

class CyberForgeRecipeFactory(val id: Identifier?, val addition: ApoliCondition<ItemStack>, val condition: ApoliCondition<ItemStack>, val outputData: ItemAction, val blockCondition: ApoliCondition<CachedBlockPosition>) : (Identifier) -> CyberForgeRecipe {
    constructor(recipe: CyberForgeRecipe) : this(recipe.id, recipe.addition, recipe.condition, recipe.outputData, recipe.blockPredicate)
    override fun invoke(id: Identifier) = CyberForgeRecipe(this.id?:id, addition, condition, outputData, blockCondition)
    companion object{
        private val TAG_CONDITION = ConditionTypes.BLOCK.read(with(JsonObject()){
            addProperty("type", "apoli:in_tag")
            addProperty("tag", "cyberlib:valid_cyberforge")
            this
        })
        private val send = BiConsumer<PacketByteBuf, CyberForgeRecipeFactory> { buf, factory ->
            buf.writeIdentifier(factory.id)
            factory.addition.write(buf)
            factory.condition.write(buf)
            factory.outputData.write(buf)
            factory.blockCondition.write(buf)
        }
        private val receive = JFunction { buf: PacketByteBuf ->
            CyberForgeRecipeFactory(buf.readIdentifier(), ConditionTypes.ITEM.read(buf), ConditionTypes.ITEM.read(buf), ActionTypes.ITEM.read(buf), ConditionTypes.BLOCK.read(buf))
        }
        private val read = JFunction { jsonElement:JsonElement ->
            val json = jsonElement.asJsonObject
            CyberForgeRecipeFactory(null, ConditionTypes.ITEM.read(json["addition"]), ConditionTypes.ITEM.read(json["prerequisites"]), ActionTypes.ITEM.read(json["outputAction"]), if(json.has("blockCondition"))ConditionTypes.BLOCK.read(json["blockCondition"])else TAG_CONDITION)
        }
        val DATA_TYPE: SerializableDataType<CyberForgeRecipeFactory> = object: SerializableDataType<CyberForgeRecipeFactory>(CyberForgeRecipeFactory::class.java, send, receive, read) {}
    }
}