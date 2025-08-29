package com.artillexstudios.axholograms.data;

import com.artillexstudios.axapi.config.adapters.MapConfigurationGetter;
import com.artillexstudios.axapi.hologram.page.HologramPage;
import com.artillexstudios.axapi.packetentity.meta.entity.TextDisplayMeta;
import com.artillexstudios.axholograms.api.holograms.data.TextDisplayHologramPageData;
import com.artillexstudios.axholograms.api.holograms.type.HologramType;
import com.artillexstudios.axholograms.api.holograms.type.HologramTypes;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TextHologramPageData extends DisplayEntityHologramPageData implements TextDisplayHologramPageData {
    private String content;
    private Integer lineWidth;
    private Integer backgroundColor;
    private Byte textOpacity;
    private TextDisplayMeta.Alignment alignment;
    private Boolean hasShadow;
    private Boolean isSeeThrough;
    private Boolean hasDefaultBackground;

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public void setContent(String content) {
        boolean changed = !Objects.equals(this.content, content);
        this.content = content;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public int getLineWidth() {
        return this.lineWidth;
    }

    @Override
    public void setLineWidth(int lineWidth) {
        boolean changed = !Objects.equals(this.lineWidth, lineWidth);
        this.lineWidth = lineWidth;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public int getBackgroundColor() {
        return this.backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        boolean changed = !Objects.equals(this.backgroundColor, backgroundColor);
        this.backgroundColor = backgroundColor;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public byte getTextOpacity() {
        return this.textOpacity;
    }

    @Override
    public void setTextOpacity(byte textOpacity) {
        boolean changed = !Objects.equals(this.textOpacity, textOpacity);
        this.textOpacity = textOpacity;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public TextDisplayMeta.Alignment getAlignment() {
        return this.alignment;
    }

    @Override
    public void setAlignment(TextDisplayMeta.Alignment alignment) {
        boolean changed = !Objects.equals(this.alignment, alignment);
        this.alignment = alignment;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public boolean hasShadow() {
        return this.hasShadow;
    }

    @Override
    public void setHasShadow(boolean hasShadow) {
        boolean changed = !Objects.equals(this.hasShadow, hasShadow);
        this.hasShadow = hasShadow;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public boolean isSeeThrough() {
        return this.isSeeThrough;
    }

    @Override
    public void setSeeThrough(boolean isSeeThrough) {
        boolean changed = !Objects.equals(this.isSeeThrough, isSeeThrough);
        this.isSeeThrough = isSeeThrough;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public boolean hasDefaultBackground() {
        return this.hasDefaultBackground;
    }

    @Override
    public void setHasDefaultBackground(boolean hasDefaultBackground) {
        boolean changed = !Objects.equals(this.hasDefaultBackground, hasDefaultBackground);
        this.hasDefaultBackground = hasDefaultBackground;

        if (changed) {
            this.getChangeListener().run();
        }
    }

    @Override
    public HologramType<?> getType() {
        return HologramTypes.TEXT.get();
    }

    @Override
    public void apply(HologramPage<?, ?> page) {
        ((HologramPage<String, com.artillexstudios.axapi.hologram.HologramType<String>>) page).setContent(this.content);
        super.apply(page);
        TextDisplayMeta meta = (TextDisplayMeta) page.getEntityMeta();

        if (this.lineWidth != null) {
            meta.lineWidth(this.lineWidth);
        }

        if (this.backgroundColor != null) {
            meta.backgroundColor(this.backgroundColor);
        }

        if (this.textOpacity != null) {
            meta.opacity(this.textOpacity);
        }

        if (this.alignment != null) {
            meta.alignment(this.alignment);
        }

        if (this.hasShadow != null) {
            meta.shadow(this.hasShadow);
        }

        if (this.isSeeThrough != null) {
            meta.seeThrough(this.isSeeThrough);
        }

        if (this.hasDefaultBackground != null) {
            meta.defaultBackground(this.hasDefaultBackground);
        }
    }

    @Override
    public void serialize(Map<String, Object> map) {
        super.serialize(map);

        if (this.content != null) {
            map.put("content", List.of(this.content.replace("<br>", "").split("\n")));
        }

        if (this.lineWidth != null) {
            map.put("line-width", this.lineWidth);
        }

        if (this.backgroundColor != null) {
            map.put("background-color", this.backgroundColor);
        }

        if (this.textOpacity != null) {
            map.put("text-opacity", this.textOpacity);
        }

        if (this.alignment != null) {
            map.put("alignment", this.alignment.name());
        }

        if (this.hasShadow != null) {
            map.put("shadow", this.hasShadow);
        }

        if (this.isSeeThrough != null) {
            map.put("see-through", this.isSeeThrough);
        }

        if (this.hasDefaultBackground != null) {
            map.put("default-background", this.hasDefaultBackground);
        }
    }

    @Override
    public void deserialize(MapConfigurationGetter data) {
        super.deserialize(data);

        List<String> contentList = data.getStringList("content");
        if (contentList != null) {
            this.setContent(String.join("\n<br>", contentList));
        }

        Integer lineWidth = data.getInteger("line-width");
        if (lineWidth != null) {
            this.setLineWidth(lineWidth);
        }

        Integer backgroundColor = data.getInteger("background-color");
        if (backgroundColor != null) {
            this.setBackgroundColor(backgroundColor);
        }

        Byte textOpacity = data.getByte("text-opacity");
        if (textOpacity != null) {
            this.setTextOpacity(textOpacity);
        }

        TextDisplayMeta.Alignment alignment = data.getEnum("alignment", TextDisplayMeta.Alignment.class);
        if (alignment != null) {
            this.setAlignment(alignment);
        }

        Boolean shadow = data.getBoolean("shadow");
        if (shadow != null) {
            this.setHasShadow(shadow);
        }

        Boolean seeThrough = data.getBoolean("see-through");
        if (seeThrough != null) {
            this.setSeeThrough(seeThrough);
        }

        Boolean hasDefaultBackground = data.getBoolean("default-background");
        if (hasDefaultBackground != null) {
            this.setHasDefaultBackground(hasDefaultBackground);
        }
    }
}
