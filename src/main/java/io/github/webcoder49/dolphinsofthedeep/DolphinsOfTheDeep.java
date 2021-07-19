package io.github.webcoder49.dolphinsofthedeep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DolphinsOfTheDeep implements ModInitializer {

    // Items - create instances
    public static final Item DOLPHIN_SADDLE = new ItemDolphinSaddle(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16));
    public static final Item LEATHER_DOLPHIN_ARMOUR = new ItemLeatherDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(8));
    public static final Item IRON_DOLPHIN_ARMOUR = new ItemIronDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(4));
    public static final Item GOLD_DOLPHIN_ARMOUR = new ItemGoldDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(3));
    public static final Item DIAMOND_DOLPHIN_ARMOUR = new ItemDiamondDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item NETHERITE_DOLPHIN_ARMOUR = new ItemNetheriteDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));

    // Item Groups
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.create(
    new Identifier("dolphinsofthedeep", "items"))
    .icon(() -> new ItemStack(DIAMOND_DOLPHIN_ARMOUR))
    .appendItems(stacks -> {
        stacks.add(new ItemStack(DOLPHIN_SADDLE));
        stacks.add(ItemStack.EMPTY);
        stacks.add(new ItemStack(LEATHER_DOLPHIN_ARMOUR));
        stacks.add(new ItemStack(IRON_DOLPHIN_ARMOUR));
        stacks.add(new ItemStack(GOLD_DOLPHIN_ARMOUR));
        stacks.add(new ItemStack(DIAMOND_DOLPHIN_ARMOUR));
        stacks.add(new ItemStack(NETHERITE_DOLPHIN_ARMOUR));
    })
    .build();

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "dolphinsofthedeep";
    public static final String MOD_NAME = "Dolphins of the Deep";

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");

        // Register Items
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "dolphin_saddle"), DOLPHIN_SADDLE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "leather_dolphin_armour"), LEATHER_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "iron_dolphin_armour"), IRON_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "gold_dolphin_armour"), GOLD_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_dolphin_armour"), DIAMOND_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "netherite_dolphin_armour"), NETHERITE_DOLPHIN_ARMOUR);
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}