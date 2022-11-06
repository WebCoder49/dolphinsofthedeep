package io.github.webcoder49.dolphinsofthedeep.mixin.fundraising;

import io.github.webcoder49.dolphinsofthedeep.DolphinsOfTheDeep;
import io.github.webcoder49.dolphinsofthedeep.mixinInterfaces.fundraising.HasDonatedGameOptionInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

/**
 * A GameOption mixin for whether the user has donated or not.
 *
 * Mimics joinedFirstServer boolean; the Visitor interface has been made public using access wideners
 */
@Mixin(GameOptions.class)
public class HasDonatedGameOption implements HasDonatedGameOptionInterface {
    private boolean dotdHasDonated;

    @Inject(at=@At(value="INVOKE", target="Lnet/minecraft/client/option/GameOptions;load()V"), method="<init>")
    private void defaultValue(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        // Default value before loading from file
        this.dotdHasDonated = false;
//        DolphinsOfTheDeep.log(Level.WARN, "Has not donated yet.");
    }

    @Inject(at=@At("HEAD"), method="accept")
    private void syncValue(GameOptions.Visitor visitor, CallbackInfo ci) {
        this.dotdHasDonated = visitor.visitBoolean("dotdHasDonated", this.dotdHasDonated);
//        DolphinsOfTheDeep.log(Level.WARN, "Has donated? "+this.dotdHasDonated);
    }

    // GET/SET
    public boolean getDotdHasDonated() {
        return this.dotdHasDonated;
    }

    public void setDotdHasDonated(boolean dotdHasDonated) {
        DolphinsOfTheDeep.log(Level.WARN, "SET Has donated? "+this.dotdHasDonated);
//        this.dotdHasDonated = dotdHasDonated;
    }
}
