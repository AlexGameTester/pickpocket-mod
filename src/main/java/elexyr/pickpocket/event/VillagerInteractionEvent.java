package elexyr.pickpocket.event;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.CapabilityPickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import elexyr.pickpocket.capability.pocketowner.CapabilityPocketOwner;
import elexyr.pickpocket.capability.pocketowner.IPocketOwner;
import elexyr.pickpocket.container.ReadonlyChestContainer;
import elexyr.pickpocket.util.ReadonlySlotFactory;
import elexyr.pickpocket.util.SkillUtils;
import elexyr.pickpocket.util.VillagerUtils;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Pickpocket.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VillagerInteractionEvent {

    private static final TranslationTextComponent CONTAINER_NAME = new TranslationTextComponent("container.villager_pocket");
//TODO: Why is this event handler called twice?
    @SubscribeEvent
    public static void bedInteractionEvent(final PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockState blockState = world.getBlockState(event.getPos());

        if (!(blockState.getBlock() instanceof BedBlock)) {
            return;
        }
        BlockPos headPos = event.getPos();
        if (blockState.get(BedBlock.PART) == BedPart.FOOT) {
            Direction direction = blockState.get(BedBlock.HORIZONTAL_FACING);
            headPos = headPos.offset(direction);
        }

        List<VillagerEntity> sleepingVillagers = world.getEntitiesWithinAABB(VillagerEntity.class, new AxisAlignedBB(headPos), LivingEntity::isSleeping);
        PlayerEntity player = event.getPlayer();
        if (!sleepingVillagers.isEmpty() && player.isShiftKeyDown()) {
            event.setResult(Event.Result.DENY);
            event.setUseBlock(Event.Result.DENY);
            if (world.isRemote) {
                return;
            }

            VillagerEntity villagerEntity = sleepingVillagers.get(0);
            List<ItemStack> sellingStacks = VillagerUtils.getSellingStacks(villagerEntity);
            LazyOptional<IPickpocket> playerCapability = player.getCapability(CapabilityPickpocket.PICKPOCKET_CAPABILITY);
            LazyOptional<IPocketOwner> villagerCapability = villagerEntity.getCapability(CapabilityPocketOwner.POCKET_OWNER_CAPABILITY);
            playerCapability.ifPresent(cap -> Pickpocket.debug("Player's amount of skill is " + cap.getSkill()));
            Pickpocket.debug("Villager sells these stacks: " + sellingStacks);

            ArrayList<ItemStack> stolenStacks = new ArrayList<>();
            for (ItemStack stack : sellingStacks) {
                ItemStack stolenStack = SkillUtils.getStolenStack(player, stack, villagerEntity);
                if (stolenStack.isEmpty()) {
                    continue;
                }
                stolenStacks.add(stolenStack);
                SkillUtils.updateSkillFromStack(playerCapability, stack);
            }
            villagerCapability.ifPresent(cap -> Pickpocket.debug(String.format("Villager has %d robberies", cap.getRobberiesCount())));

            villagerCapability.ifPresent(IPocketOwner::addRobbery);

            //TODO: Replace it with readonly container
            player.openContainer(new SimpleNamedContainerProvider(
                    (id, playerInventory, p3) -> {
                        ReadonlyChestContainer container = ReadonlyChestContainer.createGeneric(id, playerInventory, 2);
                        container.setAll(stolenStacks);
                        return container;
                    },
                    CONTAINER_NAME));
        }
    }
}
