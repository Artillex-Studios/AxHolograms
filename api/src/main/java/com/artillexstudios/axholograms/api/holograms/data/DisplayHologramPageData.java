package com.artillexstudios.axholograms.api.holograms.data;

import com.artillexstudios.axapi.packetentity.meta.entity.DisplayMeta;
import com.artillexstudios.axapi.utils.Quaternion;
import com.artillexstudios.axapi.utils.Vector3f;

public interface DisplayHologramPageData extends HologramPageData {

    Vector3f getTranslation();

    void setTranslation(Vector3f translation);

    Vector3f getScale();

    void setScale(Vector3f scale);

    Quaternion getRotationLeft();

    void setRotationLeft(Quaternion rotationLeft);

    Quaternion getRotationRight();

    void setRotationRight(Quaternion rotationRight);

    DisplayMeta.BillboardConstrain getBillboardConstrain();

    void setBillboardConstrain(DisplayMeta.BillboardConstrain billboardConstrain);

    int getBrightnessOverride();

    void setBrightnessOverride(int brightnessOverride);

    float getViewRange();

    void setViewRange(float viewRange);

    float getShadowRadius();

    void setShadowRadius(float shadowRadius);

    float getShadowStrength();

    void setShadowStrength(float shadowStrength);

    float getWidth();

    void setWidth(float width);

    float getHeight();

    void setHeight(float height);

    int getGlowColorOverride();

    void setGlowColorOverride(int glowColorOverride);
}
