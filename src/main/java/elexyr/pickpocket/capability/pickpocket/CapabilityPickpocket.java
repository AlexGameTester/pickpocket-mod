package elexyr.pickpocket.capability.pickpocket;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPickpocket {

    @CapabilityInject(IPickpocket.class)
    public static Capability<IPickpocket> PICKPOCKET_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPickpocket.class,
                new Capability.IStorage<IPickpocket>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IPickpocket> capability, IPickpocket instance, Direction side) {
                        CompoundNBT nbt = new CompoundNBT();
                        nbt.putFloat("pickpocket_skill", instance.getSkill());
                        return nbt;
                    }

                    @Override
                    public void readNBT(Capability<IPickpocket> capability, IPickpocket instance, Direction side, INBT nbt) {
                        CompoundNBT compoundNBT = (CompoundNBT) nbt;
                        instance.setStealingSkill(compoundNBT.getFloat("pickpocket_skill"));
                    }
                },
                PickpocketPlayer::new);
    }
}
