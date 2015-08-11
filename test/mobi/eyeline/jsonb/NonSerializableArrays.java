package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 10.08.2015.
 */
public class NonSerializableArrays extends SimpleObject {
    @JSONProperty(serializeIfNull = false)
    private int[] intPropPrimitiveArray;

    @JSONProperty(serializeIfNull = false)
    private boolean[] booleanPropPrimitiveArray;

    @JSONProperty(serializeIfNull = false)
    private double[] doublePropPrimitiveArray;

    @JSONProperty(serializeIfNull = false)
    private float[] floatPropPrimitiveArray;

    @JSONProperty(serializeIfNull = false)
    private String[] stringPropArray;

    @JSONProperty(serializeIfNull = false)
    private Integer[] intPropArray;

    @JSONProperty(serializeIfNull = false)
    private Boolean[] booleanPropArray;

    @JSONProperty(serializeIfNull = false)
    private Double[] doublePropArray;

    @JSONProperty(serializeIfNull = false)
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
