package com.majruszsaccessories.boosters;

import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.boosters.components.ExperienceBonus;
import com.majruszsaccessories.common.Component;
import com.majruszsaccessories.common.Handler;
import com.mlib.annotation.AutoInstance;
import com.mlib.contexts.OnLootGenerated;
import com.mlib.contexts.base.Condition;
import com.mlib.data.Serializable;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.monster.warden.Warden;

@AutoInstance
public class OwlFeather extends BoosterHandler {
	public OwlFeather() {
		super( MajruszsAccessories.OWL_FEATHER );

		this.add( ExperienceBonus.create( 0.15f ) )
			.add( VexDropChance.create() );
	}

	static class VexDropChance extends Component< BoosterItem > {
		float chance = 0.1f;

		public static ISupplier< BoosterItem > create() {
			return VexDropChance::new;
		}

		protected VexDropChance( Handler< BoosterItem > handler ) {
			super( handler );

			OnLootGenerated.listen( this::addToGeneratedLoot )
				.addCondition( Condition.isLogicalServer() )
				.addCondition( Condition.chance( ()->this.chance ) )
				.addCondition( data->data.lastDamagePlayer != null )
				.addCondition( data->data.entity instanceof Vex );

			Serializable config = handler.getConfig();
			config.defineFloat( "vex_drop_chance", ()->this.chance, x->this.chance = x );
		}
	}
}
