package br.com.gamemods.computercraft.integration.projectred;

import br.com.gamemods.computercraft.integration.projectred.block.BlockProjectRedPeripheral;
import br.com.gamemods.computercraft.integration.projectred.block.TileEntityProjectRedPeripheral;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="ProjectRedPeripheral")
public class ProjectRedPeripheralMod {
	
	@SidedProxy(clientSide="br.com.gamemods.computercraft.integration.projectred.ClientProxy",
			serverSide="br.com.gamemods.computercraft.integration.projectred.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		BlockProjectRedPeripheral.blockId = config.getBlock("bundled_io", BlockProjectRedPeripheral.blockId).getInt();
		config.save();
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event){
		GameRegistry.registerTileEntity(TileEntityProjectRedPeripheral.class, "tileEntityProjectRedPeripheral");
		
		BlockProjectRedPeripheral block = new BlockProjectRedPeripheral();
		MinecraftForge.setBlockHarvestLevel(block, "pickaxe", 1);
		GameRegistry.registerBlock(block,"blockProjectRedPeripheral");
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block),
													"SWS",
													"WCW",
													"SWS",
													'W',"projredWire",
													'S',new ItemStack(Block.stone),
													'C',"projredBundledCable"
									)
					);
		
		proxy.registerRenders();
	}
}
