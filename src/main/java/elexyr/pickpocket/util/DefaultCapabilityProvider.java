package elexyr.pickpocket.util;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DefaultCapabilityProvider<K> implements ICapabilityProvider, INBTSerializable<INBT> {
    protected LazyOptional<K> capabilityInstance;

    private Capability<K> targetCap;

    public DefaultCapabilityProvider(NonNullSupplier<K> supplier, Capability<K> targetCap) {
        this.capabilityInstance = LazyOptional.of(supplier);
        this.targetCap = targetCap;
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == targetCap) {
            return capabilityInstance.cast();
        } else {
            return LazyOptional.empty();
        }
    }

    public LazyOptional<K> getCapability() {
        return capabilityInstance;
    }

    @Override
    public INBT serializeNBT() {
        INBT[] nbt = {null};
        capabilityInstance.ifPresent(cap -> nbt[0] = targetCap.writeNBT(cap, null));
        return nbt[0];
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        capabilityInstance.ifPresent(cap -> targetCap.readNBT(cap, null, nbt));
    }
}
