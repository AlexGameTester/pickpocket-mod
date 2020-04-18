package elexyr.pickpocket.capability.pickpocket;

import elexyr.pickpocket.Pickpocket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.FloatNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class CapabilityPickpocket {

    private static final String TAG_NAME = "pickpocket_skill";

    @CapabilityInject(IPickpocket.class)
    public static Capability<IPickpocket> PICKPOCKET_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPickpocket.class,
                new Capability.IStorage<IPickpocket>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IPickpocket> capability, IPickpocket instance, Direction side) {
                        CompoundNBT nbt = new CompoundNBT();
                        nbt.putFloat(TAG_NAME, instance.getSkill());
                        return nbt;
                    }

                    @Override
                    public void readNBT(Capability<IPickpocket> capability, IPickpocket instance, Direction side, INBT nbt) {
                        instance.setStealingSkill(((CompoundNBT) nbt).getFloat(TAG_NAME));
                    }
                },
                PickpocketPlayer::new);
    }
}
