package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 10.08.2015.
 */
@JSONObject
public class SerializableArrays extends SimpleObject {

    @JSONProperty
    private int[] intPropPrimitiveArray;

    @JSONProperty
    private boolean[] booleanPropPrimitiveArray;

    @JSONProperty
    private double[] doublePropPrimitiveArray;

    @JSONProperty
    private float[] floatPropPrimitiveArray;

    @JSONProperty
    private String[] stringPropArray;

    @JSONProperty
    private Integer[] intPropArray;

    @JSONProperty
    private Boolean[] booleanPropArray;

    @JSONProperty
    private Double[] doublePropArray;

    @JSONProperty
    private Float[] floatPropArray;

    public int[] getIntPropPrimitiveArray() {
        return intPropPrimitiveArray;
    }

    public void setIntPropPrimitiveArray(int[] intPropPrimitiveArray) {
        this.intPropPrimitiveArray = intPropPrimitiveArray;
    }

    public boolean[] getBooleanPropPrimitiveArray() {
        return booleanPropPrimitiveArray;
    }

    public void setBooleanPropPrimitiveArray(boolean[] booleanPropPrimitiveArray) {
        this.booleanPropPrimitiveArray = booleanPropPrimitiveArray;
    }

    public double[] getDoublePropPrimitiveArray() {
        return doublePropPrimitiveArray;
    }

    public void setDoublePropPrimitiveArray(double[] doublePropPrimitiveArray) {
        this.doublePropPrimitiveArray = doublePropPrimitiveArray;
    }

    public float[] getFloatPropPrimitiveArray() {
        return floatPropPrimitiveArray;
    }

    public void setFloatPropPrimitiveArray(float[] floatPropPrimitiveArray) {
        this.floatPropPrimitiveArray = floatPropPrimitiveArray;
    }

    public String[] getStringPropArray() {
        return stringPropArray;
    }

    public void setStringPropArray(String[] stringPropArray) {
        this.stringPropArray = stringPropArray;
    }

    public Integer[] getIntPropArray() {
        return intPropArray;
    }

    public void setIntPropArray(Integer[] intPropArray) {
        this.intPropArray = intPropArray;
    }

    public Boolean[] getBooleanPropArray() {
        return booleanPropArray;
    }

    public void setBooleanPropArray(Boolean[] booleanPropArray) {
        this.booleanPropArray = booleanPropArray;
    }

    public Double[] getDoublePropArray() {
        return doublePropArray;
    }

    public void setDoublePropArray(Double[] doublePropArray) {
        this.doublePropArray = doublePropArray;
    }

    public Float[] getFloatPropArray() {
        return floatPropArray;
    }

    public void setFloatPropArray(Float[] floatPropArray) {
        this.floatPropArray = floatPropArray;
    }
}
