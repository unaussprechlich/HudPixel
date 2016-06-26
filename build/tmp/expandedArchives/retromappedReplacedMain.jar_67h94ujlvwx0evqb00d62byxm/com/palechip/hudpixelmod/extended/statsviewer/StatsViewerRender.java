package com.palechip.hudpixelmod.extended.statsviewer;

import com.palechip.hudpixelmod.util.GameType;
import com.palechip.hudpixelmod.extended.statsviewer.msc.IGameStatsViewer;
import com.palechip.hudpixelmod.extended.statsviewer.msc.StatsCache;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import org.lwjgl.opengl.GL11;

/******************************************************************************
 * HudPixelExtended by unaussprechlich(github.com/unaussprechlich/HudPixelExtended),
 * an unofficial Minecraft Mod for the Hypixel Network.
 * <p>
 * Original version by palechip (github.com/palechip/HudPixel)
 * "Reloaded" version by PixelModders -> Eladkay (github.com/PixelModders/HudPixel)
 * <p>
 * Copyright (c) 2016 unaussprechlich and contributors
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *******************************************************************************/
public class StatsViewerRender {

    private static final int DURATION = 10000;

    private IGameStatsViewer iGameStatsViewer;
    private long expireTimestamp;

    StatsViewerRender(GameType gameType, String playerName){
        this.iGameStatsViewer = StatsCache.getPlayerByName(playerName, gameType);
        this.expireTimestamp = System.currentTimeMillis() + DURATION;
    }

    long getExpireTimestamp() {
        return expireTimestamp;
    }

    /**
     * Renders the stats above the player
     * @param event RenderPlayerEvent
     */
    void onRenderPlayer(RenderPlayerEvent.Pre event) {

        double offset = 0.3;
        int i = 1;

        if(this.iGameStatsViewer.getRenderList() != null){
            for(String s : this.iGameStatsViewer.getRenderList()){
                renderName(event.renderer, s, event.entityPlayer, event.x, event.y+(offset * i), event.z);
                i++;
            }
        } else {
            renderName(event.renderer, "Loading stats ....", event.entityPlayer, event.x, event.y + offset, event.z);
        }
    }

    /**
     * renders a string above a Player copied from the original mc namerenderer
     * @param renderer the renderer
     * @param str the string to render
     * @param entityIn the entity to render above
     * @param x x-cord
     * @param y y-cord
     * @param z z-cord
     */

    private static void renderName(RendererLivingEntity renderer, String str, EntityPlayer entityIn, double x, double y, double z) {
        FontRenderer fontrenderer = renderer.func_76983_a();
        float f = 1.6F;
        float f1 = 0.016666668F * f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)x + 0.0F, (float)y + entityIn.field_70131_O + 0.5F, (float)z);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(-renderer.func_177068_d().field_78735_i, 0.0F, 1.0F, 0.0F);
        GlStateManager.func_179114_b(renderer.func_177068_d().field_78732_j, 1.0F, 0.0F, 0.0F);
        GlStateManager.func_179152_a(-f1, -f1, f1);
        GlStateManager.func_179140_f();
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179097_i();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a(770, 771, 1, 0);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        int i = 0;

        int j = fontrenderer.func_78256_a(str) / 2;
        GlStateManager.func_179090_x();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b((double)(-j - 1), (double)(-1 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
        worldrenderer.func_181662_b((double)(-j - 1), (double)(8 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
        worldrenderer.func_181662_b((double)(j + 1), (double)(8 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
        worldrenderer.func_181662_b((double)(j + 1), (double)(-1 + i), 0.0D).func_181666_a(0.0F, 0.0F, 0.0F, 0.25F).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, 553648127);
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a(true);
        fontrenderer.func_78276_b(str, -fontrenderer.func_78256_a(str) / 2, i, -1);
        GlStateManager.func_179145_e();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.func_179121_F();
    }
}