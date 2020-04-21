package elexyr.pickpocket;

import elexyr.pickpocket.capability.pickpocket.CapabilityPickpocket;
import elexyr.pickpocket.capability.pocketowner.CapabilityPocketOwner;
import elexyr.pickpocket.container.ModContainerTypes;
import elexyr.pickpocket.container.gui.ReadonlyChestScreen;
import elexyr.pickpocket.item.ModItems;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Pickpocket.MODID)
public class Pickpocket {
    public static final String MODID = "pickpocket";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup ITEM_GROUP = ItemGroup.MISC;

    public Pickpocket() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::preInit);
        modEventBus.addListener(this::preInitClient);

        ModItems.ITEMS.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void preInit(final FMLCommonSetupEvent event) {
        CapabilityPocketOwner.register();
        CapabilityPickpocket.register();
    }

    private void preInitClient(final FMLClientSetupEvent event) {
        ModContainerTypes.READONLY_CONTAINER_TYPES.values().forEach(containerTypeRegistryObject ->
        {
            ScreenManager.registerFactory(containerTypeRegistryObject.get(), ReadonlyChestScreen::new);
        }
        );
    }

    public static void debug(String msg) {
        LOGGER.debug(msg);
    }

}
