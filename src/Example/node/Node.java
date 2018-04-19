package Example.node;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

	private T data = null;
	private List<Node<T>> children = new ArrayList<>();
	private Node<T> parent;

	public Node(T data) {
		this.data = data;
	}

	public Node<T> addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
		return child;
	}

	public void addChildren(List<Node<T>> children) {
		children.forEach(each -> each.setParent(this));
		this.children.addAll(children);
	}

	public List<Node<T>> getChildren() {
		return children;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	private void setParent(Node<T> parent) {
		this.parent = parent;
	}

	public Node<T> getParent() {
		return parent;
	}

	public void deleteNode() {
		if (parent != null) {
			int index = this.parent.getChildren().indexOf(this);
			this.parent.getChildren().remove(this);
			for (Node<T> each : getChildren()) {
				each.setParent(this.parent);
			}
			this.parent.getChildren().addAll(index, this.getChildren());
		} else {
			deleteRootNode();
		}
		this.getChildren().clear();
	}

	public Node<T> deleteRootNode() {
		if (parent != null) {
			throw new IllegalStateException("deleteRootNode not called on root");
		}
		Node<T> newParent = null;
		if (!getChildren().isEmpty()) {
			newParent = getChildren().get(0);
			newParent.setParent(null);
			getChildren().remove(0);
			for (Node<T> each : getChildren()) {
				each.setParent(newParent);
			}
			newParent.getChildren().addAll(getChildren());
		}
		this.getChildren().clear();
		return newParent;
	}
}