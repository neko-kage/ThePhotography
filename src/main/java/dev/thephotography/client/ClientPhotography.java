package dev.thephotography.client;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec2;
import net.minecraft.client.Screenshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ClientPhotography {
    private static final int[] FOCAL_LENGTHS = {28, 50, 85};
    private static int focalIndex = 1;
    private static NativeImage lastImage;
    private static ResourceLocation textureId;

    private ClientPhotography() {}

    public static int cycleFocalLength() {
        focalIndex = (focalIndex + 1) % FOCAL_LENGTHS.length;
        return FOCAL_LENGTHS[focalIndex];
    }

    public static void captureAndOpenViewer() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null || minecraft.getMainRenderTarget() == null) {
            return;
        }

        NativeImage raw = Screenshot.takeScreenshot(minecraft.getMainRenderTarget());
        NativeImage processed = cropForFocalLength(raw, FOCAL_LENGTHS[focalIndex]);
        raw.close();

        try {
            Path directory = minecraft.gameDirectory.toPath().resolve("thephotography").resolve("photos");
            Files.createDirectories(directory);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss-SSS"));
            Path output = directory.resolve("photo-" + timestamp + "-" + FOCAL_LENGTHS[focalIndex] + "mm.png");
            processed.writeToFile(output);
            if (lastImage != null) lastImage.close();
            lastImage = processed;
            if (textureId != null) {
                minecraft.getTextureManager().release(textureId);
            }
            textureId = ResourceLocation.fromNamespaceAndPath("thephotography", "photo_" + timestamp.replace('-', '_'));
            minecraft.getTextureManager().register(textureId, new DynamicTexture(() -> "The Photography captured photo", processed));
            minecraft.setScreen(new PhotoViewerScreen(textureId, output.getFileName().toString(), FOCAL_LENGTHS[focalIndex]));
            minecraft.player.displayClientMessage(Component.literal("Photo saved: " + output), true);
        } catch (IOException exception) {
            processed.close();
            minecraft.player.displayClientMessage(Component.literal("Photo failed: " + exception.getMessage()), true);
        }
    }

    private static NativeImage cropForFocalLength(NativeImage source, int focalLength) {
        if (focalLength == 28) return source;
        int width = source.getWidth();
        int height = source.getHeight();
        double factor = focalLength == 50 ? 0.72 : 0.48;
        int cropWidth = Math.max(1, (int) (width * factor));
        int cropHeight = Math.max(1, (int) (height * factor));
        int left = (width - cropWidth) / 2;
        int top = (height - cropHeight) / 2;
        NativeImage cropped = new NativeImage(cropWidth, cropHeight, false);
        for (int y = 0; y < cropHeight; y++) {
            for (int x = 0; x < cropWidth; x++) {
                cropped.setPixelRGBA(x, y, source.getPixelRGBA(left + x, top + y));
            }
        }
        return cropped;
    }
}
