package blocking_pipe;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import blocking_pipe.CommonProxy;


public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderInformation()
	{
		MinecraftForgeClient.preloadTexture("/lightningcraft.png");
	}

	@Override
	public void registerTileEntities()
	{
		
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
