package com.oyosite.ticon.cyberlib.util

import io.github.apace100.apoli.power.factory.action.ActionFactory
import io.github.apace100.apoli.power.factory.condition.ConditionFactory
import io.github.apace100.calio.data.SerializableDataTypes
import net.minecraft.item.ItemStack
import net.minecraft.util.Pair
import net.minecraft.world.World

val NBT_DATA = SerializableDataTypes.NBT!!
val STRING_DATA = SerializableDataTypes.STRING!!
val BOOLEAN_DATA = SerializableDataTypes.BOOLEAN!!

typealias JFunction<T, R> = java.util.function.Function<T, R>
typealias ApoliCondition<T> = ConditionFactory<T>.Instance
typealias ApoliAction<T> = ActionFactory<T>.Instance
typealias ItemAction = ApoliAction<Pair<World, ItemStack>>

typealias MCPair<A, B> = Pair<A, B>