package csokicraft.forge18.librarian;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Librarian.MODID, version = Librarian.VERSION)
public class Librarian {
    public static final String MODID = "librarian";
    public static final String VERSION = "1.0.2";
    @Instance
	public static Librarian inst;
    @SidedProxy(clientSide="csokicraft.forge18.librarian.ClientProxy", serverSide="csokicraft.forge18.librarian.CommonProxy")
    public static CommonProxy proxy;
    
    private Configuration conf;
    
	public static int GUI_ID;
    public static Block shelf=new BlockLibraryShelf().setHardness(1f).setCreativeTab(CreativeTabs.tabDecorations).setUnlocalizedName("libraryShelf");
    public static boolean isShelfUni;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
    	conf=new Configuration(e.getSuggestedConfigurationFile());
    	conf.load();
    	isShelfUni=conf.getBoolean("Universal shelves", "misc", false, "Shelves accept all items");
    	conf.save();
    	
    	BookRegistry.inst.registerAsBook(new ItemStack(Blocks.bookshelf));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.book));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.written_book));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.enchanted_book));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.writable_book));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.map));
    	//BookRegistry.inst.registerAsBook(new ItemStack(Items.filled_map, 1, -1)); //having problems with this one, maybe later when it's fixed
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.paper));
    	BookRegistry.inst.registerAsBook(new ItemStack(shelf));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e){
		GameRegistry.registerBlock(shelf, "libraryShelf");
		NetworkRegistry.INSTANCE.registerGuiHandler(inst, proxy);
		GameRegistry.registerTileEntity(TileEntityLibraryShelf.class, "Librarian:libraryShelf");
		GameRegistry.addRecipe(new ItemStack(shelf, 8),
				"BBB",
				"BCB",
				"BBB",
				'B', Blocks.bookshelf,
				'C', Blocks.chest);
		proxy.registerItemModels();
    }
    
}
