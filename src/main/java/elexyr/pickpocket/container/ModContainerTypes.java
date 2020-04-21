package elexyr.pickpocket.container;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.container.gui.ReadonlyChestScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Pickpocket.MODID);

    /**
     * Map key is number of rows in the container
     * @see #registerReadonlyContainers()
     */
    public static final Map<Integer, RegistryObject<ContainerType<ReadonlyChestContainer>>> READONLY_CONTAINER_TYPES = registerReadonlyContainers();

    private static Map<Integer, RegistryObject<ContainerType<ReadonlyChestContainer>>> registerReadonlyContainers() {
        Pickpocket.debug("Registering readonly containers 122332");
        HashMap<Integer, RegistryObject<ContainerType<ReadonlyChestContainer>>> map = new HashMap<>();
        for (int i = 1; i <= 6; i++) {
            final int finalI = i;
            map.put(
                    finalI,
                    CONTAINER_TYPES.register("readonly_9x" + finalI,
                            //Data is just ignored here and I don't know if it'll cause any bugs with client-server sync
                            () -> IForgeContainerType.create((windowId, inv, data) -> ReadonlyChestContainer.createGeneric(windowId, inv, finalI)))
            );
        }

        return map;
    }
}
