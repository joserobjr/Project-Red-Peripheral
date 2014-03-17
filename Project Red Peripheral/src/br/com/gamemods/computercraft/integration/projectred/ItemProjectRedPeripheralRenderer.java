package br.com.gamemods.computercraft.integration.projectred;

import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemProjectRedPeripheralRenderer implements IItemRenderer {
	private static final TileEntityProjectRedPeripheral entity = new TileEntityProjectRedPeripheral();
	private ModelProjectRedPeripheral model = new ModelProjectRedPeripheral();
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		TileEntityRenderer.instance.renderTileEntityAt(entity, 0.0D, 0.0D, 0.0D, 3.0F);
	}

}
