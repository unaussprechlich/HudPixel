package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import eladkay.hudpixel.util.GameType;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;

import java.util.regex.Pattern;

public class CCKillstreakCounterModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsKillstreakCounter", comment = "Cops and Crims Killstreak Counter", def = true)
    public static boolean enabled = false;

    public static final String KILLSTREAK_DISPLAY = GOLD + "Current Killstreak: ";

    private int killstreak;
    private int totalKillstreaks;
    private final Pattern DEATH_PATTERN = Pattern.compile("\\w* \\W+ " + FMLClientHandler.instance().getClient().getSession().getUsername());
    private final Pattern KILL_PATTERN = Pattern.compile(FMLClientHandler.instance().getClient().getSession().getUsername() + " \\W+ \\w*");

    @Override
    public boolean showElement() {
        return doesMatchForGame() && enabled && !GameDetector.getGameHasntBegan();
    }

    @Override
    public String content() {
        return KILLSTREAK_DISPLAY + killstreak;
    }

    @Override
    public String getAfterstats() {
        return YELLOW + "You had a total of " + GREEN + totalKillstreaks + YELLOW + " good killstreaks.";
    }

    @Override
    public boolean doesMatchForGame() {
        return GameDetector.doesGameTypeMatchWithCurrent(GameType.COPS_AND_CRIMS);
    }

    @Override
    public void setupNewGame() {
        killstreak = 0;
        totalKillstreaks = 0;
    }

    @Override
    public void onGameStart() {
        killstreak = 0;
        totalKillstreaks = 0;
    }

    @Override
    public void onGameEnd() {
        killstreak = 0;
        totalKillstreaks = 0;
    }

    @Override
    public void onTickUpdate() {

    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {
        if(DEATH_PATTERN.matcher(textMessage).find()){
            onDeath();
        } else if (KILL_PATTERN.matcher(textMessage).find()){
            onKill();
        }
    }

    private void onDeath(){
        if(killstreak >= 3){
            totalKillstreaks++;
        }

        killstreak = 0;
    }

    private void onKill(){
        killstreak++;
    }
}
