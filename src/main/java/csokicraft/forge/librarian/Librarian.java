package csokicraft.forge.librarian;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Librarian.MODID, version = Librarian.VERSION)
@EventBusSubscriber
public class Librarian {
    public static final String MODID = "librarian";
    public static final String VERSION = "1.0.4";
    @Instance
	public static Librarian inst;
    @SidedProxy(clientSide="csokicraft.forge.librarian.ClientProxy", serverSide="csokicraft.forge.librarian.CommonProxy")
    public static CommonProxy proxy;
    
    private Configuration conf;
    
	public static int GUI_ID;
    public static Block shelf=new BlockLibraryShelf().setHardness(1f).setCreativeTab(CreativeTabs.DECORATIONS).setUnlocalizedName("libraryShelf").setRegistryName("library_shelf");
    public static Item shelfItem=new ItemBlock(shelf).setRegistryName(shelf.getRegistryName());
    public static boolean isShelfUni;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent e){
    	conf=new Configuration(e.getSuggestedConfigurationFile());
    	conf.load();
    	isShelfUni=conf.getBoolean("Universal shelves", "misc", false, "Shelves accept all items");
    	conf.save();
    	
    	BookRegistry.inst.registerAsBook(new ItemStack(Blocks.BOOKSHELF));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.BOOK));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.WRITTEN_BOOK));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.ENCHANTED_BOOK));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.WRITABLE_BOOK));
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.MAP));
    	//BookRegistry.inst.registerAsBook(new ItemStack(Items.filled_map, 1, -1)); //having problems with this one, maybe later when it's fixed
    	BookRegistry.inst.registerAsBook(new ItemStack(Items.PAPER));
    	BookRegistry.inst.registerAsBook(new ItemStack(shelf));
    }
    
    @EventHandler
    public void init(FMLInitializationEvent e){
		NetworkRegistry.INSTANCE.registerGuiHandler(inst, proxy);
		GameRegistry.registerTileEntity(TileEntityLibraryShelf.class, "Librarian:libraryShelf");
		proxy.registerItemModels();
    }
    
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> e){
		e.getRegistry().register(shelf);
	}
    
    @SubscribeEvent
    public static void registerItems(final RegistryEvent.Register<Item> e){
		e.getRegistry().register(shelfItem);
	}
}
