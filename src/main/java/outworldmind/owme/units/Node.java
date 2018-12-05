package outworldmind.owme.units;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Node {
	
	private List<Node> children;
	private Node parent;
	
	protected Node() {
		children = new ArrayList<Node>();
	}
	
	protected Node(Node parent) {
		this();
		setParent(parent);
	}
	
	public List<Node> getChildren() {
		return children;
	}
	
	public void addChild(Node child) {
		children.add(child);
	}
	
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public void forEachChild(Function<Node, ?> callFunction) {
		callFunction.apply(this);
		
		if (children.isEmpty()) return;
		children.forEach(child -> child.forEachChild(callFunction));
	}
	
	public void clean() {
		children.clear();
		parent = null;
	}

}
