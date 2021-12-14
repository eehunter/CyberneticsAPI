package com.oyosite.ticon.cyberlib.data

import io.github.apace100.calio.data.SerializableDataType
import net.minecraft.nbt.NbtCompound

class NbtTransformer: (NbtCompound)->Unit {
    override fun invoke(p1: NbtCompound) {
        TODO("Not yet implemented")
    }
    companion object{
        val DATA_TYPE: SerializableDataType<NbtTransformer> = TODO("Not yet implemented")
    }
}