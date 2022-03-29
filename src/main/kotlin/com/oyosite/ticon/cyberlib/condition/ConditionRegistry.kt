
package com.oyosite.ticon.cyberlib.condition

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.data.CyberwareUpgradeLevel
import com.oyosite.ticon.cyberlib.data.SDKotlin
import com.oyosite.ticon.cyberlib.registry.CyberlibRegistries
import com.oyosite.ticon.cyberlib.util.cyberwareUpgrades
import com.oyosite.ticon.cyberlib.util.maxUpgradePoints
import com.oyosite.ticon.cyberlib.util.upgradePoints
import io.github.apace100.apoli.data.ApoliDataTypes
import io.github.apace100.apoli.data.ApoliDataTypes.COMPARISON
import io.github.apace100.apoli.power.factory.condition.ConditionFactory
import io.github.apace100.apoli.registry.ApoliRegistries.BLOCK_CONDITION
import io.github.apace100.apoli.registry.ApoliRegistries.ITEM_CONDITION
import io.github.apace100.apoli.util.Comparison
import io.github.apace100.calio.data.SerializableData
import io.github.apace100.calio.data.SerializableDataTypes.*
import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.BiFunction

@Suppress("SameParameterValue")
object ConditionRegistry {
    fun register(){
        register(ITEM_CONDITION, "$MODID:cyberware_upgrade", SDKotlin("id", IDENTIFIER)("level", INT, 0)("comparison", COMPARISON, Comparison.EQUAL), ConditionRegistry::upgradePredicate)
        register(ITEM_CONDITION, "$MODID:cyberware_upgrade_points", SDKotlin("comparison", COMPARISON, Comparison.GREATER_THAN_OR_EQUAL)("compare_to", DOUBLE)){data, p -> data.get<Comparison>("comparison").compare(p.upgradePoints, data.getDouble("compare_to"))}
        register(BLOCK_CONDITION, "$MODID:mining_level", SDKotlin("comparison", COMPARISON)("compare_to", DOUBLE)){data, block -> data.get<Comparison>("comparison").compare(MiningLevelManager.getRequiredMiningLevel(block.blockState)+0.0, data.getDouble("compare_to"))}
    }
    private fun upgradePredicate(sdi: SerializableData.Instance, stack: ItemStack): Boolean{
        val upgrade = CyberlibRegistries.UPGRADE[sdi.getId("id")]?:return false
        val cyberdata = stack.getSubNbt("cyberdata")?:return false
        val upgradeLevel = cyberdata.getCompound("upgrades").getInt(sdi.getId("id").toString())
        return sdi.get<Comparison>("comparison").compare(upgradeLevel.toDouble(), sdi.getInt("level").toDouble())
    }
    private fun <T> register(registry: Registry<ConditionFactory<T>>, id: String, sd: SerializableData, predicate: (SerializableData.Instance, T)->Boolean){
        with(ConditionFactory(Identifier(id), sd, BiFunction(predicate))){Registry.register(registry, serializerId, this)}
    }
}