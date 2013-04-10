package com.voracious.dragons.client.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.voracious.dragons.client.graphics.Screen;
import com.voracious.dragons.common.Vec2D;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    public static final int VK_MOUSE_1 = 7894561;
    public static final int VK_MOUSE_2 = 7894562;
    public static final int VK_MOUSE_WHEEL = 7894563;

    public static HashMap<Integer, Boolean> keymap;
    public static ArrayList<Screen> screens;

    private static int mousex = 0;
    private static int mousey = 0;
    private static int dx = 0;
    private static int dy = 0;
    private static int sx = 0;
    private static int sy = 0;
    private static boolean letMouseMove = true;
    private static Robot robot;

    private static Logger logger = Logger.getLogger(InputHandler.class);

    public InputHandler() {
        keymap = new HashMap<Integer, Boolean>(307);
        screens = new ArrayList<Screen>();

        try {
            robot = new Robot();
        } catch (AWTException e) {
            logger.error("Could not capture mouse", e);
        }

        registerButton(VK_MOUSE_1);
        registerButton(VK_MOUSE_2);
        registerButton(VK_MOUSE_WHEEL);
    }

    public static void registerButton(int keyCode) {
        if (keymap.containsKey(keyCode)) {
            logger.warn("Key already registered");
        } else {
            keymap.put(keyCode, false);
        }
    }

    public static void deregisterButton(int keyCode) {
        keymap.remove(keyCode);
    }

    public static void registerScreen(Screen screen) {
        screens.add(screen);
    }

    public static void deregisterScreen(Screen screen) {
        screens.add(screen);
    }

    public static boolean isDown(int keyCode) {
        Boolean result = keymap.get(keyCode);

        if (result == null) {
            logger.error("Key not registered; returned false");
            return false;
        } else {
            return result.booleanValue();
        }
    }

    public static void setMouseMoveable(boolean letMouseMove) {
        InputHandler.letMouseMove = letMouseMove;
    }

    public static Vec2D getChangeInMouse() {
        return new Vec2D.Double(dx, dy);
    }

    public static Vec2D getMousePos() {
        return new Vec2D.Double(mousex, mousey);
    }

    private void handleMouseMotion(MouseEvent arg0) {
        dx = arg0.getX() - mousex;
        dy = arg0.getY() - mousey;

        if (letMouseMove) {
            mousex = arg0.getX();
            mousey = arg0.getY();

            sx = arg0.getXOnScreen();
            sy = arg0.getYOnScreen();
        } else {
            if (robot != null) {
                robot.mouseMove(sx, sy);
            } else {
                logger.warn("Robot is not available");
                letMouseMove = true;
            }
        }

        Iterator<Screen> it = screens.iterator();
        while (it.hasNext()) {
            it.next().mouseMoved(arg0);
        }
    }

    @Override
    public void mouseMoved(MouseEvent arg0) {
        handleMouseMotion(arg0);
    }

    @Override
    public void mouseDragged(MouseEvent arg0) {
        handleMouseMotion(arg0);
    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        System.out.println("mouse pressed" + arg0.getButton() + " " + MouseEvent.BUTTON2);
        switch (arg0.getButton()) {
        case MouseEvent.BUTTON1:
            keymap.put(VK_MOUSE_1, true);
            break;
        case MouseEvent.BUTTON3:
            keymap.put(VK_MOUSE_2, true);
            break;
        case MouseEvent.BUTTON2:
            keymap.put(VK_MOUSE_WHEEL, true);
            break;
        default:
            if (arg0.getButton() != MouseEvent.NOBUTTON) {
                if (keymap.containsKey(arg0.getButton())) {
                    keymap.put(arg0.getButton(), true);
                }
            }
        }

        Iterator<Screen> it = screens.iterator();
        while (it.hasNext()) {
            it.next().mousePressed(arg0);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        switch (arg0.getButton()) {
        case MouseEvent.BUTTON1:
            keymap.put(VK_MOUSE_1, false);
            break;
        case MouseEvent.BUTTON3:
            keymap.put(VK_MOUSE_2, false);
            break;
        case MouseEvent.BUTTON2:
            keymap.put(VK_MOUSE_WHEEL, false);
            break;
        default:
            if (arg0.getButton() != MouseEvent.NOBUTTON) {
                if (keymap.containsKey(arg0.getButton())) {
                    keymap.put(arg0.getButton(), false);
                }
            }
        }

        Iterator<Screen> it = screens.iterator();
        while (it.hasNext()) {
            it.next().mouseReleased(arg0);
        }
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        if (!keymap.containsKey(arg0.getKeyCode()) || keymap.get(arg0.getKeyCode()).booleanValue()) {
            keymap.put(arg0.getKeyCode(), true);
            
            Iterator<Screen> it = screens.iterator();
            while (it.hasNext()) {
                it.next().keyPressed(arg0);
            }
        }else if (keymap.containsKey(arg0.getKeyCode())) {
            keymap.put(arg0.getKeyCode(), true);
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        if (keymap.containsKey(arg0.getKeyCode())) {
            keymap.put(arg0.getKeyCode(), false);
        }

        Iterator<Screen> it = screens.iterator();
        while (it.hasNext()) {
            it.next().keyReleased(arg0);
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        Iterator<Screen> it = screens.iterator();
        while (it.hasNext()) {
            it.next().keyTyped(arg0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        Iterator<Screen> it = screens.iterator();
        while (it.hasNext()) {
            it.next().mouseClicked(arg0);
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }
}
