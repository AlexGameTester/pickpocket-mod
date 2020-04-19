package elexyr.pickpocket.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import elexyr.pickpocket.Pickpocket;
import elexyr.pickpocket.capability.pickpocket.CapabilityPickpocket;
import elexyr.pickpocket.capability.pickpocket.IPickpocket;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.server.command.EnumArgument;

public class SkillCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        Pickpocket.debug("Command registered");
        /*
        dispatcher.register(Commands.literal("skill")
                    .then(Commands.literal("get"))
                        .then(Commands.argument("player", EntityArgument.player()))
                            .executes(context -> printSkill(EntityArgument.getPlayer(context, "player")))

                    .then(Commands.literal("set")).requires(commandSource -> commandSource.hasPermissionLevel(2))
                        .then(Commands.argument("player", EntityArgument.player()))
                        .then(Commands.argument("value", FloatArgumentType.floatArg(0)))
                            .executes(context -> setSkill(EntityArgument.getPlayer(context, "player"), FloatArgumentType.getFloat(context, "value")))

        );

         */

        dispatcher.register(
            Commands.literal("skill")
            .then(
                    Commands.literal("get")
                    .then(
                            Commands.argument("target", EntityArgument.player())
                            .executes(context -> printSkill(EntityArgument.getPlayer(context, "target")))
                    )
                    .executes(context -> printSkill(context.getSource().asPlayer()))
            )
            .then(
                    Commands.literal("set").requires(commandSource -> commandSource.hasPermissionLevel(2))
                    .then(
                            Commands.argument("target", EntityArgument.player())
                            .then(
                                    Commands.argument("value", FloatArgumentType.floatArg(0))
                                    .executes(context -> setSkill(EntityArgument.getPlayer(context, "target"), FloatArgumentType.getFloat(context, "value")))
                            )
                    )
            )
        );
    }

    //TODO: Make multilingual
    private static int printSkill(PlayerEntity playerEntity) {
        LazyOptional<IPickpocket> capability = playerEntity.getCapability(CapabilityPickpocket.PICKPOCKET_CAPABILITY);
        capability.ifPresent(cap ->
                playerEntity.sendStatusMessage(new StringTextComponent("Player has skill of " + cap.getSkill()), true)
        );

        return 0;
    }

    private static int setSkill(PlayerEntity playerEntity, float value) {
        LazyOptional<IPickpocket> capability = playerEntity.getCapability(CapabilityPickpocket.PICKPOCKET_CAPABILITY);
        capability.ifPresent(cap -> cap.setStealingSkill(value));

        return 0;
    }

    private static int addSkill(PlayerEntity playerEntity, float value) {

        return 1;
    }

    private enum SkillActionType {
        get,
        set
    }
}
