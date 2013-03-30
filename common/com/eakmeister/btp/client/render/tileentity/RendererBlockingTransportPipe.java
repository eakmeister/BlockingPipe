package com.eakmeister.btp.client.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.eakmeister.btp.client.model.ModelBlockingTransportPipe;
import com.eakmeister.btp.tileentity.BlockingPipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RendererBlockingTransportPipe extends TileEntitySpecialRenderer {
	
	private ModelBlockingTransportPipe pipeModel = new ModelBlockingTransportPipe();
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick) {
		//pipeModel.render((BlockingPipe)tileentity, x, y, z);
	}
}
