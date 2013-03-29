package blocking_pipe.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

@SideOnly(Side.CLIENT)
public class RendererBlockingTransportPipe extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float tick) {
		GL11.glPushMatrix();
		//GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        //GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        
        renderCube(1.0f, 0.0f, 0.0f);
        
		GL11.glPopMatrix();
	}
	
	private void renderCube(float r, float g, float b) {
		GL11.glColor3f(r, g, b);
		
		GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex3f( 100.0f, 100.0f,-100.0f);          // Top Right Of The Quad (Top)
        GL11.glVertex3f(-100.0f, 100.0f,-100.0f);          // Top Left Of The Quad (Top)
        GL11.glVertex3f(-100.0f, 100.0f, 100.0f);          // Bottom Left Of The Quad (Top)
        GL11.glVertex3f( 100.0f, 100.0f, 100.0f);          // Bottom Right Of The Quad (Top)
        GL11.glVertex3f( 100.0f,-100.0f, 100.0f);          // Top Right Of The Quad (Bottom)
        GL11.glVertex3f(-100.0f,-100.0f, 100.0f);          // Top Left Of The Quad (Bottom)
        GL11.glVertex3f(-100.0f,-100.0f,-100.0f);          // Bottom Left Of The Quad (Bottom)
        GL11.glVertex3f( 100.0f,-100.0f,-100.0f);          // Bottom Right Of The Quad (Bottom)
        GL11.glVertex3f( 100.0f, 100.0f, 100.0f);          // Top Right Of The Quad (Front)
        GL11.glVertex3f(-100.0f, 100.0f, 100.0f);          // Top Left Of The Quad (Front)
        GL11.glVertex3f(-100.0f,-100.0f, 100.0f);          // Bottom Left Of The Quad (Front)
        GL11.glVertex3f( 100.0f,-100.0f, 100.0f);          // Bottom Right Of The Quad (Front)
        GL11.glVertex3f( 100.0f,-100.0f,-100.0f);          // Bottom Left Of The Quad (Back)
        GL11.glVertex3f(-100.0f,-100.0f,-100.0f);          // Bottom Right Of The Quad (Back)
        GL11.glVertex3f(-100.0f, 100.0f,-100.0f);          // Top Right Of The Quad (Back)
        GL11.glVertex3f( 100.0f, 100.0f,-100.0f);          // Top Left Of The Quad (Back)
        GL11.glVertex3f(-100.0f, 100.0f, 100.0f);          // Top Right Of The Quad (Left)
        GL11.glVertex3f(-100.0f, 100.0f,-100.0f);          // Top Left Of The Quad (Left)
        GL11.glVertex3f(-100.0f,-100.0f,-100.0f);          // Bottom Left Of The Quad (Left)
        GL11.glVertex3f(-100.0f,-100.0f, 100.0f);          // Bottom Right Of The Quad (Left)
        GL11.glVertex3f( 100.0f, 100.0f,-100.0f);          // Top Right Of The Quad (Right)
        GL11.glVertex3f( 100.0f, 100.0f, 100.0f);          // Top Left Of The Quad (Right)
        GL11.glVertex3f( 100.0f,-100.0f, 100.0f);          // Bottom Left Of The Quad (Right)
        GL11.glVertex3f( 100.0f,-100.0f,-100.0f);          // Bottom Right Of The Quad (Right)
        GL11.glEnd();
	}

}
