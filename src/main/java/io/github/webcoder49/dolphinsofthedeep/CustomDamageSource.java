package io.github.webcoder49.dolphinsofthedeep;

import net.minecraft.entity.damage.DamageSource;

public class CustomDamageSource extends DamageSource {

    public final String name;

    public CustomDamageSource(String name) {
        super(name);
		this.name = name;
	}
}
