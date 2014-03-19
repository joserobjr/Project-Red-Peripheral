package br.com.gamemods.computercraft.integration.projectred.item;

import java.util.Arrays;

import br.com.gamemods.computercraft.integration.projectred.ProjectRedPeripheralMod;
import br.com.gamemods.computercraft.integration.projectred.block.ExtensorPart;
import mrtjp.projectred.api.IBundledEmitter;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.JNormalOcclusion;
import codechicken.multipart.TFacePart;
import codechicken.multipart.TMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemExtensorPart extends JItemMultiPart {
	
	public static Icon icon;
	
	public ItemExtensorPart(int id) {
		super(id);
		setCreativeTab(CreativeTabs.tabRedstone);
		setUnlocalizedName("extensorPart");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("projectredperipheral:extensor");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float f, float f2, float f3)
    {
		if(1==1) return false; // Preventing uses for now...
        if (super.onItemUse(stack, player, w, x, y, z, side, f, f2, f3))
        {
            w.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Block.soundGlassFootstep.getPlaceSound(), Block.soundGlassFootstep.getVolume() * 5.0F, Block.soundGlassFootstep.getPitch() * .9F);
            return true;
        }
        return false;
    }
	
	
	@Override
	public TMultiPart newPart(ItemStack item, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 vhit) {
		ExtensorPart part = (ExtensorPart) ProjectRedPeripheralMod.instance.createPart("prp_extensor", false);
		part.preparePlacement(side, item.getItemDamage());
		return part;
	}
	
}
