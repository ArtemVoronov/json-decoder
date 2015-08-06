package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by voronov on 05.08.2015.
 */
public class TreeVisitor {

    public Set<Field> findFields(Class<?> clazz, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = clazz;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

    public boolean isSetter(Method method){
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

    public Method getSetter(Field field, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods){
            if (isSetter(method) && (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))) {
                System.out.println("setter for field: " + field.getName());
                return method;
            }
        }
        return null;
    }

    public void visitArrayNode(ArrayList arrayList, Node top, Class<?> type) {
        Queue<Node> queue=new LinkedList<>();
        queue.add(top);
        do {
            if (!queue.isEmpty()) top=queue.poll();
            initArrayElement(top, arrayList, type);
            List<Node> childrens = top.getNodes();
            if (childrens != null) {
                for(Node child : childrens) {
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty());
    }

    public void initArrayElement(Node node, ArrayList arrayList, Class<?> type) {
        Object value = null;
        if (type.equals(String.class)) {
            value = node.getValue();
        } else if (type.equals(Integer.TYPE)) {
            value = Integer.parseInt(node.getValue());
        } else if (type.equals(Double.TYPE)) {
            value = Double.parseDouble(node.getValue());
        } else if (type.equals(Float.TYPE)) {
            value = Float.parseFloat(node.getValue());
        } else if (type.equals(Boolean.TYPE)) {
            value = Boolean.parseBoolean(node.getValue());
        } else if (type.isArray()) {
            //TODO
        } else {
            //Object
            //TODO
        }
        arrayList.add(value);
    }

    public void check(Node node, Set<Field> fields, Class<?> clazz, Object obj) {
        for (Field field : fields) {
            if (node.getName().toLowerCase().equals(field.getName().toLowerCase())) {
                Method setter = getSetter(field, clazz);
                Class type = field.getType();
                try {
                    Object value = null;
                    Object[] valueArray = null;
                    if (type.equals(String.class)) {
                        value = node.getValue();
                    } else if (type.equals(Integer.TYPE)) {
                        value = Integer.parseInt(node.getValue());
                    } else if (type.equals(Double.TYPE)) {
                        value = Double.parseDouble(node.getValue());
                    } else if (type.equals(Float.TYPE)) {
                        value = Float.parseFloat(node.getValue());
                    } else if (type.equals(Boolean.TYPE)) {
                        value = Boolean.parseBoolean(node.getValue());
                    } else if (type.isArray()) {
                        //TODO: resolve casting problem
                        List<Node> childs = node.getNodes();
                        Class componentType = field.getType().getComponentType();
                        ArrayList arr = new ArrayList();
                        Node arrayNode = childs.iterator().next();
                        List<Node> values = arrayNode.getNodes();
                        for (Node child : values) {
                            visitArrayNode(arr, child, componentType);
                        }


                        valueArray = arr.toArray();
                        setter.invoke(obj,valueArray);
                        return;
                    } else {
                        //complex field (object)
                        value = type.newInstance();
                        visitTree(node, value);
                        cutBranch(node);
                    }
                    setter.invoke(obj,value);
                    System.out.println("set " + field.getName() +"  == " + value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void cutBranch(Node branch) {
        List<Node> nodes = branch.getNodes();
        for (Node node : nodes) {
            node.setParent(null);
        }
        branch.setNodes(null);
    }

    //watching nodes for JSONProperty fields
    public void visitTree(Node top, Object obj){
        Class clazz = obj.getClass();
        Set<Field> fields = findFields(clazz, JSONProperty.class);
        Queue<Node> queue=new LinkedList<>();
        queue.add(top);
        do {
            if (!queue.isEmpty()) top=queue.poll();
            check(top, fields, clazz, obj);
            List<Node> childrens = top.getNodes();
            if (childrens != null) {
                for(Node child : childrens) {
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty());
    }
}
