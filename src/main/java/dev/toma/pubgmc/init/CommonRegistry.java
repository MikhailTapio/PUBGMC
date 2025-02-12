package dev.toma.pubgmc.init;

import dev.toma.pubgmc.Pubgmc;
import dev.toma.pubgmc.api.games.GameBattleRoyale;
import dev.toma.pubgmc.api.games.GameBombDefuse;
import dev.toma.pubgmc.api.games.GameInactive;
import dev.toma.pubgmc.client.renderer.item.gun.*;
import dev.toma.pubgmc.common.BlockBuilder;
import dev.toma.pubgmc.common.HorizontalBlockBuilder;
import dev.toma.pubgmc.common.blocks.*;
import dev.toma.pubgmc.common.entity.*;
import dev.toma.pubgmc.common.entity.bot.EntityAIPlayer;
import dev.toma.pubgmc.common.entity.controllable.EntityVehicle;
import dev.toma.pubgmc.common.entity.throwables.EntityFlashBang;
import dev.toma.pubgmc.common.entity.throwables.EntityFragGrenade;
import dev.toma.pubgmc.common.entity.throwables.EntityMolotov;
import dev.toma.pubgmc.common.entity.throwables.EntitySmokeGrenade;
import dev.toma.pubgmc.common.entity.vehicles.EntityVehicleDacia;
import dev.toma.pubgmc.common.entity.vehicles.EntityVehicleUAZ;
import dev.toma.pubgmc.common.items.*;
import dev.toma.pubgmc.common.items.armor.ArmorBase;
import dev.toma.pubgmc.common.items.armor.ItemGhillie;
import dev.toma.pubgmc.common.items.armor.ItemNVGoggles;
import dev.toma.pubgmc.common.items.attachment.*;
import dev.toma.pubgmc.common.items.guns.*;
import dev.toma.pubgmc.common.items.heal.*;
import dev.toma.pubgmc.common.tileentity.*;
import dev.toma.pubgmc.config.ConfigPMC;
import dev.toma.pubgmc.config.common.CFGWeapon;
import dev.toma.pubgmc.config.common.CFGWeapons;
import dev.toma.pubgmc.util.Constants;
import dev.toma.pubgmc.util.helper.AttachmentHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class CommonRegistry {

    private static int entityID = 0;
    private static List<ItemBlock> ITEM_BLOCKS = new ArrayList<>();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                BlockBuilder.create("roadasphalt", Material.ROCK).soundType(SoundType.STONE).build(),
                BlockBuilder.create("schoolwall", Material.ROCK).soundType(SoundType.STONE).build(),
                BlockBuilder.create("schoolroof", Material.ROCK).soundType(SoundType.STONE).build(),
                BlockBuilder.create("schoolwindow", Material.GLASS).soundType(SoundType.GLASS).setGlass().build(),
                new BlockAirdrop("airdrop", Material.IRON),
                BlockBuilder.create("darkwood", Material.WOOD).soundType(SoundType.WOOD).build(),
                new BlockLootSpawner("loot_spawner", Material.ROCK, SoundType.STONE, MapColor.BLACK),
                new BlockPlayerCrate("player_crate", Material.WOOD, SoundType.WOOD, MapColor.BROWN),
                HorizontalBlockBuilder.create("chair", Material.WOOD).soundType(SoundType.WOOD).setTransparent().build(),
                HorizontalBlockBuilder.create("table", Material.WOOD).soundType(SoundType.WOOD).setTransparent().build(),
                BlockBuilder.create("ruinswall", Material.ROCK).soundType(SoundType.STONE).mapColor(MapColor.WHITE_STAINED_HARDENED_CLAY).build(),
                BlockBuilder.create("blueglass", Material.GLASS).setGlass().soundType(SoundType.GLASS).build(),
                new BlockTarget("target"),
                BlockBuilder.create("light", Material.IRON).soundType(SoundType.METAL).setTransparent().light(1f)
                        .aabb(new AxisAlignedBB(0.1, 0.75, 0.1, 0.9, 1.0, 0.9), Block.NULL_AABB).build(),
                BlockBuilder.create("crate", Material.WOOD).soundType(SoundType.WOOD).transparency(false, true).build(),
                BlockBuilder.create("crates", Material.IRON).soundType(SoundType.METAL).transparency(false, true).build(),
                HorizontalBlockBuilder.create("bush", Material.PLANTS).soundType(SoundType.PLANT).aabb(Block.FULL_BLOCK_AABB, Block.NULL_AABB)
                        .renderType(BlockRenderLayer.CUTOUT).setTransparent().build(),
                new BlockPlant("wheat", Material.PLANTS, SoundType.PLANT, MapColor.YELLOW),
                HorizontalBlockBuilder.create("prop1", Material.PLANTS).soundType(SoundType.PLANT).setProp().build(),
                HorizontalBlockBuilder.create("prop2", Material.PLANTS).soundType(SoundType.PLANT).setProp().description("My own prop.. For making the mod I guess").build(),
                HorizontalBlockBuilder.create("prop3", Material.PLANTS).soundType(SoundType.PLANT).setProp().build(),
                HorizontalBlockBuilder.create("prop4", Material.IRON).soundType(SoundType.METAL).setProp().build(),
                HorizontalBlockBuilder.create("prop5", Material.CLOTH).soundType(SoundType.CLOTH).setProp().build(),
                BlockBuilder.create("prop6", Material.ROCK).soundType(SoundType.GLASS).aabb(Block.FULL_BLOCK_AABB, Block.NULL_AABB).transparency(false, false).description("OfficialMajonaise's prop for doing great models").renderType(BlockRenderLayer.CUTOUT).build(),
                HorizontalBlockBuilder.create("fence", Material.IRON).soundType(SoundType.METAL).setTransparent()
                        .aabb(new AxisAlignedBB(0.4, 0, 0, 0.6, 1, 1), new AxisAlignedBB(0, 0, 0.4, 1, 1, 0.6), new AxisAlignedBB(0.4, 0, 0, 0.6, 1, 1), new AxisAlignedBB(0, 0, 0.4, 1, 1, 0.6))
                        .build(),
                HorizontalBlockBuilder.create("concrete", Material.ROCK).soundType(SoundType.STONE).setTransparent().build(),
                BlockBuilder.create("electricpole", Material.WOOD).soundType(SoundType.WOOD)
                        .aabb(new AxisAlignedBB(0.2, 0, 0.2, 0.8, 1, 0.8)).setTransparent().build(),
                HorizontalBlockBuilder.create("electricpoletop", Material.WOOD).soundType(SoundType.WOOD).setTransparent().build(),
                HorizontalBlockBuilder.create("electriccable", Material.IRON).soundType(SoundType.METAL).setTransparent()
                        .setPassable().build(),
                BlockBuilder.create("radiotower", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                BlockBuilder.create("radiotowertop", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                new BlockGunWorkbench("gun_workbench"),
                new BlockBigAirdrop("big_airdrop"),
                new BlockOre("copper_ore"),
                new BlockLandMine("landmine"),
                HorizontalBlockBuilder.create("desk", Material.WOOD)
                        .soundType(SoundType.WOOD).transparency(false, false)
                        .build(),
                HorizontalBlockBuilder.create("chair1", Material.WOOD)
                        .soundType(SoundType.WOOD).transparency(false, false)
                        .build(),
                HorizontalBlockBuilder.create("storagebase", Material.WOOD).aabb(new AxisAlignedBB(0, 0, 0, 1, 1, 0.85), new AxisAlignedBB(0.15, 0, 0, 1, 1, 1), new AxisAlignedBB(0, 0, 0.15, 1, 1, 1), new AxisAlignedBB(0, 0, 0, 0.85, 1, 1))
                        .soundType(SoundType.WOOD).transparency(false, false)
                        .build(),
                HorizontalBlockBuilder.create("storagetop", Material.WOOD).aabb(new AxisAlignedBB(0, 0, 0, 1, 0.8, 0.85), new AxisAlignedBB(0.15, 0, 0, 1, 0.8, 1), new AxisAlignedBB(0, 0, 0.15, 1, 0.8, 1), new AxisAlignedBB(0, 0, 0, 0.85, 0.8, 1))
                        .soundType(SoundType.WOOD).transparency(false, false)
                        .build(),
                new BlockWindow("window1x1", BlockWindow.WindowType.WINDOW_1X1),
                new BlockWindow("window1x2", BlockWindow.WindowType.WINDOW_1X2),
                new BlockWindow("window2x1", BlockWindow.WindowType.WINDOW_2X1),
                new BlockWindow("window2x2", BlockWindow.WindowType.WINDOW_2X2),
                HorizontalBlockBuilder.create("locker", Material.IRON)
                        .soundType(SoundType.METAL).transparency(false, false).aabb(new AxisAlignedBB(0, 0, 0, 1, 1.5, 1))
                        .build(),
                HorizontalBlockBuilder.create("screen", Material.ROCK).soundType(SoundType.STONE)
                        .aabb(Block.FULL_BLOCK_AABB, Block.NULL_AABB)
                        .transparency(false, false).build(),
                HorizontalBlockBuilder.create("box_single", Material.IRON).aabb(new AxisAlignedBB(0, 0, 0, 1, 0.5, 1))
                        .soundType(SoundType.METAL).transparency(false, false)
                        .build(),
                HorizontalBlockBuilder.create("box_double", Material.IRON)
                        .soundType(SoundType.METAL).transparency(false, false)
                        .build(),
                BlockBuilder.create("sandbag", Material.SAND).soundType(SoundType.SAND).setTransparent().renderType(BlockRenderLayer.CUTOUT).build(),
                BlockBuilder.create("wooden_crate", Material.WOOD).soundType(SoundType.WOOD).setTransparent().build(),
                HorizontalBlockBuilder.create("vent", Material.IRON).soundType(SoundType.METAL).setTransparent()
                        .aabb(new AxisAlignedBB(0, 0, 0.8, 1, 1, 1), new AxisAlignedBB(0, 0, 0, 0.2, 1, 1), new AxisAlignedBB(0, 0, 0, 1, 1, 0.2), new AxisAlignedBB(0.8, 0, 0, 1, 1, 1))
                        .nullAABB().build(),
                new BlockLootCrate("big_crate_empty", BlockLootCrate.EnumCrateType.EMPTY),
                new BlockLootCrate("big_crate_ammo", BlockLootCrate.EnumCrateType.AMMO),
                new BlockLootCrate("big_crate_gun", BlockLootCrate.EnumCrateType.WEAPON),
                new BlockSimpleStairs("wood_stairs", Material.WOOD),
                BlockBuilder.create("clean_metal", Material.IRON).soundType(SoundType.METAL).build(),
                BlockBuilder.create("dark_wood", Material.WOOD).soundType(SoundType.WOOD).build(),
                BlockBuilder.create("wood_tile", Material.WOOD).soundType(SoundType.WOOD).build(),
                BlockBuilder.create("sandbag_sand", Material.SAND).soundType(SoundType.SAND).build(),
                new BlockTire(),
                new BlockTireStack(),
                BlockBuilder.create("metal_table", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                BlockBuilder.create("road_blocker", Material.IRON).soundType(SoundType.METAL).setTransparent().aabb(new AxisAlignedBB(0.4, 0, 0.4, 0.6, 1, 0.6), new AxisAlignedBB(0.4, 0.0, 0.4, 0.6, 1.2, 0.6)).build(),
                BlockBuilder.create("modern_lamp", Material.ROCK).soundType(SoundType.GLASS).light(1.0F).build(),
                HorizontalBlockBuilder.create("radiator", Material.IRON).soundType(SoundType.METAL).setTransparent().nullAABB().build(),
                new BlockSmallChest(),
                BlockBuilder.create("canister", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                BlockBuilder.create("carrier_barrels_blue", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                BlockBuilder.create("carrier_barrels_red", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                BlockBuilder.create("carrier_barrels_white", Material.IRON).soundType(SoundType.METAL).setTransparent().build(),
                BlockBuilder.create("carrier_empty_ground", Material.WOOD).soundType(SoundType.WOOD).setTransparent().aabb(new AxisAlignedBB(0, 0, 0, 1.0, 0.2, 1.0)).build(),
                BlockBuilder.create("flare_stick", Material.CLOTH).soundType(SoundType.CLOTH).setTransparent().light(0.25F).nullAABB(new AxisAlignedBB(0.35, 0.0, 0.35, 0.65, 0.1, 0.65)).build(),
                BlockBuilder.create("metal_light", Material.IRON).soundType(SoundType.METAL).setTransparent().light(1.0F).aabb(new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.9, 0.7)).build(),
                BlockBuilder.create("pole", Material.IRON).soundType(SoundType.METAL).setTransparent().aabb(new AxisAlignedBB(0.35, 0.0, 0.35, 0.65, 1.0, 0.65)).build(),
                BlockBuilder.create("pole_base", Material.IRON).soundType(SoundType.METAL).setTransparent().aabb(new AxisAlignedBB(0.2, 0.0, 0.2, 0.8, 1.0, 0.8)).build(),
                HorizontalBlockBuilder.create("pole_lamp_modern", Material.IRON).soundType(SoundType.METAL).setTransparent().light(1.0F).aabb(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.7, 1.0)).build(),
                HorizontalBlockBuilder.create("roadblocker_light_gray", Material.IRON).soundType(SoundType.METAL).setTransparent().aabb(Block.FULL_BLOCK_AABB, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.25, 1.0)).build(),
                HorizontalBlockBuilder.create("roadblocker_red", Material.IRON).soundType(SoundType.METAL).setTransparent().aabb(Block.FULL_BLOCK_AABB, new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.25, 1.0)).build(),
                BlockBuilder.create("rocks_andesite", Material.ROCK).soundType(SoundType.STONE).setTransparent().nullAABB(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0)).build(),
                BlockBuilder.create("rocks_cobblestone", Material.ROCK).soundType(SoundType.STONE).setTransparent().nullAABB(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0)).build(),
                BlockBuilder.create("rocks_diorite", Material.ROCK).soundType(SoundType.STONE).setTransparent().nullAABB(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0)).build(),
                BlockBuilder.create("rocks_granite", Material.ROCK).soundType(SoundType.STONE).setTransparent().nullAABB(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0)).build(),
                BlockBuilder.create("rocks_gravel", Material.ROCK).soundType(SoundType.STONE).setTransparent().nullAABB(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0)).build()
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        CFGWeapons cfg = ConfigPMC.guns();
        IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
                new ItemBackpack("backpack1").addDescription("Right Click to equip"),
                new ItemBackpack("backpack2").addDescription("Right Click to equip"),
                new ItemBackpack("backpack3").addDescription("Right Click to equip"),
                new ItemBandage("bandage"),
                new ItemFirstAidKit("firstaidkit"),
                new ItemMedkit("medkit"),
                new ItemEnergyDrink("energydrink"),
                new ItemPainkiller("painkillers"),
                new ItemAdrenalineSyringe("adrenalinesyringe"),
                new ItemGhillie("ghillie_suit"),
                new ItemNVGoggles("nv_goggles").addDescription("Right Click to equip"),
                new ItemExplodeable("grenade", 110, ItemExplodeable.Helper::onFragRemoved),
                new ItemExplodeable("smoke", 110, ItemExplodeable.Helper::onSmokeRemoved).addAditionalDescription("Effect duration: 20s", TextFormatting.RED + "Water will cancel the effect!"),
                new ItemExplodeable("molotov", -1, ItemExplodeable.Helper::onMolotovRemoved).addAditionalDescription("Effect duration: 10s", TextFormatting.RED + "Water will cancel the effect!"),
                new ItemExplodeable("flashbang", 60, ItemExplodeable.Helper::onFlashBangRemoved),
                new ItemAmmo("ammo_9mm", AmmoType.AMMO9MM),
                new ItemAmmo("ammo_45acp", AmmoType.AMMO45ACP),
                new ItemAmmo("ammo_shotgun", AmmoType.AMMO12G),
                new ItemAmmo("ammo_556", AmmoType.AMMO556),
                new ItemAmmo("ammo_762", AmmoType.AMMO762),
                new ItemAmmo("ammo_300m", AmmoType.AMMO300M),
                new ItemAmmo("ammo_flare", AmmoType.FLARE),
                MeleeItemBuilder.create("pan").materialName("material_pan").damage(15.0F).build(),
                MeleeItemBuilder.create("machete").materialName("material_machete").damage(11.0F).build(),
                MeleeItemBuilder.create("crowbar").materialName("material_crowbar").damage(9.0F).build(),
                MeleeItemBuilder.create("sickle").materialName("material_sickle").damage(10.0F).build(),
                new ArmorBase("armor1helmet", ToolMaterials.LVL1, 1, EntityEquipmentSlot.HEAD, ArmorBase.ArmorLevel.LEVEL_ONE),
                new ArmorBase("armor1body", ToolMaterials.LVL1, 1, EntityEquipmentSlot.CHEST, ArmorBase.ArmorLevel.LEVEL_ONE),
                new ArmorBase("armor2helmet", ToolMaterials.LVL2, 1, EntityEquipmentSlot.HEAD, ArmorBase.ArmorLevel.LEVEL_TWO),
                new ArmorBase("armor2body", ToolMaterials.LVL2, 1, EntityEquipmentSlot.CHEST, ArmorBase.ArmorLevel.LEVEL_TWO),
                new ArmorBase("armor3helmet", ToolMaterials.LVL3, 1, EntityEquipmentSlot.HEAD, ArmorBase.ArmorLevel.LEVEL_THREE),
                new ArmorBase("armor3body", ToolMaterials.LVL3, 1, EntityEquipmentSlot.CHEST, ArmorBase.ArmorLevel.LEVEL_THREE),
                new ItemMuzzle("silencer_smg", true),
                new ItemMuzzle("silencer_ar", true),
                new ItemMuzzle("silencer_sniper", true),
                new ItemMuzzle("compensator_smg", 0.7F, 0.7F),
                new ItemMuzzle("compensator_ar", 0.7F, 0.7F),
                new ItemMuzzle("compensator_sniper", 0.7F, 0.7F),
                new ItemScope("red_dot", new ScopeData()),
                new ItemScope("holographic", new ScopeData()),
                new ItemScope("scope2x", new ScopeData(Constants.SCOPE_2X_ZOOM, () -> ConfigPMC.client.reticles.scope2xSensitivity.getAsFloat())),
                new ItemScope("scope4x", new ScopeData(Constants.SCOPE_4X_ZOOM, () -> ConfigPMC.client.reticles.scope4xSensitivity.getAsFloat())),
                new ItemScope("scope8x", new ScopeData(Constants.SCOPE_8X_ZOOM, () -> ConfigPMC.client.reticles.scope8xSensitivity.getAsFloat())),
                new ItemScope("scope15x", new ScopeData(Constants.SCOPE_15X_ZOOM, () -> ConfigPMC.client.reticles.scope15xSensitivity.getAsFloat()), true),
                new ItemGrip("grip_vertical", 0.8F, 1.0F),
                new ItemGrip("grip_angled", 1.0F, 0.8F),
                new ItemMagazine("quickdraw_mag_smg", false, true),
                new ItemMagazine("extended_mag_smg", true, false),
                new ItemMagazine("extended_quickdraw_mag_smg", true, true),
                new ItemMagazine("quickdraw_mag_ar", false, true),
                new ItemMagazine("extended_mag_ar", true, false),
                new ItemMagazine("extended_quickdraw_mag_ar", true, true),
                new ItemMagazine("quickdraw_mag_sniper", false, true),
                new ItemMagazine("extended_mag_sniper", true, false),
                new ItemMagazine("extended_quickdraw_mag_sniper", true, true),
                new ItemStock("bullet_loops", true, 1.0F),
                new ItemStock("cheekpad", false, 0.8F),
                new ItemParachute("parachute"),
                new PMCItem("steel_dust"),
                new PMCItem("steel_ingot"),
                new PMCItem("copper_ingot"),
                new ItemFuelCan().addDescription("Hold right click while driving vehicle", "Vehicle must be stationary!"),
                new ItemVehicleSpawner("vehicle_uaz", ItemVehicleSpawner.Vehicles.UAZ),
                new ItemVehicleSpawner("vehicle_dacia", ItemVehicleSpawner.Vehicles.DACIA),
                GunBuilder.create("flare_gun", FlareGun::new)
                        .stats(new CFGWeapon("Flare gun", 0f, 0f, 0f, 0))
                        .firerate(110)
                        .recoil(1.0F, 0.1F)
                        .reload(IReloader.magazine(), 70, PMCSounds.reload_flare)
                        .ammo(AmmoType.FLARE, 1)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_flare, 25.0F)
                        .renderer(() -> RenderFlareGun::new)
                        .build(),
                GunBuilder.create("p92")
                        .stats(cfg.p92)
                        .firerate(2)
                        .recoil(2f, 0.5f)
                        .reload(IReloader.magazine(), 25, PMCSounds.reload_p92)
                        .ammo(AmmoType.AMMO9MM, 15, 20)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_p92, 12f, PMCSounds.gun_p92_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_SMG})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, () -> new ItemScope[]{PMCItems.RED_DOT})
                        .build()
                        .renderer(() -> RenderP92::new)
                        .build(),
                GunBuilder.create("p1911")
                        .stats(cfg.p1911)
                        .firerate(2)
                        .recoil(2f, 0.5f)
                        .reload(IReloader.magazine(), 25, PMCSounds.reload_p1911)
                        .ammo(AmmoType.AMMO45ACP, 7, 12)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_p1911, 12f, PMCSounds.gun_p1911_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_SMG})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, () -> new ItemScope[]{PMCItems.RED_DOT})
                        .build()
                        .renderer(() -> RenderP1911::new)
                        .build(),
                GunBuilder.create("p18c")
                        .stats(cfg.p18c)
                        .firerate(1)
                        .recoil(1.5f, 0.75f)
                        .reload(IReloader.magazine(), 34, PMCSounds.reload_p18c)
                        .ammo(AmmoType.AMMO9MM, 17, 25)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_p18c, 12f, PMCSounds.gun_p18c_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_SMG})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, () -> new ItemScope[]{PMCItems.RED_DOT})
                        .build()
                        .renderer(() -> RenderP18C::new)
                        .build(),
                GunBuilder.create("r1895")
                        .stats(cfg.r1895)
                        .firerate(13)
                        .recoil(2.5f, 1.5f)
                        .reload(IReloader.single(), 18, PMCSounds.reload_r1895)
                        .ammo(AmmoType.AMMO762, 7)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_r1895, 12f, PMCSounds.gun_r1895_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_SMG})
                        .build()
                        .renderer(() -> RenderR1895::new)
                        .build(),
                GunBuilder.create("r45")
                        .stats(cfg.r45)
                        .firerate(12)
                        .recoil(2f, 1.5f)
                        .reload(IReloader.magazine(), 40, PMCSounds.reload_r45)
                        .ammo(AmmoType.AMMO45ACP, 6)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_r45, 12f)
                        .attachments()
                        .addForType(AttachmentType.SCOPE, () -> new ItemScope[]{PMCItems.RED_DOT})
                        .build()
                        .renderer(() -> RenderR45::new)
                        .build(),
                GunBuilder.create("scorpion")
                        .stats(cfg.scorpion)
                        .firerate(1)
                        .recoil(1.3f, 0.3f)
                        .reload(IReloader.magazine(), 57, PMCSounds.reload_scorpion)
                        .ammo(AmmoType.AMMO9MM, 20, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_scorpion, 12f, PMCSounds.gun_scorpion_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_SMG})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.GRIP, () -> new ItemGrip[]{PMCItems.GRIP_VERTICAL})
                        .addForType(AttachmentType.SCOPE, () -> new ItemScope[]{PMCItems.RED_DOT})
                        .build()
                        .renderer(() -> RenderScorpion::new)
                        .build(),
                GunBuilder.create("deagle")
                        .stats(cfg.deagle)
                        .firerate(4)
                        .recoil(4.5f, 3f)
                        .reload(IReloader.magazine(), 50, PMCSounds.reload_deagle)
                        .ammo(AmmoType.AMMO45ACP, 7, 10)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.PISTOL)
                        .sound(PMCSounds.gun_deagle, 14f)
                        .attachments()
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, () -> new ItemScope[]{PMCItems.RED_DOT})
                        .build()
                        .renderer(() -> RenderDeagle::new)
                        .build(),
                GunBuilder.create("sawed_off")
                        .stats(cfg.sawedoff)
                        .firerate(10)
                        .recoil(3.5f, 2f)
                        .reload(IReloader.magazine(), 70, PMCSounds.reload_sawedoff)
                        .ammo(AmmoType.AMMO12G, 2)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SHOTGUN)
                        .sound(PMCSounds.gun_sawed_off, 16f)
                        .renderer(() -> RenderSawedOff::new)
                        .build(),
                GunBuilder.create("s1897")
                        .stats(cfg.s1897)
                        .firerate(15)
                        .recoil(3.5f, 2f)
                        .reload(IReloader.single(), 20, PMCSounds.reload_s1897)
                        .ammo(AmmoType.AMMO12G, 5)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SHOTGUN)
                        .sound(PMCSounds.gun_s1897, 16f)
                        .attachments()
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.BULLET_LOOPS})
                        .build()
                        .renderer(() -> RenderS1897::new)
                        .addBoltAction(() -> PMCSounds.bolt_s1897)
                        .build(),
                GunBuilder.create("s686")
                        .stats(cfg.s686)
                        .firerate(5)
                        .recoil(3.5f, 2f)
                        .reload(IReloader.magazine(), 48, PMCSounds.reload_s686)
                        .ammo(AmmoType.AMMO12G, 2)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SHOTGUN)
                        .sound(PMCSounds.gun_s686, 16f)
                        .attachments()
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.BULLET_LOOPS})
                        .build()
                        .renderer(() -> RenderS686::new)
                        .build(),
                GunBuilder.create("s12k")
                        .stats(cfg.s12k)
                        .firerate(7)
                        .recoil(6f, 2f)
                        .reload(IReloader.magazine(), 65, PMCSounds.reload_s12k)
                        .ammo(AmmoType.AMMO12G, 5, 8)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SHOTGUN)
                        .sound(PMCSounds.gun_s12k, 16f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.COMPENSATOR_AR})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderS12K::new)
                        .build(),
                GunBuilder.create("microuzi")
                        .stats(cfg.microuzi)
                        .firerate(1)
                        .recoil(1.3f, 0.3f)
                        .reload(IReloader.magazine(), 56, PMCSounds.reload_microuzi)
                        .ammo(AmmoType.AMMO9MM, 25, 35)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.SMG)
                        .sound(PMCSounds.gun_micro_uzi, 16f, PMCSounds.gun_micro_uzi_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSmgMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::redDotHoloScope)
                        .build()
                        .renderer(() -> RenderMicroUzi::new)
                        .build(),
                GunBuilder.create("vector")
                        .stats(cfg.vector)
                        .firerate(1)
                        .recoil(1.2f, 0.2f)
                        .reload(IReloader.magazine(), 30, PMCSounds.reload_vector)
                        .ammo(AmmoType.AMMO9MM, 19, 33)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::cycleAll)
                        .weaponType(GunBase.GunType.SMG)
                        .burstAmount(2)
                        .sound(PMCSounds.gun_vector, 16f, PMCSounds.gun_vector_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSmgMuzzle)
                        .addForType(AttachmentType.GRIP, () -> new ItemGrip[]{PMCItems.GRIP_VERTICAL})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderVector::new)
                        .build(),
                GunBuilder.create("bizon")
                        .stats(cfg.bizon)
                        .firerate(2)
                        .recoil(1.3f, 0.4f)
                        .reload(IReloader.magazine(), 62, PMCSounds.reload_bizon)
                        .ammo(AmmoType.AMMO9MM, 53)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.SMG)
                        .sound(PMCSounds.gun_bizon, 16f, PMCSounds.gun_bizon_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSmgMuzzle)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderPPBizon::new)
                        .build(),
                GunBuilder.create("mp5k")
                        .stats(cfg.mp5k)
                        .firerate(2)
                        .recoil(1.4f, 0.35f)
                        .reload(IReloader.magazine(), 64, PMCSounds.reload_mp5k)
                        .ammo(AmmoType.AMMO9MM, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.SMG)
                        .sound(PMCSounds.gun_mp5k, 16f, PMCSounds.gun_mp5k_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSmgMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderMP5K::new)
                        .build(),
                GunBuilder.create("tommy_gun")
                        .stats(cfg.tommygun)
                        .firerate(2)
                        .recoil(1.4f, 0.5f)
                        .reload(IReloader.magazine(), 60, PMCSounds.reload_tommygun)
                        .ammo(AmmoType.AMMO45ACP, 30, 50)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.SMG)
                        .sound(PMCSounds.gun_tommy_gun, 16f, PMCSounds.gun_tommy_gun_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_SMG})
                        .addForType(AttachmentType.GRIP, () -> new ItemGrip[]{PMCItems.GRIP_VERTICAL})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .build()
                        .renderer(() -> RenderTommyGun::new)
                        .build(),
                GunBuilder.create("ump45")
                        .stats(cfg.ump45)
                        .firerate(2)
                        .recoil(1.4f, 0.45f)
                        .reload(IReloader.magazine(), 52, PMCSounds.reload_ump9)
                        .ammo(AmmoType.AMMO45ACP, 25, 35)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::cycleAll)
                        .weaponType(GunBase.GunType.SMG)
                        .burstAmount(2)
                        .sound(PMCSounds.gun_ump9, 16f, PMCSounds.gun_ump9_silenced, 8f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSmgMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getPistolSmgMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderUMP45::new)
                        .build(),
                GunBuilder.create("m16a4")
                        .stats(cfg.m16a4)
                        .firerate(2)
                        .recoil(1.6f, 0.6f)
                        .reload(IReloader.magazine(), 66, PMCSounds.reload_m16a4)
                        .ammo(AmmoType.AMMO556, 30, 40)
                        .firemode(GunBase.Firemode.SINGLE, GunBase.Firemode::ignoreAuto)
                        .weaponType(GunBase.GunType.AR)
                        .burstAmount(3)
                        .sound(PMCSounds.gun_m16a4, 20f, PMCSounds.gun_m16a4_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderM16A4::new)
                        .build(),
                GunBuilder.create("m416")
                        .stats(cfg.m416)
                        .firerate(2)
                        .recoil(1.5f, 0.7f)
                        .reload(IReloader.magazine(), 66, PMCSounds.reload_m416)
                        .ammo(AmmoType.AMMO556, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .sound(PMCSounds.gun_m416, 20f, PMCSounds.gun_m416_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderM416::new)
                        .build(),
                GunBuilder.create("scar_l")
                        .stats(cfg.scarl)
                        .firerate(2)
                        .recoil(1.4f, 0.65f)
                        .reload(IReloader.magazine(), 65, PMCSounds.reload_scarl)
                        .ammo(AmmoType.AMMO556, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .sound(PMCSounds.gun_scarl, 20f, PMCSounds.gun_scarl_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderScarL::new)
                        .build(),
                GunBuilder.create("qbz")
                        .stats(cfg.qbz)
                        .firerate(2)
                        .recoil(1.4f, 0.65f)
                        .reload(IReloader.magazine(), 70, PMCSounds.reload_qbz)
                        .ammo(AmmoType.AMMO556, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .sound(PMCSounds.gun_qbz, 20f, PMCSounds.gun_qbz_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderQBZ::new)
                        .build(),
                GunBuilder.create("g36c")
                        .stats(cfg.g36c)
                        .firerate(2)
                        .recoil(1.5f, 0.7f)
                        .reload(IReloader.magazine(), 82, PMCSounds.reload_g36c)
                        .ammo(AmmoType.AMMO556, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .sound(PMCSounds.gun_g36c, 20f, PMCSounds.gun_g36c_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderG36C::new)
                        .build(),
                GunBuilder.create("aug")
                        .stats(cfg.aug)
                        .firerate(2)
                        .recoil(1.45f, 0.75f)
                        .reload(IReloader.magazine(), 69, PMCSounds.reload_aug)
                        .ammo(AmmoType.AMMO556, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .airdropOnly()
                        .sound(PMCSounds.gun_aug, 20f, PMCSounds.gun_aug_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderAUG::new)
                        .build(),
                GunBuilder.create("akm")
                        .stats(cfg.akm)
                        .firerate(2)
                        .recoil(1.9f, 0.8f)
                        .reload(IReloader.magazine(), 60, PMCSounds.reload_akm)
                        .ammo(AmmoType.AMMO762, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .sound(PMCSounds.gun_akm, 20f, PMCSounds.gun_akm_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderAKM::new)
                        .build(),
                GunBuilder.create("beryl_m762")
                        .stats(cfg.m762)
                        .firerate(2)
                        .recoil(1.7f, 0.7f)
                        .reload(IReloader.magazine(), 50, PMCSounds.reload_m762)
                        .ammo(AmmoType.AMMO762, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::cycleAll)
                        .weaponType(GunBase.GunType.AR)
                        .burstAmount(3)
                        .sound(PMCSounds.gun_m762, 20f, PMCSounds.gun_m762_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderBerylM762::new)
                        .build(),
                GunBuilder.create("mk47_mutant")
                        .stats(cfg.mk47)
                        .firerate(2)
                        .recoil(2.0f, 0.7f)
                        .reload(IReloader.magazine(), 66, PMCSounds.reload_mk47)
                        .ammo(AmmoType.AMMO762, 20, 30)
                        .firemode(GunBase.Firemode.SINGLE, GunBase.Firemode::ignoreAuto)
                        .weaponType(GunBase.GunType.AR)
                        .burstAmount(2)
                        .sound(PMCSounds.gun_mk47, 20f, PMCSounds.gun_mk47_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getARMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderMK47Mutant::new)
                        .build(),
                GunBuilder.create("groza")
                        .stats(cfg.groza)
                        .firerate(2)
                        .recoil(1.95f, 0.75f)
                        .reload(IReloader.magazine(), 50, PMCSounds.reload_groza)
                        .ammo(AmmoType.AMMO762, 30, 40)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.AR)
                        .airdropOnly()
                        .sound(PMCSounds.gun_groza, 20f, PMCSounds.gun_groza_silenced, 14f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, () -> new ItemMuzzle[]{PMCItems.SILENCER_AR})
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getARMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderGroza::new)
                        .build(),
                GunBuilder.create("dp28")
                        .stats(cfg.dp28)
                        .firerate(2)
                        .recoil(2.1f, 0.9f)
                        .reload(IReloader.magazine(), 95, PMCSounds.reload_dp28)
                        .ammo(AmmoType.AMMO762, 47)
                        .firemode(GunBase.Firemode.AUTO)
                        .weaponType(GunBase.GunType.LMG)
                        .sound(PMCSounds.gun_dp28, 20f)
                        .attachments()
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderDP28::new)
                        .build(),
                GunBuilder.create("m249")
                        .stats(cfg.m249)
                        .firerate(2)
                        .recoil(1.7f, 0.65f)
                        .reload(IReloader.magazine(), 148, PMCSounds.reload_m249)
                        .ammo(AmmoType.AMMO556, 100)
                        .firemode(GunBase.Firemode.AUTO)
                        .weaponType(GunBase.GunType.LMG)
                        .airdropOnly()
                        .sound(PMCSounds.gun_m249, 20f)
                        .attachments()
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::closeRangeScopes)
                        .build()
                        .renderer(() -> RenderM249::new)
                        .build(),
                GunBuilder.create("vss")
                        .stats(cfg.vss)
                        .firerate(2)
                        .recoil(1.5f, 0.5f)
                        .reload(IReloader.magazine(), 40, PMCSounds.reload_vss)
                        .ammo(AmmoType.AMMO9MM, 10, 20)
                        .firemode(GunBase.Firemode.AUTO, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.DMR)
                        .sound(PMCSounds.gun_vss, 5f)
                        .builtInScope(Constants.SCOPE_4X_ZOOM, () -> ConfigPMC.client.reticles.scope4xSensitivity.getAsFloat())
                        .attachments()
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getDMRMags)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderVSS::new)
                        .build(),
                GunBuilder.create("mini14")
                        .stats(cfg.mini14)
                        .firerate(1)
                        .recoil(2.3f, 0.9f)
                        .reload(IReloader.magazine(), 62, PMCSounds.reload_mini14)
                        .ammo(AmmoType.AMMO556, 20, 30)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.DMR)
                        .sound(PMCSounds.gun_mini14, 24f, PMCSounds.gun_mini14_silenced, 16f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getDMRMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getDMRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .build()
                        .renderer(() -> RenderMini14::new)
                        .build(),
                GunBuilder.create("qbu")
                        .stats(cfg.qbu)
                        .firerate(1)
                        .recoil(2.3f, 0.9f)
                        .reload(IReloader.magazine(), 44, PMCSounds.reload_qbu)
                        .ammo(AmmoType.AMMO556, 10, 20)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.DMR)
                        .sound(PMCSounds.gun_qbu, 24f, PMCSounds.gun_qbu_silenced, 12f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getDMRMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getDMRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .build()
                        .renderer(() -> RenderQBU::new)
                        .build(),
                GunBuilder.create("sks")
                        .stats(cfg.sks)
                        .firerate(1)
                        .recoil(3.2f, 1.7f)
                        .reload(IReloader.magazine(), 32, PMCSounds.reload_sks)
                        .ammo(AmmoType.AMMO762, 10, 20)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.DMR)
                        .sound(PMCSounds.gun_sks, 24f, PMCSounds.gun_sks_silenced, 16f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getDMRMuzzle)
                        .addForType(AttachmentType.GRIP, AttachmentHelper::allGrips)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getDMRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderSKS::new)
                        .build(),
                GunBuilder.create("slr")
                        .stats(cfg.slr)
                        .firerate(1)
                        .recoil(3.5f, 1.9f)
                        .reload(IReloader.magazine(), 53, PMCSounds.reload_slr)
                        .ammo(AmmoType.AMMO762, 10, 20)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.DMR)
                        .sound(PMCSounds.gun_slr, 24f, PMCSounds.gun_slr_silenced, 16f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getDMRMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getDMRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderSLR::new)
                        .build(),
                GunBuilder.create("mk14")
                        .stats(cfg.mk14)
                        .firerate(2)
                        .recoil(4.2f, 2.2f)
                        .reload(IReloader.magazine(), 39, PMCSounds.reload_mk14)
                        .ammo(AmmoType.AMMO762, 10, 20)
                        .firemode(GunBase.Firemode.SINGLE, GunBase.Firemode::ignoreBurst)
                        .weaponType(GunBase.GunType.DMR)
                        .airdropOnly()
                        .sound(PMCSounds.gun_mk14, 24f, PMCSounds.gun_mk14_silenced, 16f)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getDMRMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getDMRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderMK14Ebr::new)
                        .build(),
                GunBuilder.create("win94")
                        .stats(cfg.win94)
                        .firerate(25)
                        .recoil(5.5f, 3.5f)
                        .reload(IReloader.single(), 20, PMCSounds.reload_win94)
                        .ammo(AmmoType.AMMO45ACP, 8)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SR)
                        .sound(PMCSounds.gun_win94, 20f)
                        .addBoltAction(() -> PMCSounds.bolt_win94)
                        .attachments()
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.BULLET_LOOPS})
                        .build()
                        .renderer(() -> RenderWincherster94::new)
                        .build(),
                GunBuilder.create("kar98k")
                        .stats(cfg.kar98k)
                        .firerate(30)
                        .recoil(2.5f, 1.25f)
                        .reload(IReloader.stripperClip(63), 24, PMCSounds.reload_kar98k_single)
                        .ammo(AmmoType.AMMO762, 5)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SR)
                        .sound(PMCSounds.gun_kar98k, 30f, PMCSounds.gun_kar98k_silenced, 20f)
                        .addBoltAction(() -> PMCSounds.bolt_kar98k)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSRMuzzle)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.BULLET_LOOPS, PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderKar98k::new)
                        .build(),
                GunBuilder.create("m24")
                        .stats(cfg.m24)
                        .firerate(35)
                        .recoil(2.5f, 1.25f)
                        .reload(IReloader.magazine(), 71, PMCSounds.reload_m24)
                        .ammo(AmmoType.AMMO762, 5, 7)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SR)
                        .sound(PMCSounds.gun_m24, 30f, PMCSounds.gun_m24_silenced, 20f)
                        .addBoltAction(() -> PMCSounds.bolt_m24)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSRMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getSRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderM24::new)
                        .build(),
                GunBuilder.create("awm")
                        .stats(cfg.awm)
                        .firerate(35)
                        .recoil(2.5f, 1.25f)
                        .reload(IReloader.magazine(), 78, PMCSounds.reload_awm)
                        .ammo(AmmoType.AMMO300M, 5, 7)
                        .firemode(GunBase.Firemode.SINGLE)
                        .weaponType(GunBase.GunType.SR)
                        .airdropOnly()
                        .sound(PMCSounds.gun_awm, 30f, PMCSounds.gun_awm_silenced, 20f)
                        .addBoltAction(() -> PMCSounds.bolt_awm)
                        .attachments()
                        .addForType(AttachmentType.MUZZLE, AttachmentHelper::getSRMuzzle)
                        .addForType(AttachmentType.MAGAZINE, AttachmentHelper::getSRMags)
                        .addForType(AttachmentType.SCOPE, AttachmentHelper::longRangeScopes)
                        .addForType(AttachmentType.STOCK, () -> new ItemStock[]{PMCItems.CHEEKPAD})
                        .build()
                        .renderer(() -> RenderAWM::new)
                        .build()
        );
        ITEM_BLOCKS.forEach(registry::register);
        ITEM_BLOCKS = null;
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityEntry> e) {
        e.getRegistry().registerAll(
                registerEntity("bullet", EntityBullet.class, 64, 40),
                registerEntity("flare", EntityFlare.class, 64, 20),
                registerEntity("parachute", EntityParachute.class, 256, 1),
                registerEntity("plane", EntityPlane.class, 128, 25),
                registerEntity("dropEntity", EntityAirdrop.class, 256, 4),
                registerVehicle("uaz", EntityVehicleUAZ.class),
                registerVehicle("dacia", EntityVehicleDacia.class),
                registerEntity("enemyai", EntityAIPlayer.class, 256, 3, true, 0x000000, 0xFFFFFF),
                registerEntity("frag_grenade", EntityFragGrenade.class, 64, 1),
                registerEntity("molotov", EntityMolotov.class, 64, 1),
                registerEntity("smoke_grenade", EntitySmokeGrenade.class, 256, 1),
                registerEntity("flashbang", EntityFlashBang.class, 64, 1)
        );
    }

    @SubscribeEvent
    public static void registerGameModes(GameRegistry.GameRegisterEvent e) {
        e.registerAll(
                new GameInactive("inactive"),
                new GameBattleRoyale("battleroyale"),
                new GameBombDefuse("bombDefuse")
        );
    }

    public static void registerItemBlock(Block block) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(block.getRegistryName());
        ITEM_BLOCKS.add(itemBlock);
    }

    private static EntityEntry registerEntity(String name, Class<? extends Entity> cl, int trackRange, int frequency) {
        return createEntityBuilder(name).entity(cl).tracker(trackRange, frequency, true).build();
    }

    private static EntityEntry registerEntity(String name, Class<? extends Entity> entityClass, int trackingRange, int updateFrequency, boolean sendVelocityUpdates, int eggPrimary, int eggSecondary) {
        return createEntityBuilder(name).entity(entityClass).tracker(trackingRange, updateFrequency, sendVelocityUpdates).egg(eggPrimary, eggSecondary).build();
    }

    private static EntityEntry registerVehicle(String name, Class<? extends EntityVehicle> vehicleClass) {
        return registerEntity(name, vehicleClass, 256, 1);
    }

    private static <E extends Entity> EntityEntryBuilder<E> createEntityBuilder(String name) {
        EntityEntryBuilder<E> builder = EntityEntryBuilder.create();
        ResourceLocation regName = new ResourceLocation(Pubgmc.MOD_ID, name);
        return builder.id(regName, entityID++).name(regName.toString());
    }

    public static void initTileEntities() {
        registerTileEntity(TileEntityAirdrop.class, "airdrop");
        registerTileEntity(TileEntityLootGenerator.class, "lootspawner");
        registerTileEntity(TileEntityPlayerCrate.class, "player_crate");
        registerTileEntity(TileEntityGunWorkbench.class, "gun_workbench");
        registerTileEntity(TileEntityLandMine.class, "landmine");
        registerTileEntity(TileEntityWindow.class, "window");
    }

    private static void registerTileEntity(Class<? extends TileEntity> tileEntityClass, String name) {
        net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity(tileEntityClass, new ResourceLocation(Pubgmc.MOD_ID + ":" + name));
    }
}
