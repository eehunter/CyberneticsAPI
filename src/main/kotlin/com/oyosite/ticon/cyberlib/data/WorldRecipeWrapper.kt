package com.oyosite.ticon.cyberlib.data

import net.minecraft.inventory.Inventory
import net.minecraft.world.World

class WorldInventoryWrapper(inv: Inventory, val world: World) : Inventory by inv