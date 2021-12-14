package com.oyosite.ticon.cyberlib.data

import com.google.gson.JsonObject
import com.oyosite.ticon.cyberlib.CyberLib.MODID
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.world.World
import net.minecraft.util.registry.Registry

class CyberForgeRecipe(
    private val _id: Identifier,
    val addition: Ingredient,
    val nbtPredicates: List<CyberwareNbtPredicate>,
    val outputData: List<NbtTransformer>
) : Recipe<Inventory> {
    override fun matches(inv: Inventory, world: World): Boolean {
        val augment = inv.getStack(0)
        val cyberData = augment.getSubNbt("cyberdata")?:return false
        return addition.test(inv.getStack(1)) && nbtPredicates.all{it(cyberData)}
    }

    override fun craft(inv: Inventory): ItemStack {
        val otpt = inv.getStack(0).copy()
        otpt.count = 1
        val dat =otpt.getOrCreateSubNbt("cyberdata")
        outputData.forEach{it(dat)}
        return otpt
    }

    override fun fits(width: Int, height: Int): Boolean = width * height >= 2

    override fun getOutput(): ItemStack = ItemStack.EMPTY

    override fun getId(): Identifier = _id

    override fun getSerializer(): RecipeSerializer<*> = SERIALIZER

    override fun getType(): RecipeType<*> = TYPE

    companion object{
        val TYPE = object : RecipeType<CyberForgeRecipe>{}
        val SERIALIZER by lazy { Registry.register(Registry.RECIPE_SERIALIZER, Identifier(MODID, "cyber_forge"), object: RecipeSerializer<CyberForgeRecipe> {
            override fun read(id: Identifier, json: JsonObject): CyberForgeRecipe = CyberForgeRecipeFactory.DATA_TYPE.read(json)(id)
            override fun read(id: Identifier, buf: PacketByteBuf): CyberForgeRecipe = CyberForgeRecipeFactory.DATA_TYPE.receive(buf)(id)
            override fun write(buf: PacketByteBuf, recipe: CyberForgeRecipe) = CyberForgeRecipeFactory.DATA_TYPE.send(buf, CyberForgeRecipeFactory(recipe))
        }) }
    }
}