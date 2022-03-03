package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType
import net.minecraft.util.Identifier

class CyberSlotData(val name: String, val x: Int, val y: Int, val icon: Identifier?) {

    companion object {
        val TYPE = SerializableDataType(CyberSlotData::class.java, { buf, csd ->
            buf.writeString(csd.name)
            buf.writeInt(csd.x)
            buf.writeInt(csd.y)
            buf.writeBoolean(csd.icon!=null)
            if(csd.icon!=null) buf.writeIdentifier(csd.icon)
        }, { buf -> CyberSlotData(buf.readString(), buf.readInt(), buf.readInt(), if(buf.readBoolean()) buf.readIdentifier() else null) }) { jsonElement ->
            val json = jsonElement.asJsonArray
            CyberSlotData(json[0].asString, json[1].asInt, json[2].asInt, if(json.size()>3)Identifier(json[3].asString)else null)
        }
        fun List<CyberSlotData>.ids() = map {it.name}
        fun List<CyberSlotData>.pos() = map {Pair(it.x, it.y)}
        fun List<CyberSlotData>.icons(): Map<Int, Identifier> {
            val map = mutableMapOf<Int,Identifier>()
            forEachIndexed { i, it -> if(it.icon!=null) map[i] = it.icon }
            return map.toMap()
        }
    }
}