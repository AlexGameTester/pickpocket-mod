package elexyr.pickpocket;

import elexyr.pickpocket.capability.pocketowner.CapabilityPocketOwner;
import elexyr.pickpocket.item.ModItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Pickpocket.MODID)
public class Pickpocket {
    public static final String MODID = "pickpocket";
    public static final Logger LOGGER = LogManager.getLogger();

    public Pickpocket() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        ModItems.ITEMS.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityPocketOwner.register();
    }
}
