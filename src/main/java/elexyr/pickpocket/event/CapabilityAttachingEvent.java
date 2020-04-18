package elexyr.pickpocket.event;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.CapabilityPickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import elexyr.pickpocket.capability.pickpocket.PickpocketPlayer;
import elexyr.pickpocket.capability.pocketowner.CapabilityPocketOwner;
import elexyr.pickpocket.capability.pocketowner.IPocketOwner;
import elexyr.pickpocket.capability.pocketowner.VillagerPocketOwner;
import elexyr.pickpocket.util.DefaultCapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityProvider;
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
        if (event.getObject() instanceof VillagerEntity) {
            event.addCapability(new ResourceLocation(Pickpocket.MODID, "pocket_owner_capability_" + event.getObject().getCachedUniqueIdString()),
                    new DefaultCapabilityProvider<IPocketOwner>(VillagerPocketOwner::new, CapabilityPocketOwner.POCKET_OWNER_CAPABILITY));
        }

        if (event.getObject() instanceof PlayerEntity && !event.getObject().getCapability(CapabilityPickpocket.PICKPOCKET_CAPABILITY).isPresent()) {
            event.addCapability(new ResourceLocation(Pickpocket.MODID, "pickpocket_capability_" + event.getObject().getCachedUniqueIdString()),
                    new DefaultCapabilityProvider<IPickpocket>(PickpocketPlayer::new, CapabilityPickpocket.PICKPOCKET_CAPABILITY));
        }
    }
}
