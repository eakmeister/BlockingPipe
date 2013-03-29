package blocking_pipe;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy implements IGuiHandler {
	public void registerRenderInformation()
	{

	}
	
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(blocking_pipe.BlockingTransportPipe.class, "net.minecraft.src.blocking_pipe.BlockingTransportPipe");
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	public World getClientWorld()
	{
		return null;
	}
}
