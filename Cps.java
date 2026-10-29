package dev.flyzer.module;

import org.lwjgl.glfw.GLFW;

import java.util.ArrayDeque;

public final class Cps {
    private static final ArrayDeque<Long> LEFT = new ArrayDeque<>(), RIGHT = new ArrayDeque<>();
    private static boolean lastL, lastR;

    public static void poll(long window) {
        long now = System.currentTimeMillis();
        boolean l = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
        boolean r = GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;
        if (l && !lastL) LEFT.add(now);
        if (r && !lastR) RIGHT.add(now);
        lastL = l;
        lastR = r;
        while (!LEFT.isEmpty() && now - LEFT.peek() > 1000) LEFT.poll();
        while (!RIGHT.isEmpty() && now - RIGHT.peek() > 1000) RIGHT.poll();
    }

    public static int left() { return LEFT.size(); }
    public static int right() { return RIGHT.size(); }
    public static boolean leftDown() { return lastL; }
    public static boolean rightDown() { return lastR; }
}
