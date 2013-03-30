package com.eakmeister.btp.client.render.item;

import org.lwjgl.opengl.GL11;

import com.eakmeister.btp.client.model.ModelBlockingTransportPipe;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RendererItemBlockingTransportPipe implements IItemRenderer {

	private ModelBlockingTransportPipe pipeModel;
	
	public RendererItemBlockingTransportPipe() {
		pipeModel = new ModelBlockingTransportPipe();
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		float scale;
		
		switch (type) {
		case ENTITY:
			scale = 1.0f;
			renderBlockingTransportPipe(-0.5f * scale, 0.0f * scale , 0.5f * scale, scale);
			break;
		case EQUIPPED:
			scale = 1.0f;
			renderBlockingTransportPipe(0.0f * scale, 0.0f * scale , 1.0f * scale, scale);
			break;
		case INVENTORY:
			scale = 1.0f;
			renderBlockingTransportPipe(0.0f * scale, -0.1f * scale , 1.0f * scale, scale);
			break;
		default:
			break;
		}
	}
	
	private void renderBlockingTransportPipe(float x, float y, float z, float scale) {
		//pipeModel.render(Tessellator.instance, scale);

	}

}
