package br.com.gamemods.computercraft.integration.projectred;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityProjectRedPeripheral.class, new TileEntityProjectRedPeripheralSpecialRender());
		MinecraftForgeClient.registerItemRenderer(BlockProjectRedPeripheral.blockId, new ItemProjectRedPeripheralRenderer());
	}
}
