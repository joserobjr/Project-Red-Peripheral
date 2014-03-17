package br.com.gamemods.computercraft.integration.projectred;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class TileEntityProjectRedPeripheralSpecialRender extends TileEntitySpecialRenderer {

	private static final ResourceLocation texture = new ResourceLocation("projectredperipheral:textures/blocks/bundled_io.png");
	private ModelProjectRedPeripheral model;
	
	public TileEntityProjectRedPeripheralSpecialRender() {
		model = new ModelProjectRedPeripheral();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
		int rotation = 0;
		if(tile.worldObj != null)
		{
			rotation = tile.getBlockMetadata();
		}
		
		bindTexture(texture);
		GL11.glPushMatrix();
		if(f == 3)
		{
			GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z - 0.5F);
			GL11.glScalef(1.0F, -1F, -1F);
			GL11.glRotatef(90, -1.0F, 0.0F, 0.0F);
		}
		else
		{
			GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GL11.glScalef(1.0F, -1F, -1F);
			GL11.glRotatef(rotation*90, 0.0F, 1.0F, 0.0F);
		}
		/*if(rotation > 1 && f != 3)
		{
			GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
			GL11.glScalef(1.0F, -1F, -1F);
			GL11.glRotatef(Direction.facingToDirection[rotation]*90, 0.0F, 1.0F, 0.0F);
		}
		else
		{
			if(rotation == 0 && f!=3)
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 1.5F);
				GL11.glScalef(1.0F, -1F, -1F);
				GL11.glRotatef(-90, -1.0F, 0.0F, 0.0F);
			}
			else
			{
				GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z - 0.5F);
				GL11.glScalef(1.0F, -1F, -1F);
				GL11.glRotatef(90, -1.0F, 0.0F, 0.0F);
			}
		}*/
		this.model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix(); //end
	}

}
