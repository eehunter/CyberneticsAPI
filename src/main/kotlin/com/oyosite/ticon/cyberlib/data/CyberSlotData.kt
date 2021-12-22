package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType

class CyberSlotData(val name: String, val x: Int, val y: Int) {

    companion object {
        val TYPE = SerializableDataType(CyberSlotData::class.java, { buf, csd ->
            buf.writeString(csd.name)
            buf.writeInt(csd.x)
            buf.writeInt(csd.y)
        }, { buf -> CyberSlotData(buf.readString(), buf.readInt(), buf.readInt()) }) { jsonElement ->
            val json = jsonElement.asJsonArray
            CyberSlotData(json[0].asString, json[1].asInt, json[2].asInt)
        }
        fun List<CyberSlotData>.ids() = map {it.name}
        fun List<CyberSlotData>.pos() = map {Pair(it.x, it.y)}
    }
}