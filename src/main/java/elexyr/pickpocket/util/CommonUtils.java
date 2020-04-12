package elexyr.pickpocket.util;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {
    //TODO: Isn't this too complicated
    public static List<ItemStack> differenceBetween(List<ItemStack> bigger, List<ItemStack> smaller) {
        ArrayList<ItemStack> difference = new ArrayList<>();
        for (ItemStack biggerStack: bigger) {
            for (ItemStack smallerStack: smaller) {
                if (!biggerStack.isEmpty() && biggerStack.isItemEqual(smallerStack)) {
                    int countDiff = biggerStack.getCount() - smallerStack.getCount();
                    if (countDiff > 0) {
                        ItemStack stackDiff = biggerStack.copy();
                        stackDiff.setCount(countDiff);
                        difference.add(stackDiff);
                    }
                }
            }
        }

        return difference;
    }

    public static List<ItemStack> sum(List<ItemStack> list1, List<ItemStack> list2) {
        //Creates copy of list1 with copies of stacks
        ArrayList<ItemStack> sum = list1.stream().map(ItemStack::copy).collect(Collectors.toCollection(ArrayList::new));
        for(ItemStack stack2 : list2) {
            }
        }
    }

    private static int indexOfItem(ItemStack stack, List<ItemStack> list) {
        int index = -1;
        if (stack.isEmpty()) {
            return index;
        }

        for (int i = 0; i < list.size(); i++) {
            ItemStack element = list.get(i);
            if (stack.isItemEqual())
        }
    }

    public List<ItemStack> mergeEqualItems(List<ItemStack> list) {
        List<ItemStack> mergedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                //TODO: Should use this comparison method
                if (ItemStack.areItemStackTagsEqual(list.get(i), list.get(j)))
            }
        }
    }
}
