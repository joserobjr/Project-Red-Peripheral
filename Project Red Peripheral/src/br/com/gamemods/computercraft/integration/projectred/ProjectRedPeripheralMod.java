package br.com.gamemods.computercraft.integration.projectred;

import java.lang.reflect.Field;

import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import br.com.gamemods.computercraft.integration.projectred.block.BlockProjectRedPeripheral;
import br.com.gamemods.computercraft.integration.projectred.block.ExtensorPart;
import br.com.gamemods.computercraft.integration.projectred.block.TileEntityProjectRedPeripheral;
import br.com.gamemods.computercraft.integration.projectred.item.ItemExtensorPart;
import mrtjp.projectred.api.ProjectRedAPI;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="ProjectRedPeripheral", name="Project: Red - Peripheral", dependencies="after:*;required-after:ComputerCraft",version="@clean-version@")
public class ProjectRedPeripheralMod implements IPartFactory {
	
	@SidedProxy(clientSide="br.com.gamemods.computercraft.integration.projectred.ClientProxy",
			serverSide="br.com.gamemods.computercraft.integration.projectred.CommonProxy")
	public static CommonProxy proxy;

	@Instance("ProjectRedPeripheral")
	public static ProjectRedPeripheralMod instance;
	
	@Instance("ProjRed|Transmission")
	public static Object prTransmission;
	
	@Override
	public TMultiPart createPart(String name, boolean client) {
		if(name.equals("prp_extensor")) return new ExtensorPart();
		
		return null;
	}
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		BlockProjectRedPeripheral.blockId = config.getBlock("bundled_io", BlockProjectRedPeripheral.blockId).getInt();
		Item itemExtensor = new ItemExtensorPart(config.getItem("extensor", 889).getInt());
		GameRegistry.registerItem(itemExtensor, "extensor");
		config.save();
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event){
		GameRegistry.registerTileEntity(TileEntityProjectRedPeripheral.class, "tileEntityProjectRedPeripheral");
		
		BlockProjectRedPeripheral block = new BlockProjectRedPeripheral();
		MinecraftForge.setBlockHarvestLevel(block, "pickaxe", 1);
		GameRegistry.registerBlock(block,"blockProjectRedPeripheral");
		
		Object o = this.prTransmission;
		JItemMultiPart item = null;
		try
		{
			Field fd = prTransmission.getClass().getDeclaredField("itemPartWire");
			fd.setAccessible(true);
			item = (JItemMultiPart) (prTransmission != null? fd.get(o) : null); 
		}
		catch(Exception e){e.printStackTrace();}
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block),
													"SWS",
													"WCW",
													"SWS",
													'W', new ItemStack(item == null? Item.redstone : item),
													'S',new ItemStack(Block.stone),
													'C', item == null? new ItemStack(Item.comparator) : "projredBundledCable"
									)
					);
		
		proxy.registerRenders();
		
		MultiPartRegistry.registerParts(this, new String[]{
				"prp_extensor"
		});
	}
}
