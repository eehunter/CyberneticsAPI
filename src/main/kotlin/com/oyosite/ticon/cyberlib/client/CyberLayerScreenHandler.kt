package com.oyosite.ticon.cyberlib.client

import com.oyosite.ticon.cyberlib.CyberLib.CYBER_LAYER_SCREEN_HANDLER
import com.oyosite.ticon.cyberlib.power.CyberwareLayerPower
import io.github.apace100.apoli.component.PowerHolderComponent
import io.github.apace100.apoli.power.PowerTypeRegistry
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtElement
import net.minecraft.nbt.NbtString
import net.minecraft.network.PacketByteBuf
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.slot.Slot
import net.minecraft.util.Identifier

class CyberLayerScreenHandler(syncId: Int, inv: PlayerInventory, id: Identifier) : ScreenHandler(CYBER_LAYER_SCREEN_HANDLER, syncId) {
    constructor(syncId: Int, inv: PlayerInventory, buf: PacketByteBuf) : this(syncId, inv, buf.readIdentifier())
    val power = PowerHolderComponent.KEY.get(inv.player).getPower(PowerTypeRegistry.get(id)) as CyberwareLayerPower
    init {
        power.slots.map{cyberSlot(power,it)}.forEach(::addSlot)
        var l: Int; var m = 0
        while (m < 3) { l = 0; while (l < 9) addSlot(Slot(inv, l + m * 9 + 9, 8 + (l++) * 18, 84 + m * 18)); ++m }
        m = 0; while (m < 9)addSlot(Slot(inv, m, 8 + (m++) * 18, 142))
    }

    override fun canInsertIntoSlot(stack: ItemStack, slot: Slot): Boolean = slot.canInsert(stack)
    override fun canInsertIntoSlot(slot: Slot): Boolean = true
    override fun canUse(player: PlayerEntity?): Boolean = power.canPlayerUse(player)
    private class CyberSlot(power:CyberwareLayerPower, slot: Int, x: Int, y: Int): Slot(power,slot,x,y){
        val power: CyberwareLayerPower get() = inventory as CyberwareLayerPower
        override fun canInsert(stack: ItemStack): Boolean = stack.getSubNbt("cyberdata")?.getList("slots", NbtElement.STRING_TYPE.toInt())?.map { (it as NbtString).asString()!! }?.any { it == "*" || it == power.slots[index] } ?:false
    }
    private fun cyberSlot(power:CyberwareLayerPower, slot: String) : Slot{
        val i = power.slots.indexOf(slot)
        val p = power.slotPos[i]
        return CyberSlot(power, i, p.first, p.second)
    }

    override fun transferSlot(player: PlayerEntity?, index: Int): ItemStack {
        val slot = slots[index]
        val oldStack = slot.stack ?: return ItemStack.EMPTY
        val stack = oldStack.copy()
        if (index < power.size()){ if(!insertItem(oldStack, power.size(), slots.size, true)) return ItemStack.EMPTY }
        else if (!insertItem(oldStack, 0, power.size(), false)) return ItemStack.EMPTY
        if (oldStack.isEmpty) slot.setStack(ItemStack.EMPTY)
        else slot.markDirty()
        return stack
    }
}