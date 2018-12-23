package com.gmail.zendarva.aei.gui;

import com.gmail.zendarva.aei.api.IDisplayCategory;
import com.gmail.zendarva.aei.api.IRecipe;
import com.gmail.zendarva.aei.gui.widget.AEISlot;
import com.gmail.zendarva.aei.gui.widget.Button;
import com.gmail.zendarva.aei.gui.widget.Control;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ContainerGui;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.util.Window;
import net.minecraft.container.Container;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RecipeGui extends ContainerGui {
    private static final Identifier CHEST_GUI_TEXTURE = new Identifier("almostenoughitems","textures/gui/recipecontainer.png");
    private final Window mainWindow;
    private final Container container;
    private final Gui prevScreen;
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

    public RecipeGui(Container p_i1072_1_, Gui prevScreen, Map<IDisplayCategory, List<IRecipe>> recipes) {
        super(new RecipeContainer());
        this.container = p_i1072_1_;
        this.prevScreen = prevScreen;
        this.recipes = recipes;
        this.client = MinecraftClient.getInstance();
        this.itemRenderer=client.getItemRenderer();
        this.fontRenderer= client.fontRenderer;
        this.mainWindow=MinecraftClient.getInstance().window;

        setupCategories();
    }

    private void setupCategories() {
        categories.addAll(recipes.keySet());
        updateRecipe();
    }


    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        int y = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));
        drawStringCentered(this.fontRenderer,categories.get(categoryPointer).getDisplayName(),left + guiWidth/2,y+9,0x999999);
        controls.forEach(Control::draw);
    }

    @Override
    public void update() {
        super.update();
        slots.forEach(AEISlot::tick);
        controls.forEach(Control::tick);
    }



    @Override
    public void onScaleChanged(MinecraftClient p_onResize_1_, int p_onResize_2_, int p_onResize_3_) {
        super.onScaleChanged(p_onResize_1_, p_onResize_2_, p_onResize_3_);
        updateRecipe();
    }

    private void updateRecipe(){
        IRecipe recipe = recipes.get(categories.get(categoryPointer)).get(recipePointer);
        categories.get(categoryPointer).setRecipe(recipe);
        slots = categories.get(categoryPointer).setupDisplay();

        left  = (int) ((mainWindow.getScaledWidth()/2 -this.guiWidth/2));
        top  = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));

        for (AEISlot slot : slots) {
            slot.move(left,top);
        }

        Button btnCatagoryLeft = new Button(left+10,top+2,15,20,"<");
        Button btnCatagoryRight = new Button(left +guiWidth-25,top+2,15,20,">");
        btnCatagoryRight.onClick= this::btnCategoryRight;
        btnCatagoryLeft.onClick= this::btnCategoryLeft;

        Button btnRecipeLeft = new Button(left+10,top+guiHeight-30,15,20,"<");
        Button btnRecipeRight = new Button(left +guiWidth-25,top+guiHeight-30,15,20,">");
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
        newControls.forEach(f->f.move(left,top));
        controls.addAll(newControls);
    }

    @Override
    protected void drawBackground(float v, int i, int i1) {
        drawBackground();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);

        int lvt_4_1_ = (int) ((mainWindow.getScaledWidth()/2 -this.guiWidth/2));
        int lvt_5_1_ = (int) ((mainWindow.getScaledHeight()/2 - this.guiHeight/2));

        this.drawTexturedRect(lvt_4_1_, lvt_5_1_, 0, 0, this.guiWidth, this.guiHeight);
        slots.forEach(AEISlot::draw);
    }


    @Override
    protected void onInitialized() {
        super.onInitialized();
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {



        if (p_keyPressed_1_ == 259 && prevScreen !=null && AEIRenderHelper.focusedControl ==null){
            MinecraftClient.getInstance().openGui(prevScreen);
            return true;
        }



        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);

    }

    @Override
    public void onClosed() {
        super.onClosed();
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
