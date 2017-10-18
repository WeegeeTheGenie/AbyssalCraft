/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;

public class ContainerSpellbook extends Container {

	private final InventorySpellbook inventory;
	private final InventorySpellbookOutput output = new InventorySpellbookOutput();
	public ItemStack book;
	public Spell currentSpell;

	public ContainerSpellbook(InventoryPlayer inventoryPlayer, ItemStack book)
	{
		inventory = new InventorySpellbook(this, book);
		this.book = book;
		int i = (8 - 4) * 18 - 1;
		int j;
		int k;

		addSlotToContainer(new Slot(inventory, 0, 51, 91));
		addSlotToContainer(new Slot(inventory, 1, 51, 66));
		addSlotToContainer(new Slot(inventory, 2, 76, 87));
		addSlotToContainer(new Slot(inventory, 3, 65, 116));
		addSlotToContainer(new Slot(inventory, 4, 37, 116));
		addSlotToContainer(new Slot(inventory, 5, 26, 87));
		addSlotToContainer(new SlotSpellOutput(inventory, output, 0, 134, 91));

		for (j = 0; j < 3; ++j)
			for (k = 0; k < 9; ++k)
				addSlotToContainer(new Slot(inventoryPlayer, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));

		for (j = 0; j < 9; ++j)
			addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18, 161 + i));
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {

		return true;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn)
	{
		ItemStack[] stacks = new ItemStack[5];
		for(int i = 1; i < 6; i++)
			stacks[i-1] = inventory.getStackInSlot(i);

		if(inventory.getStackInSlot(0) == null) return;
		ItemStack stack = inventory.getStackInSlot(0).copy();

		stack.stackSize = 1;

		output.spell = currentSpell = SpellRegistry.instance().getSpell(((ItemNecronomicon) book.getItem()).getBookType(), stack, stacks);

		output.setInventorySlotContents(0, currentSpell != null ? SpellRegistry.instance().inscribeSpell(currentSpell, stack) : null);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);

		if (!playerIn.worldObj.isRemote)
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = inventory.removeStackFromSlot(i);

				if (itemstack != null)
					playerIn.dropItem(itemstack, false);
			}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = inventorySlots.get(par2);


		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 2)
			{
				if (!mergeItemStack(itemstack1, 3, 39, true))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (par2 >= 3 && par2 < 30)
				{
					if (!mergeItemStack(itemstack1, 30, 39, false))
						return null;
				}
				else if (par2 >= 30 && par2 < 39 && !mergeItemStack(itemstack1, 3, 30, false))
					return null;
			}
			else if (!mergeItemStack(itemstack1, 3, 39, false))
				return null;

			if (itemstack1.stackSize == 0)
				slot.putStack((ItemStack)null);
			else
				slot.onSlotChanged();

			if (itemstack1.stackSize == itemstack.stackSize)
				return null;

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {

		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() != null)
			if(getSlot(slot).getStack().getItem() instanceof ItemNecronomicon)
				return null;
		return super.slotClick(slot, dragType, clickType, player);
	}
}
