package csokicraft.forge.librarian;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemModels() {
		ItemModelMesher imm=Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		Item shelfItem = ItemBlock.getItemFromBlock(Librarian.shelf);
		ModelResourceLocation res = new ModelResourceLocation(Librarian.MODID+":library_shelf", "inventory");
		imm.register(shelfItem, 0, res);
		ModelBakery.registerItemVariants(shelfItem, res);
	}
}
