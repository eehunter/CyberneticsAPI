package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType
import io.github.apace100.calio.data.SerializableDataTypes
import net.minecraft.util.Identifier

class CyberwareUpgradeLevel(val up: Double, val powers: List<Identifier>) {

    companion object{
        val TYPE = SerializableDataType(CyberwareUpgradeLevel::class.java, { buf, cul -> buf.writeDouble(cul.up); SerializableDataTypes.IDENTIFIERS.send(buf, cul.powers)}, { buf -> CyberwareUpgradeLevel(buf.readDouble(), SerializableDataTypes.IDENTIFIERS.receive(buf))}){ json -> CyberwareUpgradeLevel(json.asJsonObject["up"].asDouble, SerializableDataTypes.IDENTIFIERS.read(json.asJsonObject["powers"]))}
    }
}