package br.com.gamemods.computercraft.integration.projectred.client;

import br.com.gamemods.computercraft.integration.projectred.CommonProxy;
import br.com.gamemods.computercraft.integration.projectred.block.BlockProjectRedPeripheral;
import br.com.gamemods.computercraft.integration.projectred.block.TileEntityProjectRedPeripheral;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityProjectRedPeripheral.class, new TileEntityProjectRedPeripheralSpecialRender());
		MinecraftForgeClient.registerItemRenderer(BlockProjectRedPeripheral.blockId, new ItemProjectRedPeripheralRenderer());
	}
}
