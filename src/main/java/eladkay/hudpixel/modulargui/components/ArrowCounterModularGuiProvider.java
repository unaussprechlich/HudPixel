package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;

public class ArrowCounterModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsHeadshotCounter", comment = "Cops and Crims Headshot Counter", def = true)
    public static boolean enabled = false;

    private int arrows = 0;
    private final String ARROW_DISPLAY = "Arrows: ";
    private String prefix = "";

    @Override
    public boolean showElement() {
        return doesMatchForGame() && enabled;
    }

    @Override
    public String content() {
        return prefix + ARROW_DISPLAY + arrows;
    }

    @Override
    public String getAfterstats() {
        return null;
    }

    @Override
    public boolean doesMatchForGame() {
        return !GameDetector.getGameHasntBegan();
    }

    @Override
    public void setupNewGame() {

    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onTickUpdate() {
        EntityPlayerSP player = FMLClientHandler.instance().getClient().thePlayer;

        if(player == null || player.inventory == null) {
            return;
        }

        int count = 0;

        for(int slot = 0; slot < player.inventory.getSizeInventory(); slot++){
            ItemStack is = player.inventory.getStackInSlot(slot);

            if(is != null && is.getItem().equals(Items.arrow)){
                count += is.stackSize;
            }
        }

        arrows = count;

        prefix = arrows > 0 ? McColorHelper.GREEN.toString() : McColorHelper.RED.toString();
    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {

    }
}
