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
    public static Capability<IPocketOwner> POCKET_HANDLER_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPocketOwner.class,
                new Capability.IStorage<IPocketOwner>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IPocketOwner> capability, IPocketOwner instance, Direction side) {
                        CompoundNBT nbt = new CompoundNBT();
                        nbt.putInt("robberies_count", instance.getRobberiesCount());
                        //TODO: Also write items to nbt
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
