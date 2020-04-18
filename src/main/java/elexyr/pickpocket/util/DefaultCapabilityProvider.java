package elexyr.pickpocket.util;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class DefaultCapabilityProvider<K> implements ICapabilityProvider {

    private LazyOptional<K> capabilityInstance;
    private Capability targetCap;

    public DefaultCapabilityProvider(NonNullSupplier<K> supplier, Capability targetCap) {
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
}
