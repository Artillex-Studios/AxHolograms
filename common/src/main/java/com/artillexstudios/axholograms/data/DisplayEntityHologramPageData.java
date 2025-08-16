package com.artillexstudios.axholograms.data;

import com.artillexstudios.axapi.config.adapters.MapConfigurationGetter;
import com.artillexstudios.axapi.hologram.page.HologramPage;
import com.artillexstudios.axapi.packetentity.meta.entity.DisplayMeta;
import com.artillexstudios.axapi.utils.Quaternion;
import com.artillexstudios.axapi.utils.Vector3f;
import com.artillexstudios.axapi.utils.logging.LogUtils;
import com.artillexstudios.axholograms.api.holograms.data.DisplayHologramPageData;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class DisplayEntityHologramPageData implements DisplayHologramPageData {
    private Runnable changeListener;
    private Vector3f translation;
    private Vector3f scale;
    private Quaternion rotationLeft;
    private Quaternion rotationRight;
    private DisplayMeta.BillboardConstrain billboardConstrain;
    private Integer brightnessOverride;
    private Float viewRange;
    private Float shadowRadius;
    private Float shadowStrength;
    private Float width;
    private Float height;
    private Integer glowColorOverride;

    @Override
    public Vector3f getTranslation() {
        return this.translation;
    }

    @Override
    public void setTranslation(Vector3f translation) {
        if (!Objects.equals(this.translation, translation)) {
            this.getChangeListener().run();
        }

        this.translation = translation;
    }

    @Override
    public Vector3f getScale() {
        return this.scale;
    }

    @Override
    public void setScale(Vector3f scale) {
        if (!Objects.equals(this.scale, scale)) {
            this.changeListener.run();
        }

        this.scale = scale;
    }

    @Override
    public Quaternion getRotationLeft() {
        return this.rotationLeft;
    }

    @Override
    public void setRotationLeft(Quaternion rotationLeft) {
        if (!Objects.equals(this.rotationLeft, rotationLeft)) {
            this.getChangeListener().run();
        }

        this.rotationLeft = rotationLeft;
    }

    @Override
    public Quaternion getRotationRight() {
        return this.rotationRight;
    }

    @Override
    public void setRotationRight(Quaternion rotationRight) {
        if (!Objects.equals(this.rotationRight, rotationRight)) {
            this.getChangeListener().run();
        }

        this.rotationRight = rotationRight;
    }

    @Override
    public DisplayMeta.BillboardConstrain getBillboardConstrain() {
        return this.billboardConstrain;
    }

    @Override
    public void setBillboardConstrain(DisplayMeta.BillboardConstrain billboardConstrain) {
        if (!Objects.equals(this.billboardConstrain, billboardConstrain)) {
            this.getChangeListener().run();
        }

        this.billboardConstrain = billboardConstrain;
    }

    @Override
    public int getBrightnessOverride() {
        return this.brightnessOverride;
    }

    @Override
    public void setBrightnessOverride(int brightnessOverride) {
        if (!Objects.equals(this.brightnessOverride, brightnessOverride)) {
            this.getChangeListener().run();
        }

        this.brightnessOverride = brightnessOverride;
    }

    @Override
    public float getViewRange() {
        return this.viewRange;
    }

    @Override
    public void setViewRange(float viewRange) {
        if (!Objects.equals(this.viewRange, viewRange)) {
            this.getChangeListener().run();
        }

        this.viewRange = viewRange;
    }

    @Override
    public float getShadowRadius() {
        return this.shadowRadius;
    }

    @Override
    public void setShadowRadius(float shadowRadius) {
        if (!Objects.equals(this.shadowRadius, shadowRadius)) {
            this.getChangeListener().run();
        }

        this.shadowRadius = shadowRadius;
    }

    @Override
    public float getShadowStrength() {
        return this.shadowStrength;
    }

    @Override
    public void setShadowStrength(float shadowStrength) {
        if (!Objects.equals(this.shadowStrength, shadowStrength)) {
            this.getChangeListener().run();
        }

        this.shadowStrength = shadowStrength;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public void setWidth(float width) {
        if (!Objects.equals(this.width, width)) {
            this.getChangeListener().run();
        }

        this.width = width;
    }

    @Override
    public float getHeight() {
        return this.height;
    }

    @Override
    public void setHeight(float height) {
        if (!Objects.equals(this.height, height)) {
            this.getChangeListener().run();
        }

        this.height = height;
    }

    @Override
    public int getGlowColorOverride() {
        return this.glowColorOverride;
    }

    @Override
    public void setGlowColorOverride(int glowColorOverride) {
        if (!Objects.equals(this.glowColorOverride, glowColorOverride)) {
            this.getChangeListener().run();
        }

        this.glowColorOverride = glowColorOverride;
    }

    @Override
    public void setChangeListener(Runnable changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void apply(HologramPage<?, ?> page) {
        DisplayMeta meta = (DisplayMeta) page.getEntityMeta();
        if (this.translation != null) {
            meta.translation(this.translation);
        }

        if (this.scale != null) {
            meta.scale(this.scale);
        }

        if (this.rotationLeft != null) {
            meta.rotationLeft(this.rotationLeft);
        }

        if (this.rotationRight != null) {
            meta.rotationRight(this.rotationRight);
        }

        if (this.billboardConstrain != null) {
            meta.billboardConstrain(this.billboardConstrain);
        }

        if (this.brightnessOverride != null) {
            meta.brightnessOverride(this.brightnessOverride);
        }

        if (this.viewRange != null) {
            meta.viewRange(this.viewRange);
        }

        if (this.shadowRadius != null) {
            meta.shadowRadius(this.shadowRadius);
        }

        if (this.shadowStrength != null) {
            meta.shadowStrength(this.shadowStrength);
        }

        if (this.width != null) {
            meta.width(this.width);
        }

        if (this.height != null) {
            meta.height(this.height);
        }

        if (this.glowColorOverride != null) {
            meta.glowColorOverride(this.glowColorOverride);
        }
    }

    @Override
    public void serialize(Map<String, Object> map) {
        if (this.translation != null) {
            Map<String, Float> translation = new HashMap<>();
            translation.put("x", this.translation.x());
            translation.put("y", this.translation.y());
            translation.put("z", this.translation.z());
            map.put("translation", translation);
        }

        if (this.scale != null) {
            Map<String, Float> scale = new HashMap<>();
            scale.put("x", this.scale.x());
            scale.put("y", this.scale.y());
            scale.put("z", this.scale.z());
            map.put("scale", scale);
        }

        if (this.rotationLeft != null) {
            Map<String, Float> rotationLeft = new HashMap<>();
            rotationLeft.put("x", this.rotationLeft.x());
            rotationLeft.put("y", this.rotationLeft.y());
            rotationLeft.put("z", this.rotationLeft.z());
            rotationLeft.put("w", this.rotationLeft.w());
            map.put("rotation-left", rotationLeft);
        }

        if (this.rotationRight != null) {
            Map<String, Float> rotationRight = new HashMap<>();
            rotationRight.put("x", this.rotationRight.x());
            rotationRight.put("y", this.rotationRight.y());
            rotationRight.put("z", this.rotationRight.z());
            rotationRight.put("w", this.rotationRight.w());
            map.put("rotation-right", rotationRight);
        }

        if (this.billboardConstrain != null) {
            map.put("billboard-constrain", this.billboardConstrain.name());
        }

        if (this.brightnessOverride != null) {
            map.put("brightness-override", this.brightnessOverride);
        }

        if (this.viewRange != null) {
            map.put("view-range", this.viewRange);
        }

        if (this.shadowRadius != null) {
            map.put("shadow-radius", this.shadowRadius);
        }

        if (this.shadowStrength != null) {
            map.put("shadow-strength", this.shadowStrength);
        }

        if (this.width != null) {
            map.put("width", this.width);
        }

        if (this.height != null) {
            map.put("height", this.height);
        }

        if (this.glowColorOverride != null) {
            map.put("glow-color-override", this.glowColorOverride);
        }
    }

    @Override
    public void deserialize(MapConfigurationGetter data) {
        MapConfigurationGetter translation = data.getConfigurationSection("translation");
        if (translation != null) {
            Float x = translation.getFloat("x");
            Float y = translation.getFloat("y");
            Float z = translation.getFloat("z");
            if (x != null && y != null && z != null) {
                this.setTranslation(new Vector3f(x, y, z));
            } else {
                LogUtils.warn("Failed to load translation for hologram!");
            }
        }

        MapConfigurationGetter scale = data.getConfigurationSection("scale");
        if (scale != null) {
            Float x = scale.getFloat("x");
            Float y = scale.getFloat("y");
            Float z = scale.getFloat("z");
            if (x != null && y != null && z != null) {
                this.setScale(new Vector3f(x, y, z));
            } else {
                LogUtils.warn("Failed to load scale for hologram!");
            }
        }

        MapConfigurationGetter rotationLeft = data.getConfigurationSection("rotation-left");
        if (rotationLeft != null) {
            Float x = rotationLeft.getFloat("x");
            Float y = rotationLeft.getFloat("y");
            Float z = rotationLeft.getFloat("z");
            Float w = rotationLeft.getFloat("w");
            if (x != null && y != null && z != null && w != null) {
                this.setRotationLeft(new Quaternion(x, y, z, w));
            } else {
                LogUtils.warn("Failed to load rotation-left for hologram!");
            }
        }

        MapConfigurationGetter rotationRight = data.getConfigurationSection("rotation-right");
        if (rotationRight != null) {
            Float x = rotationRight.getFloat("x");
            Float y = rotationRight.getFloat("y");
            Float z = rotationRight.getFloat("z");
            Float w = rotationRight.getFloat("w");
            if (x != null && y != null && z != null && w != null) {
                this.setRotationRight(new Quaternion(x, y, z, w));
            } else {
                LogUtils.warn("Failed to load rotation-right for hologram!");
            }
        }

        DisplayMeta.BillboardConstrain billboardConstrain = data.getEnum("billboard-constrain", DisplayMeta.BillboardConstrain.class);
        if (billboardConstrain != null) {
            this.setBillboardConstrain(billboardConstrain);
        }

        Integer brightnessOverride = data.getInteger("brightness-override");
        if (brightnessOverride != null) {
            this.setBrightnessOverride(brightnessOverride);
        }

        Float viewRange = data.getFloat("view-range");
        if (viewRange != null) {
            this.setViewRange(viewRange);
        }

        Float shadowRadius = data.getFloat("shadow-radius");
        if (shadowRadius != null) {
            this.setShadowRadius(shadowRadius);
        }

        Float shadowStrength = data.getFloat("shadow-strength");
        if (shadowStrength != null) {
            this.setShadowStrength(shadowStrength);
        }

        Float width = data.getFloat("width");
        if (width != null) {
            this.setWidth(width);
        }

        Float height = data.getFloat("height");
        if (height != null) {
            this.setHeight(height);
        }

        Integer glowColorOverride = data.getInteger("glow-color-override");
        if (glowColorOverride != null) {
            this.setGlowColorOverride(glowColorOverride);
        }
    }

    protected Runnable getChangeListener() {
        return this.changeListener;
    }
}
