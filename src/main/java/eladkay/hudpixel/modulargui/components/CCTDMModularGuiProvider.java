package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import eladkay.hudpixel.util.GameType;
import eladkay.hudpixel.util.ScoreboardReader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;

public class CCTDMModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsAssistCounter", comment = "Cops and Crims Assist Counter", def = true)
    public static boolean enabled = false;

    private int challengeKills;
    private boolean isTDM = false;
    private static String CHALLENGE_DISPLAY = GREEN + "Challenge Kills: ";

    @Override
    public boolean showElement() {
        return enabled && !GameDetector.getGameHasntBegan() && doesMatchForGame();
    }

    @Override
    public String content() {
        return CHALLENGE_DISPLAY + challengeKills;
    }

    @Override
    public String getAfterstats() {
        return YELLOW + "You had a total of " + GREEN + challengeKills + YELLOW + " challenge kills.";
    }

    @Override
    public boolean doesMatchForGame() {
        return GameDetector.doesGameTypeMatchWithCurrent(GameType.COPS_AND_CRIMS) && isTDM;
    }

    @Override
    public void setupNewGame() {
        challengeKills = 0;
    }

    @Override
    public void onGameStart() {
        challengeKills = 0;
    }

    @Override
    public void onGameEnd() {
        challengeKills = 0;
    }

    @Override
    public void onTickUpdate() {
        if(ScoreboardReader.getScoreboardTitle().contains("DEATHMATCH")){
            isTDM = true;
        } else {
            isTDM = false;
        }
    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {
        if(textMessage.contains("(Challenge)") || textMessage.contains(FMLClientHandler.instance().getClient().thePlayer.getName() + " has completed a challenge")){
            challengeKills++;
        }
    }
}