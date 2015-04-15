/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeHooks;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemEthaxiumPickaxe extends ItemTool {

	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});
	private static Set<Block> effectiveBlocks = Sets.newHashSet( new Block[] {AbyssalCraft.ethaxium, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumslab1, AbyssalCraft.ethaxiumslab2, AbyssalCraft.ethaxiumstairs, AbyssalCraft.ethaxiumblock, AbyssalCraft.materializer, AbyssalCraft.darkethaxiumbrick, AbyssalCraft.darkethaxiumpillar, AbyssalCraft.darkethaxiumstairs, AbyssalCraft.darkethaxiumslab1, AbyssalCraft.darkethaxiumslab2, AbyssalCraft.darkethaxiumfence});

	public ItemEthaxiumPickaxe(ToolMaterial enumToolMaterial)
	{
		super(2, enumToolMaterial, blocksEffectiveAgainst);
		setHarvestLevel("pickaxe", 8);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	@Override
	public boolean func_150897_b(Block par1Block)
	{
		if (par1Block == Blocks.obsidian)
			return toolMaterial.getHarvestLevel() == 3;
		if (par1Block == Blocks.diamond_block || par1Block == Blocks.diamond_ore)
			return toolMaterial.getHarvestLevel() >= 2;
			if (par1Block == Blocks.gold_block || par1Block == Blocks.gold_ore)
				return toolMaterial.getHarvestLevel() >= 2;
				if (par1Block == Blocks.iron_block || par1Block == Blocks.iron_ore)
					return toolMaterial.getHarvestLevel() >= 1;
					if (par1Block == Blocks.lapis_block || par1Block == Blocks.lapis_ore)
						return toolMaterial.getHarvestLevel() >= 1;
						if (par1Block == Blocks.redstone_block || par1Block == Blocks.redstone_ore)
							return toolMaterial.getHarvestLevel() >= 2;
							if (par1Block.getMaterial() == Material.rock)
								return true;
							return par1Block.getMaterial() == Material.iron;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		if(effectiveBlocks.contains(block))
			return efficiencyOnProperMaterial * 10;
		if (ForgeHooks.isToolEffective(stack, block, meta))
			return efficiencyOnProperMaterial;
		return super.getDigSpeed(stack, block, meta);
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	@Override
	public float func_150893_a(ItemStack par1ItemStack, Block par2Block) {
		if (par2Block != null && (par2Block.getMaterial() == Material.iron || par2Block.getMaterial() == Material.rock))
			return efficiencyOnProperMaterial;
		else
			return super.func_150893_a(par1ItemStack, par2Block);
	}
}