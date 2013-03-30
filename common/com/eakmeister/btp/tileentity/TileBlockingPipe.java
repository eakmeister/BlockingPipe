package com.eakmeister.btp.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

public class TileBlockingPipe extends TileEntity {
	
	public static final int CONNECTION_UP = 1;
	public static final int CONNECTION_DOWN = 2;
	public static final int CONNECTION_LEFT = 4;
	public static final int CONNECTION_RIGHT = 8;
	public static final int CONNECTION_FRONT = 16;
	public static final int CONNECTION_BACK = 32;
	private int connectionMask;
	
	public static final int SLOT_UP = 0;
	public static final int SLOT_DOWN = 1;
	public static final int SLOT_LEFT = 2;
	public static final int SLOT_RIGHT = 3;
	public static final int SLOT_FRONT = 4;
	public static final int SLOT_BACK = 5;
	public ItemStack slots[] = {null, null, null, null, null, null};
	public float progress[] = {0, 0, 0, 0, 0, 0};
	
	public static final int DIR_IN = 0;
	public static final int DIR_OUT = 1;
	public int direction[] = {DIR_IN, DIR_IN, DIR_IN, DIR_IN, DIR_IN, DIR_IN};
	
	public int currentTextureIndex = -1;
	
	public TileBlockingPipe() {
		slots[0] = new ItemStack(Block.grass);
		slots[1] = new ItemStack(Block.dirt);
		slots[2] = new ItemStack(Block.sand);
		slots[3] = new ItemStack(Block.stone);
		slots[4] = new ItemStack(Block.cobblestone);
		slots[5] = new ItemStack(Block.bedrock);
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		for (int i = 0; i < 6; i++) {
			progress[i]++;
			
			if (progress[i] >= 256)
				progress[i] = 0;
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		
		try {
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(connectionMask);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] data = bos.toByteArray();
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "BlockingPipe";
		packet.data = data;
		packet.length = data.length;
		packet.isChunkDataPacket = true;

		return packet;
	}
	
	public void setConnectionMask(int connectionMask) {
		System.out.println("Connection Mask: " + connectionMask);
		this.connectionMask = connectionMask;
	}
	
	public int getConnectionMask() {
		return connectionMask;
	}
}
