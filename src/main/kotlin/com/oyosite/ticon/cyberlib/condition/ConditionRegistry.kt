
package com.oyosite.ticon.cyberlib.condition

import com.oyosite.ticon.cyberlib.CyberLib.MODID
import com.oyosite.ticon.cyberlib.data.CyberwareUpgradeLevel
import com.oyosite.ticon.cyberlib.data.SDKotlin
import com.oyosite.ticon.cyberlib.registry.CyberlibRegistries
import com.oyosite.ticon.cyberlib.util.cyberwareUpgrades
import com.oyosite.ticon.cyberlib.util.maxUpgradePoints
import com.oyosite.ticon.cyberlib.util.upgradePoints
import io.github.apace100.apoli.power.factory.condition.ConditionFactory
import io.github.apace100.apoli.registry.ApoliRegistries.ITEM_CONDITION
import io.github.apace100.calio.data.SerializableData
import io.github.apace100.calio.data.SerializableDataTypes.*
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.BiFunction

@Suppress("SameParameterValue")
object ConditionRegistry {
    fun register(){
        register(ITEM_CONDITION, "$MODID:cyberware_upgrade", SDKotlin("id", IDENTIFIER)("level", INT, 1)("allowHigherLevels", BOOLEAN, false), ConditionRegistry::upgradePredicate)
        register(ITEM_CONDITION, "$MODID:cyberware_upgrade_points", SDKotlin("min_up_available", DOUBLE, 2.0), ::upgradePointPredicate)
    }
    private fun upgradePointPredicate(sdi: SerializableData.Instance, stack: ItemStack): Boolean{
        val pts = stack.maxUpgradePoints-stack.upgradePoints
        return pts >= sdi.getDouble("min_up_available")
    }
    private fun upgradePredicate(sdi: SerializableData.Instance, stack: ItemStack): Boolean{
        val upgrade = CyberlibRegistries.UPGRADE[sdi.getId("id")]?:return false
        val validLevels = mutableListOf<CyberwareUpgradeLevel>()
        val lvl = sdi.getInt("level")
        if(lvl>upgrade.size)return false
        if(lvl<0)validLevels.addAll(upgrade)
        else if(sdi.getBoolean("allowHigherLevels")) validLevels.addAll(upgrade.subList(lvl-1, upgrade.size))
        else validLevels.add(upgrade[lvl-1])
        if(validLevels.size<1)return false
        return stack.cyberwareUpgrades.any { validLevels.contains(it) }
    }
    private fun <T> register(registry: Registry<ConditionFactory<T>>, id: String, sd: SerializableData, predicate: (SerializableData.Instance, T)->Boolean){
        with(ConditionFactory(Identifier(id), sd, BiFunction(predicate))){Registry.register(registry, serializerId, this)}
    }
}