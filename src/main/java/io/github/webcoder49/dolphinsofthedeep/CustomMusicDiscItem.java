package io.github.webcoder49.dolphinsofthedeep;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class CustomMusicDiscItem extends MusicDiscItem {
    public CustomMusicDiscItem(int number, SoundEvent sound, Item.Settings settings) {
		super(number, sound, settings);
	}
}
