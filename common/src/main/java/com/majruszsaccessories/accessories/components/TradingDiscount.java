package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.events.OnTradesUpdated;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.platform.Side;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.contexts.base.CustomConditions;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.TooltipHelper;
import net.minecraft.world.item.trading.MerchantOffer;

public class TradingDiscount extends BonusComponent< AccessoryItem > {
	RangedFloat multiplier = new RangedFloat().id( "multiplier" ).maxRange( Range.of( 0.0f, 1.0f ) );

	public static ISupplier< AccessoryItem > create( float multiplier ) {
		return handler->new TradingDiscount( handler, multiplier );
	}

	protected TradingDiscount( BonusHandler< AccessoryItem > handler, float multiplier ) {
		super( handler );

		this.multiplier.set( multiplier, Range.of( 0.0f, 1.0f ) );

		OnTradesUpdated.listen( this::decreasePrices )
			.addCondition( CustomConditions.hasAccessory( this::getItem, data->data.player ) );

		this.addTooltip( "majruszsaccessories.bonuses.trading_discount", TooltipHelper.asPercent( this.multiplier ) );

		handler.getConfig()
			.define( "trading_discount", this.multiplier::define );
	}

	private void decreasePrices( OnTradesUpdated data ) {
		float discount = CustomConditions.getLastHolder().apply( this.multiplier );
		for( MerchantOffer offer : data.offers ) {
			int price = offer.getBaseCostA().getCount();
			float currentDiscount = ( float )offer.getSpecialPriceDiff() / price;
			offer.addToSpecialPriceDiff( Math.round( -( 1.0f + currentDiscount ) * discount * price ) );
		}
		if( Side.isLogicalServer() ) {
			this.spawnEffects( data );
		}
	}

	private void spawnEffects( OnTradesUpdated data ) {
		CustomConditions.getLastHolder()
			.getParticleEmitter()
			.count( 6 )
			.sizeBased( data.villager )
			.emit( data.getServerLevel() );
	}
}
