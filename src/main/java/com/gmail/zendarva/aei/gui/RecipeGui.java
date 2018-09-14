package com.gmail.zendarva.aei.gui;

import com.gmail.zendarva.aei.api.IDisplayCategory;
import com.gmail.zendarva.aei.api.IRecipe;
import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Button;
import com.gmail.zendarva.aei.gui.widget.Control;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecipeGui extends GuiContainer {
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("almostenoughitems","textures/gui/recipecontainer.png");
    private final MainWindow mainWindow;
    private final Container container;
    private final GuiScreen prevScreen;
    private final Map<IDisplayCategory, List<IRecipe>> recipes;
    private int guiWidth = 176;
    private int guiHeight=222;
    ArrayList<IDisplayCategory> categories = new ArrayList<>();
    private int categoryPointer = 0;
    private int recipePointer = 0;
    private List<AEISlot> slots;
    private int cycleCounter =0;
    private int[] itemPointer;
    List<Control> controls = new LinkedList<>();

    public RecipeGui(Container p_i1072_1_, GuiScreen prevScreen, Map<IDisplayCategory, List<IRecipe>> recipes) {
        super(new RecipeContainer());
        this.container = p_i1072_1_;
        this.prevScreen = prevScreen;
        this.recipes = recipes;
        this.mc = Minecraft.getMinecraft();
        this.itemRender=mc.getRenderItem();
        this.fontRenderer= mc.fontRenderer;
        this.mainWindow=Minecraft.getMinecraft().mainWindow;

        setupCategories();
    }

    private void setupCategories() {
        categories.addAll(recipes.keySet());
        updateRecipe();
    }


    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
        int y = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));
        drawCenteredString(this.fontRenderer,categories.get(categoryPointer).getDisplayName(),guiLeft + guiWidth/2,y+9,0x999999);
        controls.forEach(Control::draw);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        slots.forEach(AEISlot::tick);
        controls.forEach(Control::tick);
    }



    @Override
    public void onResize(Minecraft p_onResize_1_, int p_onResize_2_, int p_onResize_3_) {
        super.onResize(p_onResize_1_, p_onResize_2_, p_onResize_3_);
        updateRecipe();
    }

    private void updateRecipe(){
        IRecipe recipe = recipes.get(categories.get(categoryPointer)).get(recipePointer);
        categories.get(categoryPointer).setRecipe(recipe);
        slots = categories.get(categoryPointer).setupDisplay();

        guiLeft  = (int) ((mainWindow.getScaledWidth()/2 -this.guiWidth/2));
        guiTop  = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));

        for (AEISlot slot : slots) {
            slot.move(guiLeft,guiTop);
        }

        Button btnCatagoryLeft = new Button(guiLeft+10,guiTop+2,15,20,"<");
        Button btnCatagoryRight = new Button(guiLeft +guiWidth-25,guiTop+2,15,20,">");
        btnCatagoryRight.onClick= this::btnCategoryRight;
        btnCatagoryLeft.onClick= this::btnCategoryLeft;

        Button btnRecipeLeft = new Button(guiLeft+10,guiTop+guiHeight-30,15,20,"<");
        Button btnRecipeRight = new Button(guiLeft +guiWidth-25,guiTop+guiHeight-30,15,20,">");
        btnRecipeRight.onClick= this::btnRecipeRight;
        btnRecipeLeft.onClick= this::btnRecipeLeft;

        controls.clear();
        controls.add(btnCatagoryLeft);
        controls.add(btnCatagoryRight);
        if (categories.size() == 1){
            btnCatagoryLeft.setEnabled(false);
            btnCatagoryRight.setEnabled(false);
        }

        controls.add(btnRecipeLeft);
        controls.add(btnRecipeRight);

        itemPointer = new int[9];
        for (int i = 0; i < itemPointer.length; i++) {
            itemPointer[i] = 0;
        }

        List<Control> newControls = new LinkedList<>();
        categories.get(categoryPointer).addWidget(newControls);
        newControls.forEach(f->f.move(guiLeft,guiTop));
        controls.addAll(newControls);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float v, int i, int i1) {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);

        int lvt_4_1_ = (int) ((mainWindow.getScaledWidth()/2 -this.guiWidth/2));
        int lvt_5_1_ = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));

        this.drawTexturedModalRect(lvt_4_1_, lvt_5_1_, 0, 0, this.guiWidth, this.guiHeight);
        slots.forEach(AEISlot::draw);
    }


    @Override
    protected void initGui() {
        super.initGui();
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {



        if (p_keyPressed_1_ == 259 && prevScreen !=null && AEIRenderHelper.focusedControl ==null){
            Minecraft.getMinecraft().displayGuiScreen(prevScreen);
            return true;
        }



        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    private boolean btnCategoryLeft(int button){
        recipePointer = 0;
        categoryPointer--;
        if (categoryPointer < 0){
            categoryPointer= categories.size()-1;

        }
        updateRecipe();
        return true;
    }

    private boolean btnCategoryRight(int button){
        recipePointer = 0;
        categoryPointer++;
        if (categoryPointer >= categories.size()){
            categoryPointer= 0;
        }
        updateRecipe();
        return true;
    }

    private boolean btnRecipeLeft(int button){
        recipePointer--;
        if (recipePointer < 0){
            recipePointer= recipes.get(categories.get(categoryPointer)).size()-1;
        }
        updateRecipe();
        return true;
    }

    private boolean btnRecipeRight(int button){
        recipePointer++;
        if (recipePointer >= recipes.get(categories.get(categoryPointer)).size()){
            recipePointer= 0;
        }
        updateRecipe();
        return true;
    }
}
