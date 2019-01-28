package outworldmind.owme.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import outworldmind.owme.maths.Vector2;

public class Mouse {
	
	public static final int CLICK_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT;
	public static final int CLICK_RIGHT = GLFW.GLFW_MOUSE_BUTTON_RIGHT;
	public static final int CLICK_MIDDLE = GLFW.GLFW_MOUSE_BUTTON_MIDDLE;

	private List<Consumer<MouseButtonEvent>> buttonEvents;
	private List<Consumer<MouseMoveEvent>> moveEvents;
	private static List<Integer> notReleasedButtons;
	
	private static double currXPos;
	private static double currYPos;
	
	protected Mouse(Config config) {
		buttonEvents = new ArrayList<Consumer<MouseButtonEvent>>();
		moveEvents = new ArrayList<Consumer<MouseMoveEvent>>();
		notReleasedButtons = new ArrayList<Integer>();		
	}
	
	protected void updateButtons(long window, int button, int action, int mods) {
		if (action == Controls.ACTION_PRESSED)
			notReleasedButtons.add(button);		
		if (action == Controls.ACTION_RELEASED)
			notReleasedButtons.remove(button);
		
		buttonEvents.forEach(consumer -> {
			var event = new MouseButtonEvent(window, button, action, mods);
			consumer.accept(event);
		});
	}
	
	protected void updateMoves(long window, double xPos, double yPos) {
		moveEvents.forEach(consumer -> {
			var event = new MouseMoveEvent(window, xPos, yPos);
			consumer.accept(event);
		});
		currXPos = xPos;
		currYPos = yPos;
	}
	
	protected void setStartPosition(Vector2 position) {
		currXPos = position.x;
		currYPos = position.y;
	}
	
	public void bindMouseButton(Consumer<MouseButtonEvent> event) {
		buttonEvents.add(event);
	}
	
	public void bindMouseMove(Consumer<MouseMoveEvent> event) {
		moveEvents.add(event);
	}
	
	public static boolean buttonPressed(int button, MouseButtonEvent event) {
		return event.getButton() == button && event.getAction() == Controls.ACTION_PRESSED;
	}
	
	public static boolean buttonReleased(int button, MouseButtonEvent event) {
		return event.getButton() == button && event.getAction() == Controls.ACTION_RELEASED;
	}
	
	public static boolean movedLeft(MouseMoveEvent event) {
		return event.getXPos() > currXPos;
	}
	
	public static boolean movedRight(MouseMoveEvent event) {
		return event.getXPos() < currXPos;
	}
	
	public static boolean movedUp(MouseMoveEvent event) {
		return event.getYPos() > currYPos;
	}
	
	public static boolean movedDown(MouseMoveEvent event) {
		return event.getYPos() < currYPos;
	}
	
	public static boolean buttonHold(int button) {
		return notReleasedButtons.contains(button);
	}
	
	public static Vector2 getDPos(MouseMoveEvent event) {
		var dX = (float) (currXPos - event.getXPos());
		var dY = (float) (currYPos - event.getYPos());
		return new Vector2(dX, dY);
	}
}
