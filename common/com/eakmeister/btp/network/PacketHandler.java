package com.eakmeister.btp.network;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.eakmeister.btp.BlockingPipe;
import com.eakmeister.btp.tileentity.TileBlockingPipe;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel == "BlockingPipe") {
			ByteArrayInputStream bis = new ByteArrayInputStream(packet.data);
			DataInputStream dis = new DataInputStream(bis);
			
			try {
				int x = dis.readInt();
				int y = dis.readInt();
				int z = dis.readInt();
				int connectionMask = dis.readInt();
				ItemStack[] slots = new ItemStack[6];
				
				for (int i = 0; i < 6; i++) {
					int item_id = dis.readInt();
					
					if (item_id != 0) {
						slots[i] = new ItemStack(item_id, dis.readInt(), 0);
					}
				}
				
				float[] progress = new float[6];
				for (int i = 0; i < 6; i++)
					progress[i] = dis.readFloat();
				
				int[] directions = new int[6];
				for (int i = 0; i < 6; i++)
					directions[i] = dis.readInt();
				
				BlockingPipe.proxy.handleTileEntityPacket(x, y, z, connectionMask, slots, progress, directions);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
