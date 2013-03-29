package blocking_pipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockBlockingTransportPipe extends BlockContainer {

	public BlockBlockingTransportPipe(int id) {
		super(id, Material.rock);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new BlockingTransportPipe();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}

}
