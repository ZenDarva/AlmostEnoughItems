package com.gmail.zendarva.aie.gui;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class RecipeGui extends GuiContainer {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("almostenoughitems","textures/gui/recipecontainer.png");
    private final MainWindow mainWindow;
    private final GuiScreen prevScreen;
    private int guiWidth = 176;
    private int guiHeight=222;

    public RecipeGui(Container p_i1072_1_, GuiScreen prevScreen) {
        super(new RecipeContainer());
        this.prevScreen = prevScreen;
        this.mc = Minecraft.getMinecraft();
        this.itemRender=mc.getRenderItem();
        this.fontRenderer= mc.fontRenderer;
        this.mainWindow=Minecraft.getMinecraft().mainWindow;

    }



    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);

        int lvt_4_1_ = (int) ((mainWindow.getScaledWidth()/2 -this.guiWidth/2));
        int lvt_5_1_ = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));

        this.drawTexturedModalRect(lvt_4_1_, lvt_5_1_, 0, 0, this.guiWidth, this.guiHeight);
    }

    @Override
    protected void initGui() {
        super.initGui();
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (p_keyPressed_1_ == 259 && prevScreen !=null){
            Minecraft.getMinecraft().displayGuiScreen(prevScreen);
            return true;
        }
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }


}
