package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import eladkay.hudpixel.util.GameType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;
import org.newdawn.slick.Game;

public class CCHeadshotModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsKillstreakCounter", comment = "Cops and Crims Killstreak Counter", def = true)
    public static boolean enabled = false;

    private int headshots;

    public static String HEADSHOTS_DISPLAY = McColorHelper.BLUE + "Headshots: ";

    @Override
    public boolean showElement() {
        return doesMatchForGame() && enabled && !GameDetector.getGameHasntBegan();
    }

    @Override
    public String content() {
        return HEADSHOTS_DISPLAY + headshots;
    }

    @Override
    public String getAfterstats() {
        return YELLOW + "You had a total of " + GREEN + headshots + YELLOW + " headshots.";
    }

    @Override
    public boolean doesMatchForGame() {
        return GameDetector.doesGameTypeMatchWithCurrent(GameType.COPS_AND_CRIMS);
    }

    @Override
    public void setupNewGame() {
        headshots = 0;
    }

    @Override
    public void onGameStart() {
        headshots = 0;
    }

    @Override
    public void onGameEnd() {
        headshots = 0;
    }

    @Override
    public void onTickUpdate() {

    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {
        if(textMessage.contains('\u9270' + " ") && textMessage.startsWith(FMLClientHandler.instance().getClient().getSession().getUsername())){
            headshots++;
        }
    }
}
