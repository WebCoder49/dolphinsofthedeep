package io.github.webcoder49.dolphinsofthedeep;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class CustomMusicDiscItem extends MusicDiscItem {
    private static final Map<SoundEvent, MusicDiscItem> MUSIC_DISCS = Maps.newHashMap();
	private final int comparatorOutput;
	private final SoundEvent sound;

    public CustomMusicDiscItem(int number, SoundEvent sound, Item.Settings settings) {
		super(number, sound, settings);
		this.comparatorOutput = number;
		this.sound = sound;
		MUSIC_DISCS.put(this.sound, this);
	}
}
