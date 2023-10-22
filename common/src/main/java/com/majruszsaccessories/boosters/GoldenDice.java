package com.majruszsaccessories.boosters;

import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.boosters.components.AccessoryDropChance;
import com.mlib.annotation.AutoInstance;

@AutoInstance
public class GoldenDice extends BoosterHandler {
	public GoldenDice() {
		super( MajruszsAccessories.GOLDEN_DICE );

		this.add( AccessoryDropChance.create( 0.4f ) );
	}
}
