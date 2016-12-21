/*
 * ***************************************************************************
 *
 *         Copyright © 2016 unaussprechlich - ALL RIGHTS RESERVED
 *
 * ***************************************************************************
 */

package net.unaussprechlich.managedgui.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * FontHelper Created by unaussprechlich on 20.12.2016.
 * Description:
 **/
public class FontHelper {


    private static FontHelper INSTANCE;

    public static FontHelper getINSTANCE() {
        if (INSTANCE == null)
            INSTANCE = new FontHelper();
        return INSTANCE;
    }

    private FontHelper() {
        //TODO: Autogenerated Singletone
    }


    public static FontRenderer getFrontRenderer(){
        return Minecraft.getMinecraft().fontRendererObj;
    }


    public static void draw(String text, int xStart, int yStart){
        getFrontRenderer().drawString(text, xStart, yStart, 0xffffff);
    }

    public static void drawWithShadow(String text, int xStart, int yStart){
        getFrontRenderer().drawStringWithShadow(text, xStart, yStart, 0xffffff);
    }

}