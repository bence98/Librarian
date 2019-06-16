package csokicraft.forge.librarian;

import java.util.Locale;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.text.*;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityLibraryShelf extends TileEntity implements IInventory{
	private ItemStackHandler slots;
	private String customName = null;
	
	public TileEntityLibraryShelf() {
		slots=new ItemStackHandler(27);
	}

	@Override
	public String getName() {
		return hasCustomName()?customName:I18n.translateToLocal("container.libraryShelf");
	}

	@Override
	public boolean hasCustomName() {
		return customName!=null;
	}

	/*@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}*/
	
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	@Override
	public int getSizeInventory() {
		return slots.getSlots();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack slot=getStackInSlot(index),
				is=slot.copy();
		is.setCount(Math.min(slot.getCount(), count));
		slot.setCount(slot.getCount()-count);
		return is;
	}

	//1.8.0 backwards compatibility
	public ItemStack getStackInSlotOnClosing(int index) {
		return getStackInSlot(index);
	}

	//1.8.9 forwards compatibility
	public ItemStack removeStackFromSlot(int index) {
		ItemStack is = getStackInSlot(index);
		setInventorySlotContents(index, ItemStack.EMPTY);
		return is;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		slots.setStackInSlot(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(pos)<64;
	}

	@Override
	public void openInventory(EntityPlayer player) { }

	@Override
	public void closeInventory(EntityPlayer player) { }

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index>=getSizeInventory()||Librarian.isShelfUni) //player inv or universal shelf
			return true;
		return BookRegistry.inst.isBook(stack);
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		for(int i=0;i<getSizeInventory();i++)
			removeStackFromSlot(i);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList l=new NBTTagList();
		for(int i=0;i<slots.getSlots();i++){
			ItemStack is=getStackInSlot(i);
			if(is.isEmpty()) continue;
			NBTTagCompound c=new NBTTagCompound();
			c.setInteger("atSlot", i);
			is.writeToNBT(c);
			l.appendTag(c);
		}
		nbt.setTag("items", l);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList l=nbt.getTagList("items", NBT.TAG_COMPOUND);
		for(int i=0;i<l.tagCount();i++){
			NBTTagCompound c=l.getCompoundTagAt(i);
			int s=c.getInteger("atSlot");
			ItemStack is=new ItemStack(c);
			setInventorySlotContents(s, is);
		}
	}
	
	@Override
	public boolean isEmpty(){
		return false; //TODO
	}
}
