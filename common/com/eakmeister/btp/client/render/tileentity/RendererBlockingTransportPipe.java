package com.eakmeister.btp.client.render.tileentity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.eakmeister.btp.client.model.ModelBlockingTransportPipe;
import com.eakmeister.btp.tileentity.TileBlockingPipe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RendererBlockingTransportPipe extends TileEntitySpecialRenderer {
	
	private ModelBlockingTransportPipe pipeModel = new ModelBlockingTransportPipe();
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
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick) {
		
		if (!(tileentity instanceof TileBlockingPipe) || tileentity == null)
			return;
		
		TileBlockingPipe pipe = (TileBlockingPipe)tileentity;
		
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			{
				for (int slot = 0; slot < 6; slot++) {
					//System.out.println(pipe.connectionMask);
					if (pipe.slots[slot] != null && ((1 << slot) & pipe.getConnectionMask()) != 0) {
						GL11.glPushMatrix();
						{
							GL11.glTranslatef((float)x, (float)y, (float)z);
							float progress_adjust = pipe.progress[slot] / 512.0f;
							
							if (slot == TileBlockingPipe.SLOT_UP)
								GL11.glTranslatef(0.5f, 0.5f + progress_adjust, 0.5f);
							else if (slot == TileBlockingPipe.SLOT_DOWN)
								GL11.glTranslatef(0.5f, 0.5f - progress_adjust, 0.5f);
							else if (slot == TileBlockingPipe.SLOT_LEFT)
								GL11.glTranslatef(0.5f - progress_adjust, 0.5f, 0.5f);
							else if (slot == TileBlockingPipe.SLOT_RIGHT)
								GL11.glTranslatef(0.5f + progress_adjust, 0.5f, 0.5f);
							else if (slot == TileBlockingPipe.SLOT_FRONT)
								GL11.glTranslatef(0.5f, 0.5f, 0.5f - progress_adjust);
							else if (slot == TileBlockingPipe.SLOT_BACK)
								GL11.glTranslatef(0.5f, 0.5f, 0.5f + progress_adjust);
							
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
