package dev.thephotography.item;

import dev.thephotography.client.ClientPhotography;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CameraItem extends Item {
    public CameraItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide && player.isCrouching()) {
            int next = ClientPhotography.cycleFocalLength();
            player.displayClientMessage(Component.literal("The Photography · " + next + " mm"), true);
            return InteractionResultHolder.success(stack);
        }
        if (level.isClientSide) {
            ClientPhotography.captureAndOpenViewer();
        }
        return InteractionResultHolder.success(stack);
    }
}
