package com.gmail.zendarva.aie.gui.widget;

import com.gmail.zendarva.aie.api.TriBooleanProducer;
import com.gmail.zendarva.aie.gui.Drawable;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.apache.logging.log4j.util.TriConsumer;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/**
 * Created by James on 7/29/2018.
 */
public abstract class Control extends Drawable {
    private boolean enabled = true;
    public IntFunction<Boolean> onClick;
    public TriBooleanProducer onKeyDown;
    public BiConsumer<Character, Integer> charPressed;
    public Control(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Control(Rectangle rect) {
        super(rect);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    protected static void drawRect(int p_drawRect_0_, int p_drawRect_1_, int p_drawRect_2_, int p_drawRect_3_, int p_drawRect_4_) {
        int lvt_5_3_;
        if (p_drawRect_0_ < p_drawRect_2_) {
            lvt_5_3_ = p_drawRect_0_;
            p_drawRect_0_ = p_drawRect_2_;
            p_drawRect_2_ = lvt_5_3_;
        }

        if (p_drawRect_1_ < p_drawRect_3_) {
            lvt_5_3_ = p_drawRect_1_;
            p_drawRect_1_ = p_drawRect_3_;
            p_drawRect_3_ = lvt_5_3_;
        }

        float lvt_5_3_1 = (float) (p_drawRect_4_ >> 24 & 255) / 255.0F;
        float lvt_6_1_ = (float) (p_drawRect_4_ >> 16 & 255) / 255.0F;
        float lvt_7_1_ = (float) (p_drawRect_4_ >> 8 & 255) / 255.0F;
        float lvt_8_1_ = (float) (p_drawRect_4_ & 255) / 255.0F;
        Tessellator lvt_9_1_ = Tessellator.getInstance();
        BufferBuilder lvt_10_1_ = lvt_9_1_.getBuffer();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(lvt_6_1_, lvt_7_1_, lvt_8_1_, lvt_5_3_1);
        lvt_10_1_.begin(7, DefaultVertexFormats.POSITION);
        lvt_10_1_.pos((double) p_drawRect_0_, (double) p_drawRect_3_, 0.0D).endVertex();
        lvt_10_1_.pos((double) p_drawRect_2_, (double) p_drawRect_3_, 0.0D).endVertex();
        lvt_10_1_.pos((double) p_drawRect_2_, (double) p_drawRect_1_, 0.0D).endVertex();
        lvt_10_1_.pos((double) p_drawRect_0_, (double) p_drawRect_1_, 0.0D).endVertex();
        lvt_9_1_.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.disableAlpha();
    }
}
