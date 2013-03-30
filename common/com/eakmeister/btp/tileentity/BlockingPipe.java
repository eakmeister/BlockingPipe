package com.eakmeister.btp.tileentity;

import net.minecraft.tileentity.TileEntity;

public class BlockingPipe extends TileEntity {
	
	public static final int CONNECTION_UP = 1;
	public static final int CONNECTION_DOWN = 2;
	public static final int CONNECTION_LEFT = 4;
	public static final int CONNECTION_RIGHT = 8;
	public static final int CONNECTION_FRONT = 16;
	public static final int CONNECTION_BACK = 32;
	public int connectionMask = 12;
	
	public int currentTextureIndex = -1;
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
	}
}
