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

//    @JSONProperty
//    private SimpleObject[] objectArray;

    @JSONProperty
    private int[] intArray_primitive;

    @JSONProperty
    private String[] stringArray;

    @JSONProperty
    private boolean[] booleanArray_primitive;

    @JSONProperty
    private double[] doubleArray_primitive;

    @JSONProperty
    private float[] floatArray_primitive;

    @JSONProperty
    private Integer[] integerArray;

    @JSONProperty
    private Boolean[] booleanArray;

    @JSONProperty
    private Double[] doubleArray;

    @JSONProperty
    private Float[] floatArray;

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

    public double[] getDoubleArray_primitive() {
        return doubleArray_primitive;
    }

    public void setDoubleArray_primitive(double[] doubleArray_primitive) {
        this.doubleArray_primitive = doubleArray_primitive;
    }

    public float[] getFloatArray_primitive() {
        return floatArray_primitive;
    }

    public void setFloatArray_primitive(float[] floatArray_primitive) {
        this.floatArray_primitive = floatArray_primitive;
    }

    public Integer[] getIntegerArray() {
        return integerArray;
    }

    public void setIntegerArray(Integer[] integerArray) {
        this.integerArray = integerArray;
    }

    public Boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(Boolean[] booleanArray) {
        this.booleanArray = booleanArray;
    }

    public Double[] getDoubleArray() {
        return doubleArray;
    }

    public void setDoubleArray(Double[] doubleArray) {
        this.doubleArray = doubleArray;
    }

    public Float[] getFloatArray() {
        return floatArray;
    }

    public void setFloatArray(Float[] floatArray) {
        this.floatArray = floatArray;
    }
}
