package br.com.gamemods.computercraft.integration.projectred.client;

import java.util.LinkedList;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.IUVTransformation;
import codechicken.lib.render.IconTransformation;
import codechicken.lib.render.RenderUtils;
import codechicken.lib.render.UVScale;
import codechicken.lib.render.Vertex5;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Translation;
import codechicken.lib.vec.Vector3;
import br.com.gamemods.computercraft.integration.projectred.block.ExtensorPart;

public class ExtensorRender {
	public static ModelExtensorPart model = new ModelExtensorPart();
	
	public static void render(ExtensorPart part, Vector3 pos)
	{
		CCModel model = CCModel.quadModel(20);
		
		Vector3 min = new Vector3(0.25, 0, 0.25);
        Vector3 max = new Vector3(0.50,0.1250,0.50);
        Vector3[] corners = new Vector3[8];
        corners[0] = new Vector3(min.x, min.y, min.z);
        corners[1] = new Vector3(max.x, min.y, min.z);
        corners[3] = new Vector3(min.x, max.y, min.z);
        corners[2] = new Vector3(max.x, max.y, min.z);
        corners[4] = new Vector3(min.x, min.y, max.z);
        corners[5] = new Vector3(max.x, min.y, max.z);
        corners[7] = new Vector3(min.x, max.y, max.z);
        corners[6] = new Vector3(max.x, max.y, max.z);
        
        int i = 0;
        Vertex5[] verts = model.verts;
        
        verts[i++] = new Vertex5(corners[7], 0.0938, 0.0625);
        verts[i++] = new Vertex5(corners[6], 0.1562, 0.0625);
        verts[i++] = new Vertex5(corners[2], 0.1562, 0.1875);
        verts[i++] = new Vertex5(corners[3], 0.0938, 0.1875);

        verts[i++] = new Vertex5(corners[4], 0.0938, 0.0313);
        verts[i++] = new Vertex5(corners[5], 0.1562, 0.0624);
        verts[i++] = new Vertex5(corners[6], 0.1562, 0.0624);
        verts[i++] = new Vertex5(corners[7], 0.0938, 0.0313);
        
        verts[i++] = new Vertex5(corners[0], 0.0938, 0.2186);
        verts[i++] = new Vertex5(corners[3], 0.0938, 0.1876);
        verts[i++] = new Vertex5(corners[2], 0.1562, 0.1876);
        verts[i++] = new Vertex5(corners[1], 0.1562, 0.2186);

        verts[i++] = new Vertex5(corners[6], 0.1563, 0.0626);
        verts[i++] = new Vertex5(corners[5], 0.1874, 0.0626);
        verts[i++] = new Vertex5(corners[1], 0.1874, 0.1874);
        verts[i++] = new Vertex5(corners[2], 0.1563, 0.1874);

        verts[i++] = new Vertex5(corners[7], 0.0937, 0.0626);
        verts[i++] = new Vertex5(corners[3], 0.0937, 0.1874);
        verts[i++] = new Vertex5(corners[0], 0.0626, 0.1874);
        verts[i++] = new Vertex5(corners[4], 0.0626, 0.0626);
        
        model.computeNormals();
        model.render(new Translation(pos), new IconTransformation(part.getIcon()));
		
		/*int rotation = 0;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)pos.x + 0.5F, (float)pos.y + 1.5F, (float)pos.z + 0.5F);
		GL11.glScalef(1.0F, -1F, -1F);
		GL11.glRotatef(rotation*90, 0.0F, 1.0F, 0.0F);
		model.render((Entity)null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();*/
	}
}
