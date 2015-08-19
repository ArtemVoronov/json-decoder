package mobi.eyeline.jsonb;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree structure of JSON objects and arrays. It is used by Parser class.
 * Created by Artem Voronov on 04.08.2015.
 */
public class Node {
    private String name;
    private String value;
    private NodeType type;
    private List<Node> nodes;
    private Node parent;
    private boolean isNull;

    Node() {
        this.name = "";
        this.value = "";
        this.nodes = new ArrayList<>();
        this.isNull = false;
    }

    Node(String name, String value) {
        this.name = name;
        this.value = value;
        this.nodes = new ArrayList<>();
        this.isNull = false;
    }

    public void put(Node node) {
        nodes.add(node);
        node.setParent(this);
    }

    public String getNodeInfo(String indent) {
        StringBuilder info = new StringBuilder(indent + "[:" + getType() + " " + getName() +
                " " + getValue() + " " + isNull() + "]");
        for (Node node : nodes) {
            info.append("\n");
            info.append(node.getNodeInfo(indent + " "));
        }
        return info.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setIsNull(boolean isNull) {
        this.isNull = isNull;
    }

    public boolean hasChildren() {
        return (nodes.size() > 0);
    }
}
