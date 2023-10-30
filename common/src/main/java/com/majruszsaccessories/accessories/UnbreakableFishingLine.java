package com.majruszsaccessories.accessories;

import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.accessories.components.FishingLureBonus;
import com.majruszsaccessories.accessories.components.TradeOffer;
import com.majruszsaccessories.common.AccessoryHandler;
import com.mlib.annotation.AutoInstance;

@AutoInstance
public class UnbreakableFishingLine extends AccessoryHandler {
	public UnbreakableFishingLine() {
		super( MajruszsAccessories.UNBREAKABLE_FISHING_LINE );

		this.add( FishingLureBonus.create( 0.2f ) )
			.add( AnglerTrophy.FishingDropChance.create( 0.005f ) )
			.add( AnglerTrophy.FishDropChance.create( 0.005f ) )
			.add( TradeOffer.create( 7 ) );
	}
}
