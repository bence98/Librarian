package csokicraft.forge18.librarian;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World w,
			int x, int y, int z) {
		//if(ID!=Librarian.GUI_ID) return null;
		TileEntityLibraryShelf te=(TileEntityLibraryShelf) w.getTileEntity(new BlockPos(x, y, z));
		return new ContainerChest(player.inventory, te, player);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World w,
			int x, int y, int z) {
		//if(ID!=Librarian.GUI_ID) return null;
		TileEntityLibraryShelf te=(TileEntityLibraryShelf) w.getTileEntity(new BlockPos(x, y, z));
		return new GuiChest(player.inventory, te);
	}

	public void registerItemModels(){}
}
