package elexyr.pickpocket.container;

import elexyr.pickpocket.util.ReadonlySlotFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class ReadonlyChestContainer extends Container {
    private final IInventory lowerChestInventory;
    private final int numRows;

    public static ReadonlyChestContainer createGeneric(int id, PlayerInventory playerInventory, int rows) {
        if (rows < 1 || rows > 6) {
            throw new IllegalArgumentException(String.format("This (%d) number of rows is not allowed. Must be from 1 to 6", rows));
        }

        return new ReadonlyChestContainer(getGenericContainer(rows), id, playerInventory, new Inventory(9 * rows), rows);
    }

    private ReadonlyChestContainer(ContainerType<?> type, int id, PlayerInventory playerInventoryIn, IInventory inventory, int rows) {
        super(type, id);
        assertInventorySize(inventory, rows * 9);
        this.lowerChestInventory = inventory;
        this.numRows = rows;
        inventory.openInventory(playerInventoryIn.player);
        int i = (this.numRows - 4) * 18;

        for(int j = 0; j < this.numRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(ReadonlySlotFactory.create(inventory, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventoryIn, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventoryIn, i1, 8 + i1 * 18, 161 + i));
        }

    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.lowerChestInventory.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < this.numRows * 9) {
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    public int getNumRows() {
        return numRows;
    }

    private static ContainerType<ReadonlyChestContainer> getGenericContainer(int rows) {
        return ModContainerTypes.READONLY_CONTAINER_TYPES.get(rows).get();
    }
}
