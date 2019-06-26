package com.toma.pubgmc.util.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.toma.pubgmc.Pubgmc;
import com.toma.pubgmc.common.entity.EntityFlashbang;
import com.toma.pubgmc.common.network.PacketHandler;
import com.toma.pubgmc.common.network.sp.PacketUpdateFlashStatus;
import com.toma.pubgmc.util.ImageUtil;
import com.toma.pubgmc.util.PUBGMCUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;

public final class FlashHandler {

	public static final Map<UUID, Integer> FLASHED_PLAYERS = new HashMap<>();
	public static final int MAX_FLASH_RANGE = 14;
	
	public static void flashPlayer(EntityPlayer player, EntityFlashbang from) {
		UUID uuid = player.getUniqueID();
		if(isInRangeToFlash(player, from)) {
			int amount = getFlashAmountFor(player, from);
			if(amount > 0) {
				FLASHED_PLAYERS.put(uuid, amount);
				if(player instanceof EntityPlayerMP) {
					PacketHandler.sendToClient(new PacketUpdateFlashStatus(true), (EntityPlayerMP)player);
				}
			}
		}
	}
	
	public static int getFlashAmountFor(EntityPlayer player, EntityFlashbang flash) {
		float f0 = (float)PUBGMCUtil.getDistanceToBlockPos3D(player.getPosition(), flash.getPosition());
		return f0 > MAX_FLASH_RANGE ? 0 : (int)(100*(1-(f0/100)) + 100);
	}
	
	public static boolean isInRangeToFlash(EntityPlayer player, EntityFlashbang flash) {
		Vec3d playerPos = PUBGMCUtil.getPositionVec(player);
		Vec3d playerLook = player.getLookVec();
		Vec3d playerLookNegativeYaw = playerPos.add(playerLook.rotateYaw(-40f));
		Vec3d playerLookPositiveYaw = playerPos.add(playerLook.rotateYaw( 40f));
		Vec3d flashPos = PUBGMCUtil.getPositionVec(flash);
		double d0 = area(playerPos, playerLookNegativeYaw, playerLookPositiveYaw)*10000;
		double d1 = area(flashPos, playerLookNegativeYaw, playerLookPositiveYaw)*10000;
		double d2 = area(playerPos, flashPos, playerLookPositiveYaw)*10000;
		double d3 = area(playerPos, playerLookNegativeYaw, flashPos)*10000;
		return ((int)d1 == (int)(d0 + d2 + d3));
	}
	
	private static double area(Vec3d yawNeg, Vec3d yawPos, Vec3d flash) {
		return Math.abs((yawNeg.x*(yawPos.z-flash.z) + yawPos.x*(flash.z-yawNeg.z) + flash.x*(yawNeg.z-yawPos.z))/2.0D);
	}
	
	@EventBusSubscriber
	public static class ServerHandler {
		
		@SubscribeEvent
		public static void worldTick(TickEvent.WorldTickEvent e) {
			if(!FLASHED_PLAYERS.isEmpty()) {
				List<UUID> list = FLASHED_PLAYERS.keySet().stream().filter(uuid -> e.world.getPlayerEntityByUUID(uuid) != null).collect(Collectors.toList());
				for(int i = 0; i < list.size(); i++) {
					UUID player = list.get(i);
					int amount = FLASHED_PLAYERS.get(player);
					amount--;
					if(amount > 0) {
						FLASHED_PLAYERS.put(player, amount);
					} else {
						FLASHED_PLAYERS.remove(player);
						EntityPlayer p = e.world.getPlayerEntityByUUID(player);
						if(p instanceof EntityPlayerMP) {
							PacketHandler.sendToClient(new PacketUpdateFlashStatus(false), (EntityPlayerMP)p);
						}
					}
				}
			}
		}
	}
	
	@EventBusSubscriber(Side.CLIENT)
	public static class ClientHandler {
		
		static final ResourceLocation FLASH = new ResourceLocation(Pubgmc.MOD_ID + ":textures/overlay/flash.png");
		static boolean blind = false;
		static int progress;
		
		@SubscribeEvent(priority = EventPriority.LOW)
		public static void renderOverlay(RenderGameOverlayEvent.Post e) {
			if(blind || progress > 0) {
				if(e.getType() == ElementType.ALL) {
					Minecraft mc = Minecraft.getMinecraft();
					ImageUtil.drawFullScreenImage(mc, e.getResolution(), FLASH, blind ? 1f : progress / 100F);
				} else if(e.isCancelable()){
					e.setCanceled(true);
				}
			}
		}
		
		@SubscribeEvent
		public static void onTick(TickEvent.ClientTickEvent e) {
			if(e.phase == Phase.START && Minecraft.getMinecraft().player != null) {
				if(progress > 0) {
					--progress;
				}
			}
		}
		
		public static void update(boolean b) {
			blind = b;
			if(!b) {
				progress = 100;
			}
		}
	}
}
