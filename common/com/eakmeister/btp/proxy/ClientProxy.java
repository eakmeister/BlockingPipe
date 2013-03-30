package com.eakmeister.btp.proxy;

import com.eakmeister.btp.client.render.block.PipeBlockRenderer;
import com.eakmeister.btp.client.render.item.RendererItemBlockingTransportPipe;
import com.eakmeister.btp.client.render.tileentity.RendererBlockingTransportPipe;
import com.eakmeister.btp.lib.BlockIds;
import com.eakmeister.btp.lib.RenderIds;
import com.eakmeister.btp.tileentity.BlockingPipe;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	public final static PipeBlockRenderer pipeBlockRenderer = new PipeBlockRenderer();
	
	@Override
	public void registerRenderInformation()
	{
		RenderIds.blockingTransportPipe = RenderingRegistry.getNextAvailableRenderId();
		MinecraftForgeClient.registerItemRenderer(BlockIds.BLOCKING_TRANSPORT_PIPE, new RendererItemBlockingTransportPipe());
		
		CommonProxy.pipeModel = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(pipeBlockRenderer);
	}

	@Override
	public void registerTileEntities()
	{
		super.registerTileEntities();
		
		ClientRegistry.bindTileEntitySpecialRenderer(BlockingPipe.class, new RendererBlockingTransportPipe());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
