package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import eladkay.hudpixel.util.GameType;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;

public class CCKillsModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsKillCounter", comment = "Cops and Crims Kill Counter", def = true)
    public static boolean enabled = false;

    private static final String KILLS_DISPLAY = RED + "Kills: ";
    private int kills;

    @Override
    public boolean showElement() {
        return doesMatchForGame() && enabled;
    }

    @Override
    public String content() {
        return KILLS_DISPLAY + kills;
    }

    @Override
    public String getAfterstats() {
        return YELLOW + "You got a total of " + GREEN + kills + YELLOW + " Kills.";
    }

    @Override
    public boolean doesMatchForGame() {
        return GameDetector.doesGameTypeMatchWithCurrent(GameType.COPS_AND_CRIMS);
    }

    @Override
    public void setupNewGame() {
        kills = 0;
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameEnd() {
        kills = 0;
    }

    @Override
    public void onTickUpdate() {

    }

    @Override
    public boolean ignoreEmptyCheck() {
        return false;
    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {
        if(textMessage.contains("(Kill)") && textMessage.startsWith("+")){
            kills++;
        }
    }
}
