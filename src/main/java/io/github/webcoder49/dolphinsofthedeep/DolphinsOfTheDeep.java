package io.github.webcoder49.dolphinsofthedeep;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.webcoder49.dolphinsofthedeep.materials.armour.ArmourGoldenDelphinium;
import io.github.webcoder49.dolphinsofthedeep.materials.tools.CustomAxeItem;
import io.github.webcoder49.dolphinsofthedeep.materials.tools.CustomHoeItem;
import io.github.webcoder49.dolphinsofthedeep.materials.tools.CustomPickaxeItem;

/* Import other mod classes */

import io.github.webcoder49.dolphinsofthedeep.materials.tools.ToolsGoldenDelphinium;
import io.github.webcoder49.dolphinsofthedeep.materials.tools.ToolsEmeraldDelphinium;
import io.github.webcoder49.dolphinsofthedeep.materials.tools.ToolsDiamondDelphinium;


public class DolphinsOfTheDeep implements ModInitializer {

    // Items - create instances
    public static final Item DOLPHIN_SADDLE = new ItemDolphinSaddle(new FabricItemSettings().group(ItemGroup.MISC).maxCount(16));
    public static final Item LEATHER_DOLPHIN_ARMOUR = new ItemLeatherDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(8));
    public static final Item IRON_DOLPHIN_ARMOUR = new ItemIronDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(4));
    public static final Item GOLD_DOLPHIN_ARMOUR = new ItemGoldDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(3));
    public static final Item DIAMOND_DOLPHIN_ARMOUR = new ItemDiamondDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    public static final Item NETHERITE_DOLPHIN_ARMOUR = new ItemNetheriteDolphinArmour(new FabricItemSettings().group(ItemGroup.COMBAT).maxCount(1));
    
    public static final Item GOLDEN_DELPHINIUM_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item EMERALD_DELPHINIUM_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item DIAMOND_DELPHINIUM_INGOT = new Item(new FabricItemSettings().group(ItemGroup.MISC));

    // Blocks - create instances
    public static final Block GOLDEN_DELPHINIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).hardness(20.0f).resistance(10.0f));
    public static final Block EMERALD_DELPHINIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).hardness(10.0f).resistance(5.0f));
    public static final Block DIAMOND_DELPHINIUM_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).hardness(30.0f).resistance(15.0f));

    public static final SeaLaser SEA_LASER_BLOCK = new SeaLaser(FabricBlockSettings.of(Material.METAL).hardness(1000000.0f).resistance(1000000.0f));

    // Tools - create instances
    public static ToolItem GOLDEN_DELPHINIUM_SHOVEL = new ShovelItem(ToolsGoldenDelphinium.INSTANCE, 2.5F, -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem GOLDEN_DELPHINIUM_SWORD = new SwordItem(ToolsGoldenDelphinium.INSTANCE, 4, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static ToolItem GOLDEN_DELPHINIUM_PICKAXE = new CustomPickaxeItem(ToolsGoldenDelphinium.INSTANCE, 2, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem GOLDEN_DELPHINIUM_AXE = new CustomAxeItem(ToolsGoldenDelphinium.INSTANCE, 7, -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem GOLDEN_DELPHINIUM_HOE = new CustomHoeItem(ToolsGoldenDelphinium.INSTANCE, 1, -3, new Item.Settings().group(ItemGroup.TOOLS));
    
    public static ToolItem EMERALD_DELPHINIUM_SHOVEL = new ShovelItem(ToolsEmeraldDelphinium.INSTANCE, 1.5F, -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem EMERALD_DELPHINIUM_SWORD = new SwordItem(ToolsEmeraldDelphinium.INSTANCE, 3, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static ToolItem EMERALD_DELPHINIUM_PICKAXE = new CustomPickaxeItem(ToolsEmeraldDelphinium.INSTANCE, 1, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem EMERALD_DELPHINIUM_AXE = new CustomAxeItem(ToolsEmeraldDelphinium.INSTANCE, 5, -2.2F, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem EMERALD_DELPHINIUM_HOE = new CustomHoeItem(ToolsEmeraldDelphinium.INSTANCE, 1, -3, new Item.Settings().group(ItemGroup.TOOLS));

    public static ToolItem DIAMOND_DELPHINIUM_SHOVEL = new ShovelItem(ToolsDiamondDelphinium.INSTANCE, 5.5F, -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem DIAMOND_DELPHINIUM_SWORD = new SwordItem(ToolsDiamondDelphinium.INSTANCE, 7, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));
    public static ToolItem DIAMOND_DELPHINIUM_PICKAXE = new CustomPickaxeItem(ToolsDiamondDelphinium.INSTANCE, 5, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem DIAMOND_DELPHINIUM_AXE = new CustomAxeItem(ToolsDiamondDelphinium.INSTANCE, 9, -3, new Item.Settings().group(ItemGroup.TOOLS));
    public static ToolItem DIAMOND_DELPHINIUM_HOE = new CustomHoeItem(ToolsDiamondDelphinium.INSTANCE, 1, 0, new Item.Settings().group(ItemGroup.TOOLS));

    // Armour - create instances

    public static ArmorMaterial GOLDEN_DELPHINIUM_ARMOUR = new ArmourGoldenDelphinium();
    public static ArmorItem GOLDEN_DELPHINIUM_HELMET = new ArmorItem(GOLDEN_DELPHINIUM_ARMOUR, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT));
    public static ArmorItem GOLDEN_DELPHINIUM_CHESTPLATE = new ArmorItem(GOLDEN_DELPHINIUM_ARMOUR, EquipmentSlot.CHEST, new Item.Settings().group(ItemGroup.COMBAT));
    public static ArmorItem GOLDEN_DELPHINIUM_LEGGINGS = new ArmorItem(GOLDEN_DELPHINIUM_ARMOUR, EquipmentSlot.LEGS, new Item.Settings().group(ItemGroup.COMBAT));
    public static ArmorItem GOLDEN_DELPHINIUM_BOOTS = new ArmorItem(GOLDEN_DELPHINIUM_ARMOUR, EquipmentSlot.FEET, new Item.Settings().group(ItemGroup.COMBAT));

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

        log(Level.INFO, "Hello, World! Hello, Minecraft!");

        /* Register Items */
        // Dolphin saddles and Armour
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "dolphin_saddle"), DOLPHIN_SADDLE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "leather_dolphin_armour"), LEATHER_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "iron_dolphin_armour"), IRON_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "gold_dolphin_armour"), GOLD_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_dolphin_armour"), DIAMOND_DOLPHIN_ARMOUR);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "netherite_dolphin_armour"), NETHERITE_DOLPHIN_ARMOUR);

        // Delphinium ingots
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_ingot"), GOLDEN_DELPHINIUM_INGOT);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_ingot"), EMERALD_DELPHINIUM_INGOT);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_ingot"), DIAMOND_DELPHINIUM_INGOT);

        // Delphinium tools
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_shovel"), GOLDEN_DELPHINIUM_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_sword"), GOLDEN_DELPHINIUM_SWORD);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_pickaxe"), GOLDEN_DELPHINIUM_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_axe"), GOLDEN_DELPHINIUM_AXE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_hoe"), GOLDEN_DELPHINIUM_HOE);

        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_shovel"), EMERALD_DELPHINIUM_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_sword"), EMERALD_DELPHINIUM_SWORD);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_pickaxe"), EMERALD_DELPHINIUM_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_axe"), EMERALD_DELPHINIUM_AXE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_hoe"), EMERALD_DELPHINIUM_HOE);

        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_shovel"), DIAMOND_DELPHINIUM_SHOVEL);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_sword"), DIAMOND_DELPHINIUM_SWORD);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_pickaxe"), DIAMOND_DELPHINIUM_PICKAXE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_axe"), DIAMOND_DELPHINIUM_AXE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_hoe"), DIAMOND_DELPHINIUM_HOE);

        // Delphinium armour

        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_helmet"), GOLDEN_DELPHINIUM_HELMET);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_chestplate"), GOLDEN_DELPHINIUM_CHESTPLATE);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_leggings"), GOLDEN_DELPHINIUM_LEGGINGS);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_boots"), GOLDEN_DELPHINIUM_BOOTS);

        /* Register Blocks */
        // Delphinium blocks
        Registry.register(Registry.BLOCK, new Identifier("dolphinsofthedeep", "golden_delphinium_block"), GOLDEN_DELPHINIUM_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "golden_delphinium_block"), new BlockItem(GOLDEN_DELPHINIUM_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.BLOCK, new Identifier("dolphinsofthedeep", "emerald_delphinium_block"), EMERALD_DELPHINIUM_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "emerald_delphinium_block"), new BlockItem(EMERALD_DELPHINIUM_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        Registry.register(Registry.BLOCK, new Identifier("dolphinsofthedeep", "diamond_delphinium_block"), DIAMOND_DELPHINIUM_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "diamond_delphinium_block"), new BlockItem(DIAMOND_DELPHINIUM_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
        
        // Sea laser
        Registry.register(Registry.BLOCK, new Identifier("dolphinsofthedeep", "sea_laser"), SEA_LASER_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("dolphinsofthedeep", "sea_laser"), new BlockItem(SEA_LASER_BLOCK, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}