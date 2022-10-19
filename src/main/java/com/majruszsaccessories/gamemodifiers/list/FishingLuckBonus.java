package com.majruszsaccessories.gamemodifiers.list;

import com.majruszsaccessories.AccessoryHandler;
import com.majruszsaccessories.gamemodifiers.AccessoryModifier;
import com.majruszsaccessories.gamemodifiers.configs.AccessoryInteger;
import com.majruszsaccessories.items.AccessoryItem;
import com.mlib.attributes.AttributeHandler;
import com.mlib.gamemodifiers.Condition;
import com.mlib.gamemodifiers.contexts.OnPlayerTick;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;

import java.util.function.Supplier;

public class FishingLuckBonus extends AccessoryModifier {
	static final AttributeHandler LUCK_ATTRIBUTE = new AttributeHandler( "4010270c-9d57-4273-8a41-00985f1e4781", "FishingLuckBonus", Attributes.LUCK, AttributeModifier.Operation.ADDITION );
	final AccessoryInteger luck;

	public FishingLuckBonus( Supplier< ? extends AccessoryItem > item, String configKey ) {
		this( item, configKey, 3 );
	}

	public FishingLuckBonus( Supplier< ? extends AccessoryItem > item, String configKey, int luck ) {
		super( item, configKey, "", "" );
		this.luck = new AccessoryInteger( "fishing_luck", "Luck bonus during fishing.", false, luck, 1, 10 );

		OnPlayerTick.Context onTick = new OnPlayerTick.Context( this::updateLuck );
		onTick.addCondition( new Condition.Cooldown( 4, Dist.DEDICATED_SERVER, false ) ).addConfig( this.luck );

		this.addContext( onTick );
		this.addTooltip( this.luck, "majruszsaccessories.bonuses.fishing_luck" );
	}

	private void updateLuck( OnPlayerTick.Data data ) {
		LUCK_ATTRIBUTE.setValueAndApply( data.player, this.getLuck( data.player ) );
	}

	private int getLuck( Player player ) {
		if( player.fishing == null ) {
			return 0;
		}

		AccessoryHandler handler = AccessoryHandler.tryToCreate( player, this.item.get() );
		return handler != null ? this.luck.getValue( handler ) : 0;
	}
}
