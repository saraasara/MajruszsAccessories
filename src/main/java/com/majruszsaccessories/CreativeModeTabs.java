package com.majruszsaccessories;

import com.majruszsaccessories.accessories.AccessoryHolder;
import com.mlib.items.CreativeModeTabHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CreativeModeTabs {
	public static Supplier< CreativeModeTab > primary() {
		return ()->CreativeModeTab.builder()
			.withTabsBefore( net.minecraft.world.item.CreativeModeTabs.SPAWN_EGGS )
			.title( Component.translatable( "itemGroup.majruszsaccessories.primary" ) )
			.withTabFactory( Primary::new )
			.displayItems( CreativeModeTabs::definePrimaryItems )
			.build();
	}

	private static void definePrimaryItems( CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output ) {
		Stream.of(
			Registries.ADVENTURER_GUIDE,
			Registries.ANCIENT_SCARAB,
			Registries.ANGLER_TROPHY,
			Registries.CERTIFICATE_OF_TAMING,
			Registries.IDOL_OF_FERTILITY,
			Registries.LUCKY_ROCK,
			Registries.METAL_LURE,
			Registries.MINER_GUIDE,
			Registries.OVERWORLD_RUNE,
			Registries.PEACE_EMBLEM,
			Registries.PEACE_TREATY,
			Registries.SECRET_INGREDIENT,
			Registries.SRIRACHA,
			Registries.SWIMMER_GUIDE,
			Registries.TAMED_POTATO_BEETLE,
			Registries.ULTIMATE_GUIDE,
			Registries.UNBREAKABLE_FISHING_LINE,
			Registries.WHITE_FLAG
		).forEach( item->{
			for( int i = 0; i < 9; ++i ) {
				float bonus = Math.round( 100.0f * Mth.lerp( i / 8.0f, AccessoryHolder.BONUS_RANGE.from, AccessoryHolder.BONUS_RANGE.to ) ) / 100.0f;
				output.accept( AccessoryHolder.create( item.get() ).setBonus( bonus ).getItemStack() );
			}
		} );

		Stream.of(
			Registries.DICE,
			Registries.GOLDEN_DICE,
			Registries.OWL_FEATHER,
			Registries.HORSESHOE,
			Registries.GOLDEN_HORSESHOE,
			Registries.ONYX
		).forEach( item->output.accept( new ItemStack( item.get() ) ) );
	}

	private static class Primary extends CreativeModeTab {
		final Supplier< ItemStack > currentIcon;

		protected Primary( Builder builder ) {
			super( builder );

			this.currentIcon = CreativeModeTabHelper.buildMultiIcon( Stream.of(
				Registries.ADVENTURER_GUIDE,
				Registries.ANGLER_TROPHY,
				Registries.CERTIFICATE_OF_TAMING,
				Registries.IDOL_OF_FERTILITY,
				Registries.LUCKY_ROCK,
				Registries.OVERWORLD_RUNE,
				Registries.SECRET_INGREDIENT,
				Registries.TAMED_POTATO_BEETLE,
				Registries.WHITE_FLAG
			) );
		}

		@Override
		public ItemStack getIconItem() {
			return this.currentIcon.get();
		}
	}
}
