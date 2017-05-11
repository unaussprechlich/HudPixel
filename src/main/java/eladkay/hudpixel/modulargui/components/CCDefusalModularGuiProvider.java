package eladkay.hudpixel.modulargui.components;

import eladkay.hudpixel.GameDetector;
import eladkay.hudpixel.config.CCategory;
import eladkay.hudpixel.config.ConfigPropertyBoolean;
import eladkay.hudpixel.modulargui.SimpleHudPixelModularGuiProvider;
import eladkay.hudpixel.util.GameType;
import eladkay.hudpixel.util.ScoreboardReader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.unaussprechlich.hudpixelextended.util.McColorHelper;

import java.util.regex.Pattern;

public class CCDefusalModularGuiProvider extends SimpleHudPixelModularGuiProvider implements McColorHelper {

    @ConfigPropertyBoolean(category = CCategory.HUD, id = "copsAndCrimsDefusalAddons", comment = "Cops and Crims Defusal Addons", def = true)
    public static boolean enabled = false;

    public static final String BOMB_STATUS_DISPLAY = McColorHelper.D_GREEN + "Bomb Status: ";
    public static final String TEAM_TEMP_DISPLAY = McColorHelper.YELLOW + "Team: ";

    private boolean isDefusal = false;
    private final String[] BOMB_STATUS_STRINGS = {"Planted", "Carried by ", "On the ground", "Not planted"};
    private int bombStatus = -1;
    private String optSuffix;
    private String team;
    private int tickCount = 0;
    private final Pattern IS_CARRIER = Pattern.compile("[^\\w\\d\\s] [.]* [^\\w\\d\\s] \\[\\d-\\d\\]");

    @Override
    public boolean showElement() {
        return doesMatchForGame() && enabled && bombStatus > -1;
    }

    @Override
    public String content() {
        return BOMB_STATUS_DISPLAY + BOMB_STATUS_STRINGS[bombStatus] + optSuffix + "\n" + TEAM_TEMP_DISPLAY + team;
    }

    @Override
    public String getAfterstats() {
        return null;
    }

    @Override
    public boolean doesMatchForGame() {
        return GameDetector.doesGameTypeMatchWithCurrent(GameType.COPS_AND_CRIMS) && isDefusal && !GameDetector.getGameHasntBegan();
    }

    @Override
    public void setupNewGame() {
        bombStatus = -1;
        optSuffix = "";
        tickCount = 0;
    }

    @Override
    public void onGameStart() {
        bombStatus = -1;
        optSuffix = "";
        tickCount = 0;
    }

    @Override
    public void onGameEnd() {
        bombStatus = -1;
        optSuffix = "";
        tickCount = 0;
    }

    @Override
    public void onTickUpdate() {
        tickCount++;
        if(tickCount == 10){
            makeUpdates();
            tickCount = 0;
        }
    }

    @Override
    public void onChatMessage(String textMessage, String formattedMessage) {

    }

    private boolean isPlanted(){
        for(String score : ScoreboardReader.getScoreboardNames()) {
            if (score.contains("Defend the bomb") || score.contains("Defuse the bomb")) {
                return true;
            }
        }

        return false;
    }

    private EntityPlayer getCarrier(){
        for(EntityPlayer p : FMLClientHandler.instance().getClient().theWorld.playerEntities) {

            if (!IS_CARRIER.matcher(p.getDisplayName().getUnformattedText()).find()) {
                continue;
            }

            return p;
        }

        return null;
    }

    private void makeUpdates(){
        isDefusal = ScoreboardReader.getScoreboardTitle().contains("0");

        if (!doesMatchForGame()) {
            return;
        }

        bombStatus = -1;

        EntityPlayer p = getCarrier();

        if (p != null) {
            boolean isSelf = FMLClientHandler.instance().getClientPlayerEntity().getName().equalsIgnoreCase(p.getName());

            bombStatus = 1;
            optSuffix = isSelf ? "me" : getCarrier().getName();

            return;
        }


        if (bombStatus < 0) {
            for (String score : ScoreboardReader.getScoreboardNames()) {

                if (score.contains("Defend the bomb") || score.contains("Defuse the bomb")) {
                    bombStatus = 0;
                }

                if (score.contains("Team:")) team = score.split(" ")[4];

                if (score.contains("Objective")) optSuffix = " - " + score.split(" ")[1];

                if (score.contains("Defend bomb sites")) {
                    bombStatus = 3;
                    team = "Cops";
                }
                //Defend the bomb!
                //Defend bomb sites!
                //Defuse the bomb!
            }

            if (bombStatus < 0) {
                bombStatus = team != null && team.contains("Cops") ? 3 : 2;

                optSuffix = "";
            }
        }
    }
}
