package io.github.webcoder49.dolphinsofthedeep.item;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class CustomMusicDiscItem extends MusicDiscItem {
    public CustomMusicDiscItem(int number, SoundEvent sound, Item.Settings settings, int lengthInSeconds) {
		super(number, sound, settings, lengthInSeconds);
	}
}
