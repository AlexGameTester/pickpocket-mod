package elexyr.pickpocket.event;

import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.CapabilityPickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import elexyr.pickpocket.capability.pocketowner.CapabilityPocketOwner;
import elexyr.pickpocket.capability.pocketowner.IPocketOwner;
import elexyr.pickpocket.util.SkillUtils;
import elexyr.pickpocket.util.VillagerUtils;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
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
            if (world.isRemote) {
                return;
            }

            VillagerEntity villagerEntity = sleepingVillagers.get(1);
            List<ItemStack> sellingStacks = VillagerUtils.getSellingStacks(villagerEntity);
            LazyOptional<IPickpocket> playerCapability = player.getCapability(CapabilityPickpocket.PICKPOCKET_CAPABILITY);


            ArrayList<ItemStack> stolenStacks = new ArrayList<>();
            for (ItemStack stack : sellingStacks) {
                ItemStack stolenStack = SkillUtils.getStolenStack(player, stack, villagerEntity);
                if (stolenStack.isEmpty()) {
                    continue;
                }
                stolenStacks.add(stolenStack);
                SkillUtils.updateSkillFromStack(playerCapability, stack);
            }

            //TODO: Replace it with readonly container
            player.openContainer(new SimpleNamedContainerProvider(
                    //Don't know what these parameters mean. From EnderChestTileEntity
                    (p1, p2, p3) -> {
                        ChestContainer container = ChestContainer.createGeneric9X2(p1, p2);
                        //TODO: What if length of the list is bigger than container slots number - vanilla maximum amount of trades = 10
                        container.setAll(stolenStacks);
//                            Pickpocket.LOGGER.info("If chest container can interact with player: " + container.canInteractWith(event.getPlayer()));
                        return container;
                    },
                    new TranslationTextComponent("container.villager_pocket")));
        }
    }

    /*
    //TODO: Work out remote/not remote worlds. Rewrite in more orderly way
    @SubscribeEvent
    public static void bedVillagerStealEvent(final PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();

        if (world.isRemote) {
            return;
        }

        BlockState state = world.getBlockState(event.getPos());
        if (state.getBlock() instanceof BedBlock) {

            if (!world.isRemote) {
                event.setResult(Event.Result.DENY);
                return;
            }

            BlockPos headPos = event.getPos();
            if (state.get(BedBlock.PART) == BedPart.FOOT) {
                Direction direction = state.get(BedBlock.HORIZONTAL_FACING);
                headPos = headPos.offset(direction);
            }

            Pickpocket.LOGGER.info("Head position is " + headPos);

            List<VillagerEntity> sleepingVillagers = world.getEntitiesWithinAABB(VillagerEntity.class, new AxisAlignedBB(headPos), LivingEntity::isSleeping);

            PlayerEntity player = event.getPlayer();

            if (!sleepingVillagers.isEmpty() && player.isShiftKeyDown()) {
                VillagerEntity villagerEntity = sleepingVillagers.get(0);
                /*
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("List of items this villager sells: [");
                villagerEntity.getOffers().forEach((x) -> {
                    stringBuilder.append(x.getCopyOfSellingStack().getItem().getName().getFormattedText()).append(", ");
                });
                stringBuilder.append("].");
                checkCapability(villagerEntity, player);

                List<ItemStack> villagerSellingStacks = VillagerUtils.getSellingStacks(villagerEntity);
//                event.getPlayer().sendStatusMessage(new StringTextComponent(stringBuilder.toString()), false);
                player.openContainer(new SimpleNamedContainerProvider(
                        //Don't know what these parameters mean. From EnderChestTileEntity
                        (p1, p2, p3) -> {
                            ChestContainer container = ChestContainer.createGeneric9X2(p1, p2);
                            //TODO: What if length of the list is bigger than container slots number - vanilla maximum amount of trades = 10
                            container.setAll(villagerSellingStacks);
//                            Pickpocket.LOGGER.info("If chest container can interact with player: " + container.canInteractWith(event.getPlayer()));
                            return container;
                        },
                        new TranslationTextComponent("container.villager_pocket")));
                player.setSneaking(true);
                event.setUseBlock(Event.Result.DENY);
            }
        }
    }

    private static void checkCapability(VillagerEntity villagerEntity, PlayerEntity playerEntity) {
        LazyOptional<IPocketOwner> capability = villagerEntity.getCapability(CapabilityPocketOwner.POCKET_OWNER_CAPABILITY);
        if (capability.isPresent()) {
            capability.ifPresent(cap -> {
                cap.addRobbery();
                playerEntity.sendStatusMessage(new StringTextComponent(String.format("This villager has been robbed %d times", cap.getRobberiesCount())), false);
            });
            Pickpocket.LOGGER.info("Villager has capability");
        }
        else {
            Pickpocket.LOGGER.info("Villager hasn't this capability.");
        }
    }
        */

}
