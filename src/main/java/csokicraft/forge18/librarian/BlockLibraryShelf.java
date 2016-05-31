package csokicraft.forge18.librarian;

import java.util.*;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockLibraryShelf extends BlockContainer {
	private final String name="libraryShelf";

	public BlockLibraryShelf() {
		super(Material.wood);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLibraryShelf();
	}

	@Override
	public boolean onBlockActivated(World w, BlockPos pos,
			IBlockState state, EntityPlayer p, EnumFacing side,
			float hitX, float hitY, float hitZ) {
		FMLNetworkHandler.openGui(p, Librarian.inst, Librarian.GUI_ID, w, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityLibraryShelf te=(TileEntityLibraryShelf) worldIn.getTileEntity(pos);
		for(int i=0;i<te.getSizeInventory();i++){
			ItemStack is=te.getStackInSlot(i);
			if(is==null) continue;
			EntityItem e=new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), is);
			worldIn.spawnEntityInWorld(e);
		}
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	public boolean isToolEffective(String type, IBlockState state) {
		return type.equals("axe");
	}
}
