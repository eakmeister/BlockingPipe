package com.eakmeister.btp.block;


import java.util.Random;

import com.eakmeister.btp.lib.RenderIds;
import com.eakmeister.btp.tileentity.TileBlockingPipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockBlockingTransportPipe extends BlockContainer {

	private Icon icon_normal;
	private Icon icon_powered;
	
	public BlockBlockingTransportPipe(int id) {
		super(id, Material.rock);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileBlockingPipe();
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
	
	@Override
	public void registerIcons(IconRegister register) {
		icon_normal = register.registerIcon("BlockingPipe:Normal");
		icon_powered = register.registerIcon("BlockingPipe:Powered");
	}
	
	@Override
	public Icon getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return icon_normal;
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int blockSide) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		return icon_normal;
		
		//return ((BlockingPipe)tile).currentTextureIndex;
	}
	
	private void updateConnections(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile == null || !(tile instanceof TileBlockingPipe)) {
			return;
		}
		
		TileBlockingPipe pipe = (TileBlockingPipe)tile;
		int connectionMask = 0;
		
		TileEntity t = world.getBlockTileEntity(x + 1, y, z);
		if (t != null && (t instanceof IInventory || t instanceof TileBlockingPipe))
			connectionMask |= TileBlockingPipe.CONNECTION_RIGHT;
		else
			connectionMask &= ~TileBlockingPipe.CONNECTION_RIGHT;
		
		t = world.getBlockTileEntity(x - 1, y, z);
		if (t != null && (t instanceof IInventory || t instanceof TileBlockingPipe))
			connectionMask |= TileBlockingPipe.CONNECTION_LEFT;
		else
			connectionMask &= ~TileBlockingPipe.CONNECTION_LEFT;
		
		t = world.getBlockTileEntity(x, y + 1, z);
		if (t != null && (t instanceof IInventory || t instanceof TileBlockingPipe))
			connectionMask |= TileBlockingPipe.CONNECTION_UP;
		else
			connectionMask &= ~TileBlockingPipe.CONNECTION_UP;
		
		t = world.getBlockTileEntity(x, y - 1, z);
		if (t != null && (t instanceof IInventory || t instanceof TileBlockingPipe))
			connectionMask |= TileBlockingPipe.CONNECTION_DOWN;
		else
			connectionMask &= ~TileBlockingPipe.CONNECTION_DOWN;
		
		t = world.getBlockTileEntity(x, y, z + 1);
		if (t != null && (t instanceof IInventory || t instanceof TileBlockingPipe))
			connectionMask |= TileBlockingPipe.CONNECTION_FRONT;
		else
			connectionMask &= ~TileBlockingPipe.CONNECTION_FRONT;
		
		t = world.getBlockTileEntity(x, y, z - 1);
		if (t != null && (t instanceof IInventory || t instanceof TileBlockingPipe))
			connectionMask |= TileBlockingPipe.CONNECTION_BACK;
		else
			connectionMask &= ~TileBlockingPipe.CONNECTION_BACK;
		
		pipe.setConnectionMask(connectionMask);
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int l) {
		updateConnections(world, x, y, z);
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		updateConnections(world, x, y, z);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		
		if (tile == null || !(tile instanceof TileBlockingPipe)) {
			return false;
		}
		
		TileBlockingPipe pipe = (TileBlockingPipe)tile;
		pipe.powered = !pipe.powered;
		world.markBlockForUpdate(x, y, z);
		System.out.println("Block " + (pipe.powered ? "powered" : "unpowered"));
		
		return true;
	}
}
