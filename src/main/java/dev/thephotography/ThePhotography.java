package dev.thephotography;

import dev.thephotography.registry.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ThePhotography.MOD_ID)
public final class ThePhotography {
    public static final String MOD_ID = "thephotography";

    public ThePhotography() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modBus);
    }
}
