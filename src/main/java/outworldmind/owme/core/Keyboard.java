package outworldmind.owme.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

public class Keyboard {
	
	public static final int KEY_0 = GLFW.GLFW_KEY_0;
	public static final int KEY_1 = GLFW.GLFW_KEY_1;
	public static final int KEY_2 = GLFW.GLFW_KEY_2;
	public static final int KEY_3 = GLFW.GLFW_KEY_3;
	public static final int KEY_4 = GLFW.GLFW_KEY_4;
	public static final int KEY_5 = GLFW.GLFW_KEY_5;
	public static final int KEY_6 = GLFW.GLFW_KEY_6;
	public static final int KEY_7 = GLFW.GLFW_KEY_7;
	public static final int KEY_8 = GLFW.GLFW_KEY_8;
	public static final int KEY_9 = GLFW.GLFW_KEY_9;
	
	public static final int KEY_MINUS = GLFW.GLFW_KEY_MINUS;
	public static final int KEY_EQUAL = GLFW.GLFW_KEY_EQUAL;
	public static final int KEY_BACKSPACE = GLFW.GLFW_KEY_BACKSPACE;
	public static final int KEY_HOME = GLFW.GLFW_KEY_HOME;
	public static final int KEY_END = GLFW.GLFW_KEY_END;
	public static final int KEY_DELETE = GLFW.GLFW_KEY_DELETE;
	
	public static final int KEY_RIGHT = GLFW.GLFW_KEY_RIGHT;
	public static final int KEY_LEFT = GLFW.GLFW_KEY_LEFT;
	public static final int KEY_UP = GLFW.GLFW_KEY_UP;
	public static final int KEY_DOWN = GLFW.GLFW_KEY_DOWN;

	public static final int KEY_PAGE_UP = GLFW.GLFW_KEY_PAGE_UP;
	public static final int KEY_PAGE_DOWN = GLFW.GLFW_KEY_PAGE_DOWN;
	public static final int KEY_PAUSE = GLFW.GLFW_KEY_PAUSE;
	public static final int KEY_PRINT_SCREEN = GLFW.GLFW_KEY_PRINT_SCREEN;
	public static final int KEY_INSERT = GLFW.GLFW_KEY_INSERT;

	public static final int KEY_F1 = GLFW.GLFW_KEY_F1;
	public static final int KEY_F2 = GLFW.GLFW_KEY_F2;
	public static final int KEY_F3 = GLFW.GLFW_KEY_F3;
	public static final int KEY_F4 = GLFW.GLFW_KEY_F4;
	public static final int KEY_F5 = GLFW.GLFW_KEY_F5;
	public static final int KEY_F6 = GLFW.GLFW_KEY_F6;
	public static final int KEY_F7 = GLFW.GLFW_KEY_F7;
	public static final int KEY_F8 = GLFW.GLFW_KEY_F8;
	public static final int KEY_F9 = GLFW.GLFW_KEY_F9;
	public static final int KEY_F10 = GLFW.GLFW_KEY_F10;
	public static final int KEY_F11 = GLFW.GLFW_KEY_F11;
	public static final int KEY_F12 = GLFW.GLFW_KEY_F12;
	
	public static final int KEY_CONSOLE = GLFW.GLFW_KEY_GRAVE_ACCENT;
	public static final int KEY_ESCAPE = GLFW.GLFW_KEY_ESCAPE;
	public static final int KEY_ENTER = GLFW.GLFW_KEY_ENTER;
	public static final int KEY_SPACE = GLFW.GLFW_KEY_SPACE;
	public static final int KEY_TAB = GLFW.GLFW_KEY_TAB;
	public static final int KEY_CAPS_LOCK = GLFW.GLFW_KEY_CAPS_LOCK;
	public static final int KEY_SCROLL_LOCK = GLFW.GLFW_KEY_SCROLL_LOCK;
	public static final int KEY_NUM_LOCK = GLFW.GLFW_KEY_NUM_LOCK;

	public static final int KEY_SHIFT = GLFW.GLFW_KEY_LEFT_SHIFT;
	public static final int KEY_SHIFT_RIGHT = GLFW.GLFW_KEY_RIGHT_SHIFT;
	public static final int KEY_CTRL = GLFW.GLFW_KEY_LEFT_CONTROL;
	public static final int KEY_CTRL_RIGHT = GLFW.GLFW_KEY_RIGHT_CONTROL;
	public static final int KEY_ALT = GLFW.GLFW_KEY_LEFT_ALT;
	public static final int KEY_ALT_RIGHT = GLFW.GLFW_KEY_RIGHT_ALT;

