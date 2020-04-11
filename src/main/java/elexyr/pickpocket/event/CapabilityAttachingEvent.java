package elexyr.pickpocket.event;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.PickpocketPlayer;
import elexyr.pickpocket.capability.pocketowner.VillagerPocketOwner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Pickpocket.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityAttachingEvent {

    @SubscribeEvent
    public static void villagerCapabilitiesEventHandler(final AttachCapabilitiesEvent<Entity> event) {
        Pickpocket.LOGGER.info("Attaching capability");
        if (event.getObject() instanceof VillagerEntity) {
            event.addCapability(new ResourceLocation(Pickpocket.MODID, "pocket_owner_capability"),
                    new ICapabilityProvider() {
                        @Nonnull
                        @Override
                        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                            return LazyOptional.of(VillagerPocketOwner::new).cast();
                        }
                    });
        }

        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Pickpocket.MODID, "pickpocket_capability"),
                    new ICapabilityProvider() {
                        @Nonnull
                        @Override
                        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                            return LazyOptional.of(PickpocketPlayer::new).cast();
                        }
            });
        }
    }
}
