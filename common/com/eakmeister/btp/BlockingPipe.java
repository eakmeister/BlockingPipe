package com.eakmeister.btp;

import com.eakmeister.btp.block.BlockBlockingTransportPipe;
import com.eakmeister.btp.lib.BlockIds;
import com.eakmeister.btp.network.PacketHandler;
import com.eakmeister.btp.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.java.games.util.Version;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;

@Mod(version = "0.1", modid = "BlockingPipe", name = "Blocking Pipe")
@NetworkMod(channels = { "BlockingPipe" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class BlockingPipe {
	
	static BlockBlockingTransportPipe blockBlockingTransportPipe;
	
	@Instance
	public static BlockingPipe instance;
	
	@SidedProxy(clientSide = "com.eakmeister.btp.proxy.ClientProxy", serverSide = "com.eakmeister.btp.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		blockBlockingTransportPipe = new BlockBlockingTransportPipe(BlockIds.BLOCKING_TRANSPORT_PIPE);
		GameRegistry.registerBlock(blockBlockingTransportPipe, "Blocking Pipe");
		GameRegistry.addRecipe(new ItemStack(blockBlockingTransportPipe), new Object [] {"   ", " i ", "   ", Character.valueOf('i'), Item.stick});
		proxy.registerTileEntities();
	}
	
	@Init
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderInformation();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
