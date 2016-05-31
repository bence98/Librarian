package csokicraft.forge18.librarian;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerItemModels() {
		ItemModelMesher imm=Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		imm.register(ItemBlock.getItemFromBlock(Librarian.shelf), 0, new ModelResourceLocation(Librarian.MODID+":libraryShelf", "inventory"));
		ModelBakery.addVariantName(ItemBlock.getItemFromBlock(Librarian.shelf), Librarian.MODID+":libraryShelf");
	}
}
