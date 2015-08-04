package mobi.eyeline.jsonb;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voronov on 04.08.2015.
 *
 */
public class Node {
    private String name;
    private String value;
    private NodeType type;
    private List<Node> nodes;
    private Node parent;

    Node() {
        this.nodes = new ArrayList<Node>();
    }

    Node(String name, String value) {
        this.name = name;
        this.value = value;
        this.nodes = new ArrayList<Node>();
    }

    public void put(Node node) {
        nodes.add(node);
        node.setParent(this);
    }

    //TODO: make pretty printin (string concatenation in StringBuilder.append()
    public String getNodeInfo(String indent) {
        StringBuilder info = new StringBuilder(indent + "[:" + getType() + " " + getName() + " " + getValue() + "]");
        for (Node node : nodes) {
            info.append("\n" + node.getNodeInfo(indent + " "));
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

    public List getNodes() {
        return nodes;
    }

    //TODO: unchecked assignment
    public void setNodes(List nodes) {
        this.nodes = nodes;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
