package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType
import net.minecraft.nbt.NbtCompound

class CyberwareNbtPredicate: (NbtCompound)->Boolean {
    override fun invoke(p1: NbtCompound): Boolean {
        TODO("Not yet implemented")
    }
    companion object {
        val DATA_TYPE: SerializableDataType<CyberwareNbtPredicate> = TODO("Not yet implemented")
    }
}