package blocking_pipe;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import blocking_pipe.CommonProxy;
import blocking_pipe.renderer.RendererBlockingTransportPipe;


public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderInformation()
	{
		RenderIds.blockingTransportPipe = RenderingRegistry.getNextAvailableRenderId();
		
		//MinecraftForgeClient.registerItemRenderer(BlockIds.BLOCKING_TRANSPORT_PIPE, new RendererBlockingTransportPipe());
	}

	@Override
	public void registerTileEntities()
	{
		super.registerTileEntities();
		
		ClientRegistry.bindTileEntitySpecialRenderer(BlockingTransportPipe.class, new RendererBlockingTransportPipe());
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
