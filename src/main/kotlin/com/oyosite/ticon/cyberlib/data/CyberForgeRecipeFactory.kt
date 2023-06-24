package com.oyosite.ticon.cyberlib.data

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.oyosite.ticon.cyberlib.util.ApoliCondition
import com.oyosite.ticon.cyberlib.util.ItemAction
import com.oyosite.ticon.cyberlib.util.JFunction
import io.github.apace100.apoli.power.factory.action.ActionTypes
import io.github.apace100.apoli.power.factory.condition.ConditionTypes
import io.github.apace100.calio.data.SerializableDataType
import io.github.apace100.calio.data.SerializableDataTypes
import net.minecraft.block.pattern.CachedBlockPosition
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.util.Identifier
import java.util.function.BiConsumer

class CyberForgeRecipeFactory(val id: Identifier?, val addition: ApoliCondition<ItemStack>, val condition: ApoliCondition<ItemStack>, val outputData: ItemAction, val blockCondition: ApoliCondition<CachedBlockPosition>, val outputStackTemplate: ItemStack?) : (Identifier) -> CyberForgeRecipe {
    constructor(recipe: CyberForgeRecipe) : this(recipe.id, recipe.addition, recipe.condition, recipe.outputData, recipe.blockPredicate, recipe.outputStackTemplate)
    override fun invoke(id: Identifier) = CyberForgeRecipe(this.id?:id, addition, condition, outputData, blockCondition, outputStackTemplate)
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
            if(factory.outputStackTemplate!=null){
                buf.writeBoolean(true)
                buf.writeItemStack(factory.outputStackTemplate)
            } else buf.writeBoolean(false)

        }
        private val receive = JFunction { buf: PacketByteBuf ->
            CyberForgeRecipeFactory(buf.readIdentifier(), ConditionTypes.ITEM.read(buf), ConditionTypes.ITEM.read(buf), ActionTypes.ITEM.read(buf), ConditionTypes.BLOCK.read(buf), if(buf.readBoolean())buf.readItemStack()else null)
        }
        private val read = JFunction { jsonElement:JsonElement ->
            val json = jsonElement.asJsonObject
            println("Reading cyberforge recipe from json")
            CyberForgeRecipeFactory(null, ConditionTypes.ITEM.read(json["addition"]), ConditionTypes.ITEM.read(json["prerequisites"]), ActionTypes.ITEM.read(json["outputAction"]), if(json.has("blockCondition"))ConditionTypes.BLOCK.read(json["blockCondition"])else TAG_CONDITION, if(json.has("new_stack"))SerializableDataTypes.ITEM_STACK.read(json["new_stack"])else null)
        }
        val DATA_TYPE: SerializableDataType<CyberForgeRecipeFactory> = object: SerializableDataType<CyberForgeRecipeFactory>(CyberForgeRecipeFactory::class.java, send, receive, read) {}
    }
}