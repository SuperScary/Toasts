package net.superscary.toasts;

import net.minecraft.resources.ResourceLocation;
import java.awt.Color;

public class CustomToast {
    private final String id;
    private final String title;
    private final String subtitle;
    private final ResourceLocation icon;
    private final Color background;
    private final Color textColor;
    private final Color subtitleColor;
    private final int duration;

    private CustomToast(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.subtitle = builder.subtitle;
        this.icon = builder.icon;
        this.background = builder.background;
        this.textColor = builder.textColor;
        this.subtitleColor = builder.subtitleColor;
        this.duration = builder.duration;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public ResourceLocation getIcon() { return icon; }
    public Color getBackground() { return background; }
    public Color getTextColor() { return textColor; }
    public Color getSubtitleColor() { return subtitleColor; }
    public int getDuration() { return duration; }

    public static class Builder {
        private String id;
        private String title;
        private String subtitle;
        private ResourceLocation icon;
        private Color background = new Color(34, 34, 34); // Default dark gray
        private Color textColor = Color.WHITE;
        private Color subtitleColor = new Color(204, 204, 204); // Default light gray
        private int duration = 80; // Default duration

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public Builder icon(String iconId) {
            this.icon = ResourceLocation.fromNamespaceAndPath(iconId.split(":")[0], iconId.split(":")[1]);
            return this;
        }

        public Builder background(Color background) {
            this.background = background;
            return this;
        }

        public Builder textColor(Color textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder subtitleColor(Color subtitleColor) {
            this.subtitleColor = subtitleColor;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public CustomToast build() {
            if (id == null || title == null) {
                throw new IllegalStateException("ID and title are required for a toast");
            }
            return new CustomToast(this);
        }
    }
} 