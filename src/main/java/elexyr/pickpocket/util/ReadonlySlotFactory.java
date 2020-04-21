package elexyr.pickpocket.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ReadonlySlotFactory {
    public static Slot create(IInventory inventoryIn, int index, int posX, int posY) {
        return new Slot(inventoryIn, index, posX, posY) {
            private int dummyField = 1;

            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.isEmpty();
            }
        };
    }

    public static Slot fromSlot(Slot slotIn) {
        return create(slotIn.inventory, slotIn.slotNumber, slotIn.xPos, slotIn.yPos);
    }
}
