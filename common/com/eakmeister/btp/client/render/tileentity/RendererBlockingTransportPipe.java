package com.eakmeister.btp.client.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.eakmeister.btp.client.model.ModelBlockingTransportPipe;
import com.eakmeister.btp.client.model.ModelPipeCenter;
import com.eakmeister.btp.client.model.ModelPipeSide;
import com.eakmeister.btp.lib.Reference;
import com.eakmeister.btp.tileentity.TileBlockingPipe;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RendererBlockingTransportPipe extends TileEntitySpecialRenderer {
	
	private static ModelPipeSide pipeSideModel = new ModelPipeSide();
	private static ModelPipeCenter pipeCenterModel = new ModelPipeCenter();
	private EntityItem dummyEntityItem = new EntityItem(null);
	private final RenderItem customRenderItem;
	
	public RendererBlockingTransportPipe() {
		customRenderItem = new RenderItem() {
			@Override
			public boolean shouldBob() {
				return false;
			}
			
			@Override
			public boolean shouldSpreadItems() {
				return false;
			}
		};
		
		customRenderItem.setRenderManager(RenderManager.instance);
	}
	
	private void renderPipeSide(TileEntity tileentity, int slot) {
		GL11.glPushMatrix();
		{
			if (slot == TileBlockingPipe.SLOT_LEFT) {
				GL11.glRotatef(180, 0f, 1f, 0f);
			}
			else if (slot == TileBlockingPipe.SLOT_UP) {
				GL11.glRotatef(90, 0f, 0f, 1f);
			}
			else if (slot == TileBlockingPipe.SLOT_DOWN) {
				GL11.glRotatef(-90, 0f, 0f, 1f);
			}
			else if (slot == TileBlockingPipe.SLOT_FRONT) {
				GL11.glRotatef(-90, 0f, 1f, 0f);
			}
			else if (slot == TileBlockingPipe.SLOT_BACK) {
				GL11.glRotatef(90, 0f, 1f, 0f);
			}
			
			GL11.glTranslatef(0.25f, -0.25f, -0.25f);
			GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
			pipeSideModel.render((Entity)null, 0f, 0f, 0f, 0f, 0f, 1f);
		}
		GL11.glPopMatrix();
	}
	
	private void renderPipeCenter() {
		GL11.glPushMatrix();
		{
			GL11.glTranslatef(-0.25f, -0.25f, -0.25f);
			GL11.glScalef(0.0625f, 0.0625f, 0.0625f);
			pipeCenterModel.render((Entity)null, 0f, 0f, 0f, 0f, 0f, 1f);
		}
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick) {
		
		if (!(tileentity instanceof TileBlockingPipe) || tileentity == null)
			return;
		
		TileBlockingPipe pipe = (TileBlockingPipe)tileentity;
		
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			{
				GL11.glTranslatef((float)x + 0.5f, (float)y + 0.5f, (float)z + 0.5f);
				
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.TEXTURE_PIPE_CENTER);
				renderPipeCenter();
				
				if (pipe.powered)
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.TEXTURE_PIPE_SIDE_POWERED);
				else
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(Reference.TEXTURE_PIPE_SIDE);
				
				for (int slot = 0; slot < 6; slot++) {
					if (((1 << slot) & pipe.getConnectionMask()) != 0) {
						renderPipeSide(tileentity, slot);
					}
					
					if (pipe.slots[slot] != null) {
						GL11.glPushMatrix();
						{
							float progress_adjust = pipe.progress[slot] / (2 * TileBlockingPipe.SPEED);
							
							if (slot == TileBlockingPipe.SLOT_UP)
								GL11.glTranslatef(0.0f, 0.0f + progress_adjust, 0.0f);
							else if (slot == TileBlockingPipe.SLOT_DOWN)
								GL11.glTranslatef(0.0f, 0.0f - progress_adjust, 0.0f);
							else if (slot == TileBlockingPipe.SLOT_LEFT)
								GL11.glTranslatef(0.0f - progress_adjust, 0.0f, 0.0f);
							else if (slot == TileBlockingPipe.SLOT_RIGHT)
								GL11.glTranslatef(0.0f + progress_adjust, 0.0f, 0.0f);
							else if (slot == TileBlockingPipe.SLOT_FRONT)
								GL11.glTranslatef(0.0f, 0.0f, 0.0f + progress_adjust);
							else if (slot == TileBlockingPipe.SLOT_BACK)
								GL11.glTranslatef(0.0f, 0.0f, 0.0f - progress_adjust);
							
							GL11.glScalef(0.7f, 0.7f, 0.7f);
							dummyEntityItem.setEntityItemStack(pipe.slots[slot]);
							customRenderItem.doRenderItem(dummyEntityItem, 0, 0, 0, 0, 0);
						}
						GL11.glPopMatrix();
					}
				}		
			}
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		GL11.glPopMatrix();
		//pipeModel.render((BlockingPipe)tileentity, x, y, z);
	}
}
