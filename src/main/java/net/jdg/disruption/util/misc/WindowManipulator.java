package net.jdg.disruption.util.misc;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class WindowManipulator {
        public static Window getWindow() {
            return Minecraft.getInstance().getWindow();
        }
        public static void showWindow() {
            GLFW.glfwShowWindow(getWindow().getWindow());
        }
        public static void hideWindow() {
            GLFW.glfwHideWindow(getWindow().getWindow());
        }
        public static void freezeFor(long milliseconds) {
            var prevTime = System.currentTimeMillis();
            while (true) {
                if ((prevTime - System.currentTimeMillis()) < -milliseconds) {
                    break;
                }
            }
        }
        public static void setPosition(int x, int y) {
            GLFW.glfwSetWindowPos(getWindow().getWindow(), x, y);
        }
        public static void setSize(int w, int h) {
            GLFW.glfwSetWindowSize(getWindow().getWindow(), w, h);
        }
        public static void setDimensions(int x, int y, int w, int h) {
            GLFW.glfwSetWindowSize(getWindow().getWindow(), w, h);
            GLFW.glfwSetWindowPos(getWindow().getWindow(), x, y);
        }
        public static class MightCauseSecurityControversyIdk {
            private MightCauseSecurityControversyIdk() {}
            public static void pressKey(char k) {
                try {
                    var key = KeyEvent.getExtendedKeyCodeForChar(k);
                    var bot = new Robot();
                    bot.keyPress(key);
                    bot.keyRelease(key);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                };
            }
            public static void middleClick() {
                try {
                    var bot = new Robot();
                    bot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                    bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                };
            }
            public static void rightClick() {
                try {
                    var bot = new Robot();
                    bot.mousePress(InputEvent.BUTTON2_DOWN_MASK);
                    bot.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                };
            }
            public static void leftClick() {
                try {
                    var bot = new Robot();
                    bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                };
            }
            public static void setCursorPos(int x, int y) {
                GLFW.glfwSetCursorPos(getWindow().getWindow(), x, y);
            }
        }
    }

