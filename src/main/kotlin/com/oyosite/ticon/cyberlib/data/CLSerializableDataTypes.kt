package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType


object CLSerializableDataTypes {
    val CYBER_SLOT_DATA_LIST: SerializableDataType<MutableList<CyberSlotData>> = SerializableDataType.list(CyberSlotData.TYPE)
    //val NBT_TRANSFORMER_LIST: SerializableDataType<MutableList<NbtTransformer>> = SerializableDataType.list(NbtTransformer.DATA_TYPE)


}