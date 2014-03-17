package br.com.gamemods.computercraft.integration.projectred;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockProjectRedPeripheral extends Block implements ITileEntityProvider {
	public static int blockId = 888;
	
	public BlockProjectRedPeripheral() {
		super(blockId, Material.rock);
		setHardness(2.0f);
		setUnlocalizedName("peripheralProjectRed");
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityProjectRedPeripheral();
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return -2;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z,
			ForgeDirection side) {
		return true;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	private int convertRedstoneToForgeDirection(int side){
		switch(side)
		{
		case 0: return 1; // Up
		case 1: return 0; // Down
		case 2: return 3; // South
		case 3: return 2; // North
		case 4: return 5; // East
		case 5: return 4; // West
		}
		return side;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		/*int l = BlockPistonBase.determineOrientation(world, x, y, z, entity);
		world.setBlockMetadataWithNotify(x, y, z, l, 2);*/
		int rotation = MathHelper.floor_double((double)((entity.rotationYaw * 4F) / 360F) + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}
	
	@Override
	public int isProvidingStrongPower(IBlockAccess w, int x, int y, int z, int side) {
		TileEntityProjectRedPeripheral tile = (TileEntityProjectRedPeripheral) w.getBlockTileEntity(x, y, z);
		return tile.getRedstonePower(convertRedstoneToForgeDirection(side));
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess w, int x, int y, int z, int side) {
		TileEntityProjectRedPeripheral tile = (TileEntityProjectRedPeripheral) w.getBlockTileEntity(x, y, z);
		return tile.getRedstonePower(convertRedstoneToForgeDirection(side));
	}
}