	public static final int KEY_MENU = GLFW.GLFW_KEY_MENU;
	public static final int KEY_A = GLFW.GLFW_KEY_A;
	public static final int KEY_B = GLFW.GLFW_KEY_B;
	public static final int KEY_C = GLFW.GLFW_KEY_C;
	public static final int KEY_D = GLFW.GLFW_KEY_D;
	public static final int KEY_E = GLFW.GLFW_KEY_E;
	public static final int KEY_F = GLFW.GLFW_KEY_F;
	public static final int KEY_G = GLFW.GLFW_KEY_G;
	public static final int KEY_H = GLFW.GLFW_KEY_H;
	public static final int KEY_I = GLFW.GLFW_KEY_I;
	public static final int KEY_J = GLFW.GLFW_KEY_J;
	public static final int KEY_K = GLFW.GLFW_KEY_K;
	public static final int KEY_L = GLFW.GLFW_KEY_L;
	public static final int KEY_M = GLFW.GLFW_KEY_M;
	public static final int KEY_N = GLFW.GLFW_KEY_N;
	public static final int KEY_O = GLFW.GLFW_KEY_O;
	public static final int KEY_P = GLFW.GLFW_KEY_P;
	public static final int KEY_Q = GLFW.GLFW_KEY_Q;
	public static final int KEY_R = GLFW.GLFW_KEY_R;
	public static final int KEY_S = GLFW.GLFW_KEY_S;
	public static final int KEY_T = GLFW.GLFW_KEY_T;
	public static final int KEY_U = GLFW.GLFW_KEY_U;
	public static final int KEY_V = GLFW.GLFW_KEY_V;
	public static final int KEY_W = GLFW.GLFW_KEY_W;
	public static final int KEY_X = GLFW.GLFW_KEY_X;
	public static final int KEY_Y = GLFW.GLFW_KEY_Y;
	public static final int KEY_Z = GLFW.GLFW_KEY_Z;
	public static final int KEY_BRACKET_LEFT = GLFW.GLFW_KEY_LEFT_BRACKET;
	public static final int KEY_BRACKET_RIGHT = GLFW.GLFW_KEY_RIGHT_BRACKET;
	public static final int KEY_SEMICOLON = GLFW.GLFW_KEY_SEMICOLON;
	public static final int KEY_APOSTROPHE = GLFW.GLFW_KEY_APOSTROPHE;
	public static final int KEY_COMMA = GLFW.GLFW_KEY_COMMA;
	public static final int KEY_DOT = GLFW.GLFW_KEY_PERIOD;
	public static final int KEY_SLASH = GLFW.GLFW_KEY_SLASH;
	public static final int KEY_BACK_SLASH = GLFW.GLFW_KEY_BACKSLASH;
	
	private List<Consumer<KeyEvent>> keyEvents;
	private static List<Integer> notReleasedKeys;

	protected Keyboard(Config config) {
		keyEvents = new ArrayList<Consumer<KeyEvent>>();
		notReleasedKeys = new ArrayList<Integer>();
	}
	
	protected void update(long window, int key, int scancode, int action, int mods) {
		if (action == Controls.ACTION_PRESSED)
			notReleasedKeys.add(key);
		if (action == Controls.ACTION_RELEASED)
			notReleasedKeys.remove((Integer) key);
		
		keyEvents.forEach(consumer -> {
			var event = new KeyEvent(window, key, scancode, action, mods);				
			consumer.accept(event);
		});
	}
	
	public void bindKey(Consumer<KeyEvent> event) {
		keyEvents.add(event);
	}
	
	public static boolean keyPressed(int key, Window window, KeyEvent event) {
		return event.getWindow() == window.getId() && keyPressed(key, event);
	}
	
	public static boolean keyPressed(int key, KeyEvent event) {
		return event.getKey() == key && event.getAction() == Controls.ACTION_PRESSED;
	}
	
	public static boolean keyReleased(int key, Window window, KeyEvent event) {
		return event.getWindow() == window.getId() && keyReleased(key, event);
	}
	
	public static boolean keyReleased(int key, KeyEvent event) {
		return event.getKey() == key && event.getAction() == Controls.ACTION_RELEASED;
	}
	
	public static boolean keyHold(int key, Window window, KeyEvent event) {
		return event.getWindow() == window.getId() && keyHold(key);
	}
	
	public static boolean keyHold(int key) {
		return notReleasedKeys.contains(key);                                                         
	}
	
	
}
