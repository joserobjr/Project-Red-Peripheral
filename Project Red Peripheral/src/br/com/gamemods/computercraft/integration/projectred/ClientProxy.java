package br.com.gamemods.computercraft.integration.projectred;

import br.com.gamemods.computercraft.integration.projectred.block.BlockProjectRedPeripheral;
import br.com.gamemods.computercraft.integration.projectred.block.TileEntityProjectRedPeripheral;
import br.com.gamemods.computercraft.integration.projectred.client.ItemProjectRedPeripheralRenderer;
import br.com.gamemods.computercraft.integration.projectred.client.TileEntityProjectRedPeripheralSpecialRender;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityProjectRedPeripheral.class, new TileEntityProjectRedPeripheralSpecialRender());
		MinecraftForgeClient.registerItemRenderer(BlockProjectRedPeripheral.blockId, new ItemProjectRedPeripheralRenderer());
	}
}
