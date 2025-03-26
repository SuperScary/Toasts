package net.superscary.toasts;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.nio.file.Path;

import static net.superscary.toasts.Constants.MOD_ID;

@Mod("toasts")
@OnlyIn(Dist.CLIENT)
public class ToastsNeo {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToastsNeo.class);
    private static final ResourceLocation TOAST_TEXTURE = ResourceLocation.fromNamespaceAndPath("toasts", "textures/gui/toasts.png");

    public ToastsNeo() {
        // Load toasts from config
        Path configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config");
        ToastLoader.loadToasts(configDir).forEach(Toasts::register);
    }

    @SubscribeEvent
    public static void onRegisterGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.read(MOD_ID).getOrThrow(), (gui, deltaTracker) -> {
            ToastManager manager = ToastManager.getInstance();
            if (manager.isShowingToast()) {
                renderToast(gui, manager.getCurrentToast());
            }
        });
    }

    private static void renderToast(GuiGraphics graphics, CustomToast toast) {
        int width = 160;
        int height = 32;
        int x = graphics.guiWidth() - width - 16;
        int y = 16;

        // Draw background
        Color bgColor = toast.getBackground();
        graphics.fill(x, y, x + width, y + height, 
            (bgColor.getAlpha() << 24) | (bgColor.getRed() << 16) | (bgColor.getGreen() << 8) | bgColor.getBlue());

        // Draw icon if present
        if (toast.getIcon() != null) {
            ItemStack iconStack = new ItemStack(Items.BOOK); // Default to book if icon not found
            try {
                // TODO: Implement proper icon loading from ResourceLocation
                graphics.renderItem(iconStack, x + 8, y + 8);
            } catch (Exception e) {
                LOGGER.error("Failed to render toast icon", e);
            }
        }

        // Draw title
        Color titleColor = toast.getTextColor();
        graphics.drawString(Minecraft.getInstance().font, 
            Component.literal(toast.getTitle()),
            x + (toast.getIcon() != null ? 32 : 8), y + 8,
            (titleColor.getAlpha() << 24) | (titleColor.getRed() << 16) | (titleColor.getGreen() << 8) | titleColor.getBlue());

        // Draw subtitle if present
        if (toast.getSubtitle() != null) {
            Color subtitleColor = toast.getSubtitleColor();
            graphics.drawString(Minecraft.getInstance().font,
                Component.literal(toast.getSubtitle()),
                x + (toast.getIcon() != null ? 32 : 8), y + 20,
                (subtitleColor.getAlpha() << 24) | (subtitleColor.getRed() << 16) | (subtitleColor.getGreen() << 8) | subtitleColor.getBlue());
        }
    }
}