package csokicraft.forge19.librarian;

import java.util.*;
import net.minecraft.item.ItemStack;

public class BookRegistry {
	public static BookRegistry inst=new BookRegistry();
	private List<ItemStack> l;
	
	private BookRegistry(){
		l=new ArrayList<ItemStack>();
	}
	
	public void registerAsBook(ItemStack is){
		l.add(is);
	}
	
	public boolean isBook(ItemStack is){
		for(ItemStack book:l){
			if(book.isItemEqual(is)) return true;
			//removing as it does not work as intended, but instead causes crash
			//if(book.getItemDamage()<0&&book.getItem().equals(is.getItem())) return true;
		}
		return false;
	}
}
