package dev.thephotography.registry;

import dev.thephotography.ThePhotography;
import dev.thephotography.item.CameraItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ThePhotography.MOD_ID);

    public static final DeferredItem<Item> CAMERA = ITEMS.register("camera",
            () -> new CameraItem(new Item.Properties().stacksTo(1)));

    private ModItems() {}
}
