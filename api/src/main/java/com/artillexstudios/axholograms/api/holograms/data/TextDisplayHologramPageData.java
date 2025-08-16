package com.artillexstudios.axholograms.api.holograms.data;

import com.artillexstudios.axapi.packetentity.meta.entity.TextDisplayMeta;

public interface TextDisplayHologramPageData extends DisplayHologramPageData {

    String getContent();

    void setContent(String content);

    int getLineWidth();

    void setLineWidth(int lineWidth);

    int getBackgroundColor();

    void setBackgroundColor(int backgroundColor);

    byte getTextOpacity();

    void setTextOpacity(byte textOpacity);

    TextDisplayMeta.Alignment getAlignment();

    void setAlignment(TextDisplayMeta.Alignment alignment);

    boolean hasShadow();

    void setHasShadow(boolean hasShadow);

    boolean isSeeThrough();

    void setSeeThrough(boolean isSeeThrough);

    boolean hasDefaultBackground();

    void setHasDefaultBackground(boolean hasDefaultBackground);
}
