package com.oyosite.ticon.cyberlib.client

import com.oyosite.ticon.cyberlib.CyberLib.CYBER_LAYER_SCREEN_HANDLER
import com.oyosite.ticon.cyberlib.power.CyberwareLayerPower
import io.github.apace100.apoli.component.PowerHolderComponent
import io.github.apace100.apoli.power.PowerTypeRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.util.Identifier

class CyberLayerScreenHandler(syncId: Int, inv: PlayerInventory, id: Identifier) : ScreenHandler(CYBER_LAYER_SCREEN_HANDLER, syncId) {
    constructor(syncId: Int, inv: PlayerInventory, buf: PacketByteBuf) : this(syncId, inv, buf.readIdentifier())
    val power = PowerHolderComponent.KEY.get(inv.player).getPower(PowerTypeRegistry.get(id)) as CyberwareLayerPower
    init {
        power.slots.map{cyberSlot(power,it)}.forEach(::addSlot)
        var l: Int
        var m = 0
        while (m < 3) {
            l = 0
            while (l < 9) addSlot(Slot(inv, l + m * 9 + 9, 8 + (l++) * 18, 84 + m * 18))
            ++m
        }
        m = 0
        while (m < 9)addSlot(Slot(inv, m, 8 + (m++) * 18, 142))

    }
    override fun canUse(player: PlayerEntity?): Boolean = power.canPlayerUse(player)
    private fun cyberSlot(power:CyberwareLayerPower, slot: String) : Slot{
        val i = power.slots.indexOf(slot)
        val p = power.slotPos[i]
        return Slot(power, i, p.first, p.second)
    }
}