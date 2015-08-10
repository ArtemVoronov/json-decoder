package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONProperty;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * Created by voronov on 05.08.2015.
 */
public class TreeVisitor {

    /**
     * @param clazz - class where should be fields with @JSONProperty annotation
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

    private boolean isSetter(Method method){
        if (!method.getName().startsWith("set")) {
            return false;
        }
        if (method.getParameterTypes().length != 1) {
            return false;
        }
        return true;
    }

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

    private void visitArrayNode(ArrayList arrayList, Node top, Class<?> type, boolean serialize)
            throws InstantiationException, IllegalAccessException, UnmarshallerException {
        Queue<Node> queue=new LinkedList<>();
        queue.add(top);
        do {
            if (!queue.isEmpty()) top=queue.poll();
            initArrayElement(top, arrayList, type, serialize);
            List<Node> childrens = top.getNodes();
            if (childrens != null) {
                for(Node child : childrens) {
                    queue.add(child);
                }
            }
        } while (!queue.isEmpty());
    }

    private void initArrayElement(Node node, ArrayList arrayList, Class<?> type, boolean serialize)
            throws IllegalAccessException, InstantiationException, UnmarshallerException {
        Object value = null;
        if (node.isNull()) {
            if (serialize) {
                arrayList.add(value);
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
        arrayList.add(value);
    }

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
                        boolean serialize = field.getAnnotation(JSONProperty.class).serializeIfNull();
                        List<Node> childs = node.getNodes();
                        Class componentType = field.getType().getComponentType();
                        ArrayList arr = new ArrayList();
                        Node arrayNode = childs.iterator().next();
                        List<Node> values = arrayNode.getNodes();
                        for (Node child : values) {
                            visitArrayNode(arr, child, componentType, serialize);
                        }
                        valueArray = arr.toArray();

                        //check all cases for java types
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


                        cutBranch(node);
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

    private void cutBranch(Node branch) {
        List<Node> nodes = branch.getNodes();
        for (Node node : nodes) {
            node.setParent(null);
        }
        branch.setNodes(null);
    }

    //watching nodes for JSONProperty fields
    public void visitTree(Node top, Object obj) throws UnmarshallerException {
        Class clazz = obj.getClass();
        Map<String, Field> fields = findFields(clazz, JSONProperty.class);
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
