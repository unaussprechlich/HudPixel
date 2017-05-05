package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import eladkay.hudpixel.util.GameType;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;

public class CCAssistsModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsKillCounter", comment = "Cops and Crims Kill Counter", def = true)
    public static boolean enabled = false;

    private static final String ASSISTS_DISPLAY = YELLOW + "Assists: ";
    private int assists;

    @Override
    public boolean showElement() {
        return doesMatchForGame() && enabled;
    }

    @Override
    public String content() {
        return ASSISTS_DISPLAY + assists;
    }

    @Override
    public String getAfterstats() {
        return YELLOW + "You got a total of " + assists + " assists.";
    }

    @Override
    public boolean doesMatchForGame() {
        return GameDetector.doesGameTypeMatchWithCurrent(GameType.COPS_AND_CRIMS);
    }

    @Override
    public void setupNewGame() {
        assists = 0;
    }

    @Override
    public void onGameStart() {
        assists = 0;
    }

    @Override
    public void onGameEnd() {
        assists = 0;
    }

    @Override
    public void onTickUpdate() {

    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {
        if(textMessage.startsWith("+") && textMessage.contains("(Assist)")){
            assists++;
        }
    }
}
