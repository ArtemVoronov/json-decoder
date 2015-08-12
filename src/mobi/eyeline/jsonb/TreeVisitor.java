package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

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
            throws InstantiationException, IllegalAccessException, UnmarshallerException, InvocationTargetException {
        Queue<Node> queue=new LinkedList<>();
        queue.add(top);
        do {
            if (!queue.isEmpty()) top=queue.poll();
            initArrayElement(top, list, type, serialize);
            List<Node> children = top.getNodes();
            if (children != null) {
                queue.addAll(children);
            }
        } while (!queue.isEmpty());
    }

    /**
     * delete double quotes, check whether it is not string
     * @param node
     * @return
     * @throws UnmarshallerException
     */
    private String processArrayStringValue(Node node) throws UnmarshallerException {
        String text = node.getValue();
        int len = text.length();
        if (len > 1) {
            String starts = text.substring(0, 1);
            String ends = text.substring(len - 1,len);
            if (starts.equals("\"") && ends.equals("\"")) {
                return text.substring(1, len - 1);
            } else {
                throw new UnmarshallerException("Type mismatch: " +
                        "array type is string, but element is not");
            }
        } else {
            throw new UnmarshallerException("Type mismatch: " +
                    "array type is string, but element is not");
        }
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
            throws IllegalAccessException, InstantiationException, UnmarshallerException, InvocationTargetException {
        Object value = null;
        if (node.isNull()) {
            if (serialize) {
                list.add(value);
            }
            return;
        }
        try {
            if (type.equals(String.class)) {
                value = processArrayStringValue(node);
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
                String booleanText = node.getValue().toLowerCase().trim();
                if (booleanText.equals("false") || booleanText.equals("true")) {
                    value = Boolean.parseBoolean(node.getValue());
                } else {
                    throw new UnmarshallerException("Type mismatch: " +
                            "array element type is not boolean");
                }
            } else if (type.equals(Boolean.class)) {
                String booleanText = node.getValue().toLowerCase().trim();
                if (booleanText.equals("false") || booleanText.equals("true")) {
                    value = Boolean.valueOf(node.getValue());
                } else {
                    throw new UnmarshallerException("Type mismatch: " +
                            "array element type is not boolean");
                }
            } else if (type.isArray()) {
                //array element type
                Class componentType = type.getComponentType();

                //get node values of array
                List<Node> children = node.getNodes();
                List elements = new ArrayList<>();
                Node arrayNode = children.iterator().next();
                List<Node> valueNodes = arrayNode.getNodes();

                //process array values and collect all data which should be serialized
                for (Node valueNode : valueNodes) {
                    visitArrayNode(elements, valueNode, componentType, serialize);
                }

                //cast array
                Object[] result = castArray(componentType, elements);

                //delete processed node
                cutBranch(node);

                //add array for list,
                //IMPORTANT: arrays are wrapped in Object[], because we takes [0] every time
                list.add(result[0]);
                return;
            } else {
                //it could be Object.class
                //or custom Object
                if (type.equals(Object.class)) {
                    if (node.getType().equals(NodeType.VALUE_NUMBER)) {
                        value = Double.parseDouble(node.getValue());
                    } else if (node.getType().equals(NodeType.VALUE_BOOLEAN)) {
                        value = Boolean.parseBoolean(node.getValue());
                    } else if (node.getType().equals(NodeType.VALUE_NULL)) {
                        value = null;
                    } else if (node.getType().equals(NodeType.VALUE_STRING)) {
                        value = processArrayStringValue(node);
                    } else  {
                        value = node.getValue();
                    }
                } else {
                    value = type.newInstance();
                    visitTree(node, value);
                    cutBranch(node);
                }
            }
            list.add(value);
        } catch (NumberFormatException ex) {
            throw new UnmarshallerException("Type mismatch: " +
                    "array element type and array type of object are different");
        } catch (StringIndexOutOfBoundsException ex) {

            //actually this code snippet never will be performed,
            //because Lexer always return STRING token in double quotes
            //and all cases for unquoted tokens are processed
            //but when we cutting unknown string we must be sure
            //that in the event of exception we get informative message
            throw new UnmarshallerException("Type mismatch: " +
                    "array type is string, but element is not");
        }
    }

    /**
     * check the node and the field of object
     * @param node current node
     * @param fields all fields of the object marked as @JSONProperty
     * @param clazz class of the object
     * @param obj object of deserialization
     * @throws UnmarshallerException
     */
    private void check(Node node, Map<String, Field> fields, Class<?> clazz, Object obj)
            throws UnmarshallerException {

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
                        valueArray = castArray(componentType, list);

                        field.setAccessible(true);
                        //[0] - because all array wrapped in Object[]
                        field.set(obj,valueArray[0]);

                        //delete processed node
                        cutBranch(node);
                        return;
                    } else {
                        //it could be Object.class
                        //or custom Object
                        if (type.equals(Object.class)) {
                            if (node.getType().equals(NodeType.PAIR_NUMBER)) {
                                value = Double.parseDouble(node.getValue());
                            } else if (node.getType().equals(NodeType.PAIR_BOOLEAN)) {
                                value = Boolean.parseBoolean(node.getValue());
                            } else if (node.getType().equals(NodeType.PAIR_NULL)) {
                                value = null;
                            }  else {
                                value = node.getValue();
                            }

                        } else {
                            value = type.newInstance();
                            visitTree(node, value);
                            cutBranch(node);
                        }
                    }

                    //set value
                    field.setAccessible(true);
                    field.set(obj, value);

                    //debug info
                    //System.out.println("set " + field.getName() +"  == " + value);
                } catch (IllegalAccessException e) {
                    throw new UnmarshallerException("Internal error of unmarshalling process: " +
                            "IllegalAccessException");
                } catch (InvocationTargetException e) {
                    throw new UnmarshallerException("Internal error of unmarshalling process: " +
                            "InvocationTargetException");
                } catch (InstantiationException e) {
                    throw new UnmarshallerException("Internal error of unmarshalling process: " +
                            "InstantiationException");
                }
            }
        }

    }

    /**
     * cast array to field type
     * @param componentType type of array elements
     * @param list list of values that should be casted
     * @return casted array wrapped in Object[]
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws UnmarshallerException
     */
    private Object[] castArray(Class<?> componentType, List list)
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
            return new Object[]{result};
        } else if (componentType.equals(Integer.class)) {
            Integer[] result = new Integer[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Integer.valueOf(valueArray[i].toString());
                }
            }
            return new Object[]{result};
        } else if (componentType.equals(String.class)) {
            String[] result = new String[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = valueArray[i].toString();
                }
            }
            return new Object[]{result};
        } else if (componentType.equals(Boolean.TYPE)) {
            boolean[] result = new boolean[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Boolean)valueArray[i]).booleanValue();
                }
            }
            return new Object[]{result};
        } else if (componentType.equals(Boolean.class)) {
            Boolean[] result = new Boolean[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Boolean.valueOf(valueArray[i].toString());
                }
            }
            return new Object[]{result};
        } else if (componentType.equals(Double.TYPE)) {
            double[] result = new double[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Double)valueArray[i]).doubleValue();
                }
            }
            return new Object[]{result};
        } else if (componentType.equals(Double.class)) {
            Double[] result = new Double[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Double.valueOf(valueArray[i].toString());
                }
            }
            return new Object[]{result};
        } else if (componentType.equals(Float.TYPE)) {
            float[] result = new float[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    continue;
                } else {
                    result[i] = ((Float)valueArray[i]).floatValue();
                }

            }
            return new Object[]{result};
        } else if (componentType.equals(Float.class)) {
            Float[] result = new Float[valueArray.length];
            for (int i = 0; i < valueArray.length; i++) {
                if (valueArray[i] == null) {
                    result[i] = null;
                } else {
                    result[i] = Float.valueOf(valueArray[i].toString());
                }
            }
            return new Object[]{result};
        } else if (componentType.isArray()) {
            Object[] result =  (Object[]) Array.newInstance(componentType, valueArray.length);
            System.arraycopy(valueArray, 0, result, 0, valueArray.length);
            return new Object[] {result};
        } else {
            Object[] result =  (Object[]) Array.newInstance(componentType, valueArray.length);
            System.arraycopy(valueArray, 0, result, 0, valueArray.length);
            return new Object[] {result};
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
