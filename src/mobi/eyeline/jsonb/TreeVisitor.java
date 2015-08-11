package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TreeVisitor is used for watching tree nodes and creating java objects according of deserializing class info:
 * fields types, annotations @JSONObject for class and @JSONProperty for fields.
 * Created by Artem Voronov on 05.08.2015.
 */
public class TreeVisitor {

    /**
     * get all fields of class 'clazz' with annotation 'ann'
     * @param clazz - class where should be fields with annotation 'ann'
     * @param ann - annotation of the field (generally it should be JSONProperty.class
     * @return map where key is name of field in lower case, value is Field.class instance
     */
    private Map<String, Field> findFields(Class<?> clazz, Class<? extends Annotation> ann) {
        Map<String, Field> map = new HashMap<>();
        Class<?> c = clazz;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    map.put(field.getName().toLowerCase(), field);
                }
            }
            c = c.getSuperclass();
        }
        return map;
    }

    /**
     * checks whether this method is setter
     * @param method
     * @return true if it is setter
     */
    private boolean isSetter(Method method){
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

    /**
     * get setter method for the field
     * @param field field of the clazz
     * @param clazz class which contains the field
     * @return setter method for field
     */
    private Method getSetter(Field field, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods){
            if (isSetter(method) && (method.getName().toLowerCase().endsWith(field.getName().toLowerCase()))) {
                System.out.println("setter for field: " + field.getName());
                return method;
            }
        }
        return null;
    }

    /**
     * check array node and the field of object
     * @param list list which will be filled by array values
     * @param top array node
     * @param type array element type
     * @param serialize @JSONProperty.serializeIfNull() (def == true)
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws UnmarshallerException
     */
    private void visitArrayNode(List list, Node top, Class<?> type, boolean serialize)
            throws InstantiationException, IllegalAccessException, UnmarshallerException {
        Queue<Node> queue=new LinkedList<>();
        queue.add(top);
        do {
            if (!queue.isEmpty()) top=queue.poll();
            initArrayElement(top, list, type, serialize);
            List<Node> childrens = top.getNodes();
            if (childrens != null) {
                for(Node child : childrens) {
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty());
    }

    /**
     * add array element from tree to the list (in other words create Java Object from tree node)
     * @param node array value node
     * @param list list which will be filled
     * @param type array element type
     * @param serialize @JSONProperty.serializeIfNull() (def == true)
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws UnmarshallerException
     */
    private void initArrayElement(Node node, List list, Class<?> type, boolean serialize)
            throws IllegalAccessException, InstantiationException, UnmarshallerException {
        Object value = null;
        if (node.isNull()) {
            if (serialize) {
                list.add(value);
            }
            return;
        }

        if (type.equals(String.class)) {
            value = node.getValue().substring(1, node.getValue().length() - 1);
        } else if (type.equals(Integer.TYPE)) {
            value = Integer.parseInt(node.getValue());
        } else if (type.equals(Integer.class)) {
            value = Integer.valueOf(node.getValue());
        } else if (type.equals(Double.TYPE)) {
            value = Double.parseDouble(node.getValue());
        } else if (type.equals(Double.class)) {
            value = Double.valueOf(node.getValue());
        } else if (type.equals(Float.TYPE)) {
            value = Float.parseFloat(node.getValue());
        } else if (type.equals(Float.class)) {
            value = Float.valueOf(node.getValue());
        } else if (type.equals(Boolean.TYPE)) {
            value = Boolean.parseBoolean(node.getValue());
        } else if (type.equals(Boolean.class)) {
            value = Boolean.valueOf(node.getValue());
        } else if (type.isArray()) {
            //TODO
            throw new UnmarshallerException("Wrong format of input string: " +
                    "array element should have a key and it must be equal to java-object attribute name");
        } else {
            //else -> object
            value = type.newInstance();
            visitTree(node, value);
            cutBranch(node);

        }
        list.add(value);
    }

    /**
     * check the node and the field of object
     * @param node current node
     * @param fields all fields of the object marked as @JSONProperty
     * @param clazz class of the object
     * @param obj object of deserialization
     * @throws UnmarshallerException
     */
    private void check(Node node, Map<String, Field> fields, Class<?> clazz, Object obj) throws UnmarshallerException {

        //in all cases null is default value (for primitive types, for wrappers and for complex objects
        //exclusion is only array elements, case of [..., null, ...]
        if (node.isNull()) {
            return;
        }

        String nodeName = node.getName().toLowerCase();
        if (fields.containsKey(nodeName)) {
            Field field = fields.get(nodeName);

            //variable name should be == json key name
            if (node.getName().toLowerCase().equals(field.getName().toLowerCase())) {
                Method setter = getSetter(field, clazz);
                if (setter == null) {
                    throw new UnmarshallerException("no setter for field: " + field.getName());
                }
                Class type = field.getType();
                try {
                    Object value = null;
                    Object[] valueArray = null;
                    if (type.equals(String.class)) {
                        value = node.getValue();
                    } else if (type.equals(Integer.TYPE)) {
                        value = Integer.parseInt(node.getValue());
                    } else if (type.equals(Integer.class)) {
                        value = Integer.valueOf(node.getValue());
                    } else if (type.equals(Double.TYPE)) {
                        value = Double.parseDouble(node.getValue());
                    } else if (type.equals(Double.class)) {
                        value = Double.valueOf(node.getValue());
                    } else if (type.equals(Float.TYPE)) {
                        value = Float.parseFloat(node.getValue());
                    } else if (type.equals(Float.class)) {
                        value = Float.valueOf(node.getValue());
                    } else if (type.equals(Boolean.TYPE)) {
                        value = Boolean.parseBoolean(node.getValue());
                    } else if (type.equals(Boolean.class)) {
                        value = Boolean.valueOf(node.getValue());
                    } else if (type.isArray()) {

                        //check serialize if null (see @JSONProperty)
                        boolean serialize = field.getAnnotation(JSONProperty.class).serializeIfNull();

                        //array element type
                        Class componentType = field.getType().getComponentType();

                        //get node values of array
                        List<Node> children = node.getNodes();
                        List list = new ArrayList<>();
                        Node arrayNode = children.iterator().next();
                        List<Node> valueNodes = arrayNode.getNodes();

                        //process array values and collect all data which should be serialized
                        for (Node valueNode : valueNodes) {
                            visitArrayNode(list, valueNode, componentType, serialize);
                        }

                        //check all cases for java types, cast and save value
                        castAndSave(componentType, list, setter, obj);

                        //delete processed node
                        cutBranch(node);
                        return;
                    } else {
                        //complex field (object)
                        value = type.newInstance();
                        visitTree(node, value);
                        cutBranch(node);
                    }

                    //set value
                    setter.invoke(obj,value);

                    //TODO: comment debug info later
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

    /**
     * cast array field and save it in the object
     * @param componentType type of array elements
     * @param list list of values that shuld be casted
     * @param setter setter method which will be invoked
     * @param obj object of deserialization
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws UnmarshallerException
     */
    private void castAndSave(Class<?> componentType, List list, Method setter, Object obj)
            throws InvocationTargetException, IllegalAccessException, UnmarshallerException {
        Object[] valueArray = list.toArray();
        if (componentType.equals(Integer.TYPE)) {
            int[] result = new int[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Integer)valueArray[i]).intValue();
                }
            }
            setter.invoke(obj,result);
        } else if (componentType.equals(Integer.class)) {
            Integer[] result = new Integer[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Integer.valueOf(valueArray[i].toString());
                }
            }
            setter.invoke(obj, new Object[] {result} );
        } else if (componentType.equals(String.class)) {
            String[] result = new String[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = valueArray[i].toString();
                }
            }
            setter.invoke(obj, new Object[] {result} );
        } else if (componentType.equals(Boolean.TYPE)) {
            boolean[] result = new boolean[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Boolean)valueArray[i]).booleanValue();
                }
            }
            setter.invoke(obj,result);
        } else if (componentType.equals(Boolean.class)) {
            Boolean[] result = new Boolean[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Boolean.valueOf(valueArray[i].toString());
                }
            }
            setter.invoke(obj, new Object[] {result} );
        } else if (componentType.equals(Double.TYPE)) {
            double[] result = new double[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Double)valueArray[i]).doubleValue();
                }
            }
            setter.invoke(obj,result);
        } else if (componentType.equals(Double.class)) {
            Double[] result = new Double[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Double.valueOf(valueArray[i].toString());
                }
            }
            setter.invoke(obj, new Object[] {result} );
        } else if (componentType.equals(Float.TYPE)) {
            float[] result = new float[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Float)valueArray[i]).floatValue();
                }

            }
            setter.invoke(obj,result);
        } else if (componentType.equals(Float.class)) {
            Float[] result = new Float[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Float.valueOf(valueArray[i].toString());
                }
            }
            setter.invoke(obj, new Object[] {result} );
        } else if (componentType.isArray()) {
            //TODO
            throw new UnmarshallerException("Wrong format of input string: " +
                    "array VALUES should be delimited by commas");
        } else {
            Object[] objects =  (Object[]) Array.newInstance(componentType, valueArray.length);
            System.arraycopy(valueArray,0,objects,0,valueArray.length);
            setter.invoke(obj, new Object[] {objects} );
        }
    }

    /**
     * delete node from the tree
     * @param branch node
     */
    private void cutBranch(Node branch) {
        List<Node> nodes = branch.getNodes();
        for (Node node : nodes) {
            node.setParent(null);
        }
        branch.setNodes(null);
    }

    /**
     * watching nodes for JSONProperty fields (breadth-first search)
     * @param top first node in queue and root of the tree at the beginning
     * @param obj object of deserialization
     * @throws UnmarshallerException errors of deserialization
     */
    public void visitTree(Node top, Object obj) throws UnmarshallerException {
        Class clazz = obj.getClass();
        Map<String, Field> fields = findFields(clazz, JSONProperty.class);
        Queue<Node> queue=new LinkedList<>();
        queue.add(top);
        do {
            if (!queue.isEmpty()) top=queue.poll();
            check(top, fields, clazz, obj);
            List<Node> children = top.getNodes();
            if (children != null) {
                queue.addAll(children);
            }
        } while (!queue.isEmpty());
    }
}
