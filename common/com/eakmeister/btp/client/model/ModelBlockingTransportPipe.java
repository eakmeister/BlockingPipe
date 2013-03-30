package com.eakmeister.btp.client.model;

import org.lwjgl.opengl.GL11;

import com.eakmeister.btp.tileentity.BlockingPipe;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;

public class ModelBlockingTransportPipe extends ModelBase {
	private float scale;

	public ModelBlockingTransportPipe() {
		scale = 1.0f;
	}

	public ModelBlockingTransportPipe(float scale) {
		this.scale = scale;
	}

	public void render(Tessellator tessellator, float scale) {
		renderCube(1.0f, 0.0f, 0.0f);
	}

	public void render(BlockingPipe blockingPipe, double x, double y, double z) {
		GL11.glPushMatrix();
		{
			GL11.glDisable(GL11.GL_LIGHTING);
			{
				GL11.glTranslatef((float) x, (float) y, (float) z);
				//GL11.glRotatef(45F, 0F, 1F, 0F);
				//GL11.glRotatef(-90F, 1F, 0F, 0F);
				this.render(Tessellator.instance, scale);
			}
			GL11.glEnable(GL11.GL_LIGHTING);
		}
		GL11.glPopMatrix();
	}

	private void renderCube(float r, float g, float b) {
		System.out.println("Render called!");
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glColor3f(r, g, b);

		GL11.glBegin(GL11.GL_QUADS);
		{
			// front
			//GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(1.0f, 0.0f, 1.0f);
			GL11.glVertex3f(1.0f, 1.0f, 1.0f);
			GL11.glVertex3f(0.0f, 1.0f, 1.0f);
			
			// back
			//GL11.glNormal3f(0.0f, 0.0f, -1.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
			GL11.glVertex3f(0.0f, 1.0f, 0.0f);
			GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			GL11.glVertex3f(1.0f, 0.0f, 0.0f);
			
			// right
			//GL11.glNormal3f(1.0f, 0.0f, 0.0f);
			GL11.glVertex3f(1.0f, 0.0f, 1.0f);
			GL11.glVertex3f(1.0f, 0.0f, 0.0f);
			GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			GL11.glVertex3f(1.0f, 1.0f, 1.0f);

			// left
			//GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
			GL11.glVertex3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 1.0f, 1.0f);
			GL11.glVertex3f(0.0f, 1.0f, 0.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
			
			// top
			//GL11.glNormal3f(0.0f, 1.0f, 0.0f);
			GL11.glVertex3f(0.0f, 1.0f, 1.0f);
			GL11.glVertex3f(1.0f, 1.0f, 1.0f);
			GL11.glVertex3f(1.0f, 1.0f, 0.0f);
			GL11.glVertex3f(0.0f, 1.0f, 0.0f);
			
			// bottom
			//GL11.glNormal3f(0.0f, -1.0f, 0.0f);
			GL11.glVertex3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
			GL11.glVertex3f(1.0f, 0.0f, 0.0f);
			GL11.glVertex3f(1.0f, 0.0f, 1.0f);
		}
		GL11.glEnd();
	}
}
