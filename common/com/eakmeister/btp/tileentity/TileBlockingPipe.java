package com.eakmeister.btp.tileentity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

public class TileBlockingPipe extends TileEntity {
	
	public static final int SPEED = 255; // lower is faster
	
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
	
	public boolean powered = false;
	private int tick_counter = 0;
	
	public TileBlockingPipe() {
		/*
		slots[0] = new ItemStack(Block.grass);
		slots[1] = new ItemStack(Block.dirt);
		slots[2] = new ItemStack(Block.sand);
		slots[3] = new ItemStack(Block.stone);
		slots[4] = new ItemStack(Block.cobblestone);
		slots[5] = new ItemStack(Block.bedrock);
		*/
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	private void tryPullBlock(int x, int y, int z, int slot) {
		if (slots[slot] != null)
			return;
		
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		
		if (tile == null || !(tile instanceof IInventory)) {
			return;
		}
		
		IInventory inventory = (IInventory)tile;
		
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			slots[slot] = inventory.decrStackSize(i, 1);
			
			if (slots[slot] != null)
				break;
		}
		
		progress[slot] = SPEED;
		direction[slot] = DIR_IN;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		++tick_counter;
		
		if (tick_counter % 20 == 0 && powered) {
			tryPullBlock(xCoord - 1, yCoord, zCoord, SLOT_LEFT);
			tryPullBlock(xCoord + 1, yCoord, zCoord, SLOT_RIGHT);
			tryPullBlock(xCoord, yCoord - 1, zCoord, SLOT_DOWN);
			tryPullBlock(xCoord, yCoord + 1, zCoord, SLOT_UP);
			tryPullBlock(xCoord, yCoord, zCoord - 1, SLOT_BACK);
			tryPullBlock(xCoord, yCoord, zCoord + 1, SLOT_FRONT);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
		
		for (int i = 0; i < 6; i++) {
			if (slots[i] != null) {
				if (direction[i] == DIR_OUT) {
					progress[i]++;
					
					if (progress[i] > SPEED) {
						progress[i] = SPEED;
					}
				}
				else {
					progress[i]--;
					
					if (progress[i] < 0) {
						for (int j = 0; j < 6; j++) {
							if (slots[j] == null && ((1 << j) & connectionMask) != 0) {
								slots[j] = slots[i];
								slots[i] = null;
								direction[j] = DIR_OUT;
								progress[j] = 0;
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								break;
							}
						}
						
						if (slots[i] != null)
							progress[i] = 0;
					}
				}
			}
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
			
			for (int i = 0; i < 6; i++) {
				if (slots[i] == null)
					dos.writeInt(0);
				else {
					dos.writeInt(slots[i].itemID);
					dos.writeInt(slots[i].stackSize);
				}
			}
			System.out.println(Integer.toHexString(System.identityHashCode(this)) + ": " + connectionMask);

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
		this.connectionMask = connectionMask;
	}
	
	public int getConnectionMask() {
		return connectionMask;
	}
}
