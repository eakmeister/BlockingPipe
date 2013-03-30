package com.eakmeister.btp.block;


import com.eakmeister.btp.lib.RenderIds;
import com.eakmeister.btp.tileentity.BlockingPipe;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBlockingTransportPipe extends BlockContainer {

	public BlockBlockingTransportPipe(int id) {
		super(id, Material.rock);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new BlockingPipe();
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.blockingTransportPipe;
	}
	
	public int getBlockTexture(IBlockAccess iblockaccess, int x, int y, int z, int l) {
		TileEntity tile = iblockaccess.getBlockTileEntity(x, y, z);
		
		if (!(tile instanceof BlockingPipe)) {
			return 0;
		}
		
		return ((BlockingPipe)tile).currentTextureIndex;
	}

}
