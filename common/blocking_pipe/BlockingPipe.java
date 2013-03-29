package blocking_pipe;

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
import net.java.games.util.Version;

@Mod(version = "0.1", modid = "BlockingPipe", name = "Blocking Pipe")
@NetworkMod(channels = { "BlockingPipe" }, clientSideRequired = true, serverSideRequired = false, packetHandler = PacketHandler.class)
public class BlockingPipe {
	
	@Instance
	public static BlockingPipe instance;
	
	@SidedProxy(clientSide = "blocking_pipe.ClientProxy", serverSide = "blocking_pipe.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	@Init
	public void init(FMLInitializationEvent event) {
		proxy.registerTileEntities();
		proxy.registerRenderInformation();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
