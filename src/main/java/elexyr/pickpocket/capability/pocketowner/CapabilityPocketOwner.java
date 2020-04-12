package elexyr.pickpocket.capability.pocketowner;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPocketOwner {

    @CapabilityInject(IPocketOwner.class)
    public static Capability<IPocketOwner> POCKET_OWNER_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPocketOwner.class,
                new Capability.IStorage<IPocketOwner>() {

                    @Override
                    public INBT writeNBT(Capability<IPocketOwner> capability, IPocketOwner instance, Direction side) {
                        CompoundNBT nbt = new CompoundNBT();
                        nbt.putInt("robberies_count", instance.getRobberiesCount());
                        //TODO: Also write items to nbt. Maybe it's better to use dictionary of (ItemStack of 1 item, number of items of this type stolen)
                        return nbt;
                    }

                    @Override
                    public void readNBT(Capability<IPocketOwner> capability, IPocketOwner instance, Direction side, INBT nbt) {
                        CompoundNBT compoundNBT = (CompoundNBT) nbt;
                        instance.setRobberiesCount(compoundNBT.getInt("robberies_count"));
                    }
                },
                VillagerPocketOwner::new);
    }
}
