package br.com.gamemods.computercraft.integration.projectred.block;

import java.util.Arrays;

import br.com.gamemods.computercraft.integration.projectred.ProjectRedPeripheralMod;
import br.com.gamemods.computercraft.integration.projectred.client.ExtensorRender;
import br.com.gamemods.computercraft.integration.projectred.item.ItemExtensorPart;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;

import mrtjp.projectred.api.IBundledEmitter;
import mrtjp.projectred.api.IConnectable;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.lighting.LazyLightMatrix;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.IFaceRedstonePart;
import codechicken.multipart.IRedstonePart;
import codechicken.multipart.JNormalOcclusion;
import codechicken.multipart.NormalOcclusionTest;
import codechicken.multipart.TFacePart;
import codechicken.multipart.TMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtensorPart extends TMultiPart implements TFacePart, IBundledEmitter, IConnectable, IFaceRedstonePart{

	public byte side;
	public byte[] bundledSignal = new byte[16];
	
	public static Cuboid6[] selectionBounds = new Cuboid6[6];
    public static Cuboid6[] occlusionBounds = new Cuboid6[6];
    
    static
    {
        for (int t = 2; t < 3; t++)
        {
            Cuboid6 selection = new Cuboid6(0, 0, 0, 1, (t+2)/16D, 1).expand(-0.005); // subtract the box a little because we'd like things like posts to get first hit
            Cuboid6 occlusion = new Cuboid6(2/8D, 0, 2/8D, 6/8D, (t+2)/16D, 6/8D);
            for (int s = 0; s < 6; s++)
            {
                selectionBounds[s] = selection.copy().apply(Rotation.sideRotations[s].at(Vector3.center));
                occlusionBounds[s] = occlusion.copy().apply(Rotation.sideRotations[s].at(Vector3.center));
            }
        }
    }
	
    public void preparePlacement(int side, int meta)
    {
        this.side = (byte)(side^1);
    }
    
    @Override
    public void save(NBTTagCompound tag)
    {
        super.save(tag);
        tag.setByte("side", side);
    }
    
    @Override
    public void load(NBTTagCompound tag) {
    	super.load(tag);
    	side = tag.getByte("side");
    }
    
    @Override
    public void writeDesc(MCDataOutput packet) {
    	packet.writeByte(side);
    }
    
    @Override
    public void readDesc(MCDataInput packet) {
    	side = packet.readByte();
    }
    
	@Override
	public String getType() {
		return "prp_extensor";
	}

	@Override
	public int getSlotMask() {
		ProjectRedPeripheralMod.log.info("getSlotMask");
		return 1<<side;
	}

	@Override
	public int redstoneConductionMap() {
		ProjectRedPeripheralMod.log.info("redstoneConductionMap");
		return 0xF;
	}

	@Override
	public boolean solid(int arg0) {
		return true;
	}

	@Override
	public byte[] getBundledSignal(int side) {
		ProjectRedPeripheralMod.log.info("getBundledSignal "+side);
		bundledSignal[1] = (byte)(255 & 0xFF);
		return bundledSignal;
	}

	@Override
	public boolean connectStraight(IConnectable part, int r, int edgeRot) {
		ProjectRedPeripheralMod.log.info("connectStraight "+r+" "+part);
		return true;
	}

	@Override
	public boolean connectInternal(IConnectable part, int r) {
		ProjectRedPeripheralMod.log.info("connectInternal "+r+" "+part);
		return false;
	}

	@Override
	public boolean connectCorner(IConnectable part, int r, int edgeRot) {
		ProjectRedPeripheralMod.log.info("connectCorner "+r+" "+edgeRot+" "+part);
		return true;
	}
	
	@Override
	public Iterable<IndexedCuboid6> getSubParts()
    {
        return Arrays.asList(new IndexedCuboid6(0, selectionBounds[side]));
    }

	@Override
	public boolean canConnectCorner(int r) {
		ProjectRedPeripheralMod.log.info("canConnectCorner "+r);
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderStatic(Vector3 pos, LazyLightMatrix olm, int pass) {
		if (pass == 0)
		{
			CCRenderState.setBrightness(world(), x(), y(), z());
			ExtensorRender.render(this, pos);
			CCRenderState.setColour(-1);
		}
	}
	
	/*@Override
    @SideOnly(Side.CLIENT)
    public void drawBreaking(RenderBlocks renderBlocks)
    {
        CCRenderState.reset();
        RenderWire.renderBreakingOverlay(renderBlocks.overrideBlockTexture, this);
    }*/
	
	/*@Override
	public Iterable<Cuboid6> getCollisionBoxes() {
		return Arrays.asList(selectionBounds[side]);
	}*/
	
	/*@Override
	public Iterable<Cuboid6> getOcclusionBoxes() {
		return Arrays.asList(occlusionBounds[side]);
	}*/
	
	@Override
	public boolean doesTick() {
		return false;
	}

	public Icon getIcon() {
		return ItemExtensorPart.icon;
	}

	@Override
	public boolean canConnectRedstone(int arg0) {
		ProjectRedPeripheralMod.log.info("canConnectRedstone "+arg0);
		return true;
	}

	@Override
	public int strongPowerLevel(int arg0) {
		ProjectRedPeripheralMod.log.info("strongPowerLevel "+arg0);
		return 0;
	}

	@Override
	public int weakPowerLevel(int arg0) {
		ProjectRedPeripheralMod.log.info("weakPowerLevel "+arg0);
		return 15;
	}

	@Override
	public int getFace()
	{
		int i = side;
		ProjectRedPeripheralMod.log.info("GetFace: "+i);
		return i;
	}
}
