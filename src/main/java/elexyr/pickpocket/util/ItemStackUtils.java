package elexyr.pickpocket.util;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemStackUtils {
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
        List<ItemStack> sumList = copyList(list1);
        sumList.addAll(list2);
        return mergeEqualItems(sumList);
    }

    /*
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

     */
    public static List<ItemStack> mergeEqualItems(List<ItemStack> list) {
        List<ItemStack> listCopy = copyList(list);
        for (int i = 0; i < listCopy.size(); i++) {
            for (int j = i + 1; j < listCopy.size(); j++) {
                //TODO: Should use this comparison method
                if (ItemStack.areItemStackTagsEqual(listCopy.get(i), listCopy.get(j))) {
                    listCopy.get(i).setCount(listCopy.get(i).getCount() + listCopy.get(j).getCount());
                    listCopy.remove(j);
                }
            }
        }

        return listCopy;
    }

    private static List<ItemStack> copyList(List<ItemStack> list) {
        return list.stream().map(ItemStack::copy).collect(Collectors.toList());
    }

}
