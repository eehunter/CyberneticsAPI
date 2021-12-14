package com.oyosite.ticon.cyberlib.data

import com.google.gson.JsonElement
import com.oyosite.ticon.cyberlib.data.CLSerializableDataTypes.NBT_TRANSFORMER_LIST
import io.github.apace100.apoli.power.factory.condition.ConditionFactory
import io.github.apace100.apoli.power.factory.condition.ConditionTypes
import io.github.apace100.calio.data.SerializableDataType
import io.github.apace100.calio.data.SerializableDataTypes
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

typealias JFunction<T, R> = java.util.function.Function<T, R>
typealias ApoliCondition<T> = ConditionFactory<T>.Instance

class CyberForgeRecipeFactory(val id: Identifier?, val addition: Ingredient, val condition: ApoliCondition<ItemStack>, val outputData: List<NbtTransformer>) : (Identifier) -> CyberForgeRecipe {
    constructor(recipe: CyberForgeRecipe) : this(recipe.id, recipe.addition, recipe.condition, recipe.outputData)
    override fun invoke(id: Identifier) = CyberForgeRecipe(this.id?:id, addition, condition, outputData)
    companion object{
        private val send = BiConsumer<PacketByteBuf, CyberForgeRecipeFactory> { buf, factory ->
            buf.writeIdentifier(factory.id)
            factory.addition.write(buf)
            factory.condition.write(buf)
            NBT_TRANSFORMER_LIST.send(buf, factory.condition)
        }
        private val receive = JFunction { buf: PacketByteBuf ->
            CyberForgeRecipeFactory(buf.readIdentifier(), Ingredient.fromPacket(buf), ConditionTypes.ITEM.read(buf), NBT_TRANSFORMER_LIST.receive(buf))
        }
        private val read = JFunction { jsonElement:JsonElement ->
            val json = jsonElement.asJsonObject
            CyberForgeRecipeFactory(null, SerializableDataTypes.INGREDIENT.read(json["addition"]), ConditionTypes.ITEM.read(json["condition"]), NBT_TRANSFORMER_LIST.read(json["outputData"]))
        }
        val DATA_TYPE: SerializableDataType<CyberForgeRecipeFactory> = object: SerializableDataType<CyberForgeRecipeFactory>(CyberForgeRecipeFactory::class.java, send, receive, read) {}
    }
}