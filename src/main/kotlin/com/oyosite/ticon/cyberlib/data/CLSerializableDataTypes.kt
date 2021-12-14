package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier


object CLSerializableDataTypes {
    val CYBER_SLOT_DATA_LIST: SerializableDataType<MutableList<CyberSlotData>> = SerializableDataType.list(CyberSlotData.TYPE)
    val CYBERWARE_NBT_PREDICATE_LIST: SerializableDataType<MutableList<CyberwareNbtPredicate>> = SerializableDataType.list(CyberwareNbtPredicate.DATA_TYPE)
    val NBT_TRANSFORMER_LIST: SerializableDataType<MutableList<NbtTransformer>> = SerializableDataType.list(NbtTransformer.DATA_TYPE)


}