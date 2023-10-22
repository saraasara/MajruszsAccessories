package com.majruszsaccessories.accessories.components;

import com.majruszsaccessories.accessories.AccessoryItem;
import com.majruszsaccessories.common.Handler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.contexts.base.CustomConditions;
import com.majruszsaccessories.tooltip.TooltipHelper;
import com.mlib.contexts.OnAnimalTamed;
import com.mlib.contexts.base.Condition;
import com.mlib.data.Serializable;
import com.mlib.entity.AttributeHandler;
import com.mlib.math.AnyPos;
import com.mlib.math.Range;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Horse;

public class TamingStrongerAnimals extends AccessoryComponent {
	final AttributeHandler health;
	final AttributeHandler damage;
	final AttributeHandler speed;
	final AttributeHandler jumpHeight;
	RangedFloat bonus = new RangedFloat().id( "bonus" ).maxRange( Range.of( 0.0f, 10.0f ) );

	public static ISupplier< AccessoryItem > create( float bonus ) {
		return handler->new TamingStrongerAnimals( handler, bonus );
	}

	protected TamingStrongerAnimals( Handler< AccessoryItem > handler, float bonus ) {
		super( handler );

		this.health = new AttributeHandler( "%s_health_multiplier".formatted( handler.getId() ), ()->Attributes.MAX_HEALTH, AttributeModifier.Operation.MULTIPLY_BASE );
		this.damage = new AttributeHandler( "%s_damage_multiplier".formatted( handler.getId() ), ()->Attributes.ATTACK_DAMAGE, AttributeModifier.Operation.MULTIPLY_BASE );
		this.speed = new AttributeHandler( "%s_speed_multiplier".formatted( handler.getId() ), ()->Attributes.MOVEMENT_SPEED, AttributeModifier.Operation.MULTIPLY_BASE );
		this.jumpHeight = new AttributeHandler( "%s_jump_height_multiplier".formatted( handler.getId() ), ()->Attributes.JUMP_STRENGTH, AttributeModifier.Operation.MULTIPLY_BASE );
		this.bonus.set( bonus, Range.of( 0.0f, 1.0f ) );

		OnAnimalTamed.listen( this::applyBonuses )
			.addCondition( Condition.isLogicalServer() )
			.addCondition( CustomConditions.hasAccessory( this::getItem, data->data.tamer ) );

		this.addTooltip( "majruszsaccessories.bonuses.animal_attributes", TooltipHelper.asPercent( this.bonus ) );

		Serializable config = handler.getConfig();
		config.defineCustom( "animal_bonus", this.bonus::define );
	}

	private void applyBonuses( OnAnimalTamed data ) {
		float bonus = CustomConditions.getLastHolder().apply( this.bonus );
		this.health.setValue( bonus ).apply( data.animal );
		if( this.damage.hasAttribute( data.animal ) ) {
			this.damage.setValue( bonus ).apply( data.animal );
		}
		if( data.animal instanceof Horse horse ) {
			this.jumpHeight.setValue( bonus ).apply( horse );
			this.speed.setValue( bonus ).apply( horse );
		}
		data.animal.setHealth( data.animal.getMaxHealth() );
		this.spawnEffects( data );
	}

	private void spawnEffects( OnAnimalTamed data ) {
		float width = data.animal.getBbWidth();
		float height = data.animal.getBbHeight();

		CustomConditions.getLastHolder()
			.getParticleEmitter()
			.count( Math.round( 4 * ( 1.0f + width + height ) ) )
			.offset( ()->AnyPos.from( width, height, width ).mul( 0.5 ).vec3() )
			.emit( data.getServerLevel(), AnyPos.from( data.animal.position() ).add( 0.0, height * 0.5, 0.0 ).vec3() );
	}
}
