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
	
	public static final int SPEED = 50; // lower is faster
	
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
			
			if (slots[slot] != null) {
				progress[slot] = SPEED;
				direction[slot] = DIR_IN;
				System.out.println("Got block from inventory to slot " + slot);
				break;
			}

		}
	}
	
	private boolean tryPushBlock(int x, int y, int z, int slot) {
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		
		if (tile == null) {
			return false;
		}
		
		if (tile instanceof IInventory) {
			IInventory inventory = (IInventory) tile;

			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				if (inventory.isStackValidForSlot(i, slots[slot])) {
					int currentSize = 0;
					
					if (inventory.getStackInSlot(i) != null) {
						if (!inventory.getStackInSlot(i).isItemEqual(slots[slot]))
							continue;
						
						currentSize = inventory.getStackInSlot(i).stackSize;
					}
					
					int itemsToPush = slots[slot].stackSize;
					if (itemsToPush + currentSize > inventory.getInventoryStackLimit())
						itemsToPush = inventory.getInventoryStackLimit() - currentSize;
					
					ItemStack newStack = slots[slot].copy();
					newStack.stackSize = itemsToPush + currentSize;
					inventory.setInventorySlotContents(i, newStack);
					
					slots[slot].stackSize -= itemsToPush;
					System.out.println("Pushed block to inventory");
					
					if (slots[slot].stackSize <= 0) {
						slots[slot] = null;
						return true;
					}
				}
			}
		}
		else if (tile instanceof TileBlockingPipe) {
			TileBlockingPipe pipe = (TileBlockingPipe)tile;
			int otherslot = (slot % 2 == 0 ? slot + 1 : slot - 1);
			
			if (pipe.slots[otherslot] == null) {
				pipe.slots[otherslot] = slots[slot];
				pipe.direction[otherslot] = DIR_IN;
				pipe.progress[otherslot] = SPEED;
				slots[slot] = null;
				
				System.out.println("Pushed block from slot " + slot + " to slot " + otherslot);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		++tick_counter;
		
		if (tick_counter % 20 == 0) {
			if (powered) {
				tryPullBlock(xCoord - 1, yCoord, zCoord, SLOT_LEFT);
				tryPullBlock(xCoord + 1, yCoord, zCoord, SLOT_RIGHT);
				tryPullBlock(xCoord, yCoord - 1, zCoord, SLOT_DOWN);
				tryPullBlock(xCoord, yCoord + 1, zCoord, SLOT_UP);
				tryPullBlock(xCoord, yCoord, zCoord - 1, SLOT_BACK);
				tryPullBlock(xCoord, yCoord, zCoord + 1, SLOT_FRONT);
			}
			
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			tick_counter = 0;
		}
		
		for (int i = 0; i < 6; i++) {
			if (slots[i] != null) {
				if (direction[i] == DIR_OUT) {
					progress[i]++;
					
					if (progress[i] > SPEED) {
						boolean success = false;
						
						if (i == SLOT_LEFT)
							success = tryPushBlock(xCoord - 1, yCoord, zCoord, i);
						else if (i == SLOT_RIGHT)
							success = tryPushBlock(xCoord + 1, yCoord, zCoord, i);
						else if (i == SLOT_UP)
							success = tryPushBlock(xCoord, yCoord + 1, zCoord, i);
						else if (i == SLOT_DOWN)
							success = tryPushBlock(xCoord, yCoord - 1, zCoord, i);
						else if (i == SLOT_FRONT)
							success = tryPushBlock(xCoord, yCoord, zCoord + 1, i);
						else if (i == SLOT_BACK)
							success = tryPushBlock(xCoord, yCoord, zCoord - 1, i);
						
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						
						if (!success)
							progress[i] = SPEED;
					}
				}
				else {
					System.out.println("Progress for slot " + i + ": " + progress[i]);
					progress[i]--;
					
					if (progress[i] <= 0) {
						for (int j = 0; j < 6; j++) {
							if (slots[j] == null && ((1 << j) & connectionMask) != 0) {
								slots[j] = slots[i];
								slots[i] = null;
								direction[j] = DIR_OUT;
								progress[j] = 0;
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
								System.out.println("Transfered block from slot " + i + " to slot " + j);
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
			
			for (int i = 0; i < 6; i++)
				dos.writeFloat(progress[i]);
			
			for (int i = 0; i < 6; i++)
				dos.writeInt(direction[i]);

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
