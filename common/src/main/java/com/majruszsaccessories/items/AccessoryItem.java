package com.majruszsaccessories.items;

import com.majruszsaccessories.common.AccessoryHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class AccessoryItem extends Item {
	private final int boosterSlotsCount;

	public static Supplier< AccessoryItem > tier1() {
		return ()->new AccessoryItem( 1 );
	}

	public static Supplier< AccessoryItem > tier2() {
		return ()->new AccessoryItem( 2 );
	}

	public static Supplier< AccessoryItem > tier3() {
		return ()->new AccessoryItem( 3 );
	}

	protected AccessoryItem( int boosterSlotsCount ) {
		super( new Properties().stacksTo( 1 ) );

		this.boosterSlotsCount = boosterSlotsCount;
	}

	@Override
	public boolean isFoil( ItemStack itemStack ) {
		return AccessoryHolder.create( itemStack ).hasMaxBonus();
	}

	@Override
	public Rarity getRarity( ItemStack itemStack ) {
		return AccessoryHolder.create( itemStack ).getRarity();
	}

	public int getBoosterSlotsCount() {
		return this.boosterSlotsCount;
	}
}