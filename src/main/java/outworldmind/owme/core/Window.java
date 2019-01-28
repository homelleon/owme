package outworldmind.owme.core;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import outworldmind.owme.maths.Vector2;

public class Window {
	
	private final static String defaultName = "OWME";
	
	private String name;
	private int width;
	private int height;
	private long id;
	private boolean closeRequest;
	private int FPS;
	private boolean showFPS = false;
	private boolean initialized = false;
	
	public Window(int width, int height) {
		this(defaultName, width, height);
	}
	
	public Window(String name, int width, int height) {
		setName(name);
		setWidth(width);
		setHeight(height);
	}
	
	public void initialize() {
		if (initialized) return;
		initialized = true;
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!glfwInit())
			throw new IllegalStateException("Unable ot initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		
		id = glfwCreateWindow(width, height, name, NULL, NULL);
		
		if (id == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		glfwSetInputMode(id, GLFW.GLFW_LOCK_KEY_MODS, GLFW.GLFW_TRUE);
		glfwSetKeyCallback(id, (window, key, scancode, action, mods) -> {
			Tools.getControls().getKeyboard().update(window, key, scancode, action, mods);
		});
		
		glfwSetMouseButtonCallback(id, (window, button, action, mods) -> {
			Tools.getControls().getMouse().updateButtons(window, button, action, mods);
		});
		
		glfwSetCursorPosCallback(id, (window, xPos, yPos) -> {
			Tools.getControls().getMouse().updateMoves(window, xPos, yPos);
		});
		
		try (MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);
			
			glfwGetWindowSize(id, pWidth, pHeight);
			
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
			
			glfwSetWindowPos(
					id,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		glfwMakeContextCurrent(id);
		glfwSwapInterval(1);
		glfwShowWindow(id);
		
		GL.createCapabilities();
	}
	
	public boolean getCloseRequest() {
		return closeRequest;
	}
	
	public void close() {
		glfwSetWindowShouldClose(id, true);
	}
	
	public void update() {
		glfwSwapBuffers(id);
		glfwPollEvents();
		if (glfwWindowShouldClose(id)) closeRequest = true;
	}
	
	public void destroy() {
		glfwFreeCallbacks(id);
		glfwDestroyWindow(id);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}
	
	public void grabMouse() {
		Tools.getControls().getMouse().setStartPosition(new Vector2(width / 2, height / 2));
		glfwSetCursorPos(id, width / 2, height / 2);
		glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}
	
	public void hideCursor() {
		glfwSetInputMode(id, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
	}
	
	private void setName(String name) {
		this.name = (name == null || name.equals("null")) ? defaultName : name;
	}
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}
	
	private void addToTitle(String title) {
		glfwSetWindowTitle(id, name + title);
	}
	
	private void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	private void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}

}
