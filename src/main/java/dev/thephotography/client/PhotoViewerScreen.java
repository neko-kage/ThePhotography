package dev.thephotography.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PhotoViewerScreen extends Screen {
    private final ResourceLocation texture;
    private final String filename;
    private final int focalLength;

    public PhotoViewerScreen(ResourceLocation texture, String filename, int focalLength) {
        super(Component.literal("The Photography · Photo Viewer"));
        this.texture = texture;
        this.filename = filename;
        this.focalLength = focalLength;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics, mouseX, mouseY, partialTick);
        int margin = 24;
        int maxWidth = this.width - margin * 2;
        int maxHeight = this.height - 90;
        graphics.fill(margin - 2, 28, this.width - margin + 2, this.height - 42, 0xFF171717);
        graphics.blit(texture, margin, 30, 0, 0, maxWidth, maxHeight, maxWidth, maxHeight);
        graphics.drawCenteredString(this.font, "The Photography", this.width / 2, 8, 0xFFFFFF);
        graphics.drawCenteredString(this.font, focalLength + " mm · " + filename, this.width / 2, this.height - 32, 0xE0E0E0);
        graphics.drawCenteredString(this.font, "Esc: close · PNG saved to thephotography/photos", this.width / 2, this.height - 18, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
