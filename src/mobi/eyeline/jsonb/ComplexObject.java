package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by voronov on 06.08.2015.
 */
@JSONObject
public class ComplexObject {

//    @JSONProperty
//    private SimpleObject innerObject1;
//    @JSONProperty
//    private SimpleObject innerObject2;
//    @JSONProperty
//    private String stringProp;

    @JSONProperty//(serializeIfNull = false)
    private SimpleObject[] objectArray;

    @JSONProperty
    private int[] intArray_primitive;

    @JSONProperty//(serializeIfNull = false)
    private String[] stringArray;

    @JSONProperty
    private boolean[] booleanArray_primitive;
//
//    @JSONProperty
//    private double[] doubleArray_primitive;
//
//    @JSONProperty
//    private float[] floatArray_primitive;
//
//    @JSONProperty
//    private Integer[] integerArray;
//
//    @JSONProperty
//    private Boolean[] booleanArray;
//
//    @JSONProperty
//    private Double[] doubleArray;
//
//    @JSONProperty
//    private Float[] floatArray;

    public SimpleObject[] getObjectArray() {
        return objectArray;
    }

    public void setObjectArray(SimpleObject[] objectArray) {
        this.objectArray = objectArray;
    }

    public int[] getIntArray_primitive() {
        return intArray_primitive;
    }

    public void setIntArray_primitive(int[] intArray_primitive) {
        this.intArray_primitive = intArray_primitive;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public boolean[] getBooleanArray_primitive() {
        return booleanArray_primitive;
    }

    public void setBooleanArray_primitive(boolean[] booleanArray_primitive) {
        this.booleanArray_primitive = booleanArray_primitive;
    }
}
