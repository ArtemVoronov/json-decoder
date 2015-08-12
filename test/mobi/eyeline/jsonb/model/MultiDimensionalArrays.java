package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 11.08.2015.
 */
@JSONObject
public class MultiDimensionalArrays {

    @JSONProperty
    private int[][][] multiDimIntPrimitive;

    @JSONProperty
    private boolean[][][] multiDimBooleanPrimitive;

    @JSONProperty
    private double[][][] multiDimDoublePrimitive;

    @JSONProperty
    private float[][][] multiDimFloatPrimitive;

    @JSONProperty
    private String[][][] multiDimString;

    @JSONProperty
    private Integer[][][] multiDimInt;

    @JSONProperty
    private Boolean[][][] multiDimBoolean;

    @JSONProperty
    private Double[][][] multiDimDouble;

    @JSONProperty
    private Float[][][] multiDimFloat;

    @JSONProperty
    private SimpleObject[][][] multiDimSimpleObject;

    public int[][][] getMultiDimIntPrimitive() {
        return multiDimIntPrimitive;
    }

    public void setMultiDimIntPrimitive(int[][][] multiDimIntPrimitive) {
        this.multiDimIntPrimitive = multiDimIntPrimitive;
    }

    public boolean[][][] getMultiDimBooleanPrimitive() {
        return multiDimBooleanPrimitive;
    }

    public void setMultiDimBooleanPrimitive(boolean[][][] multiDimBooleanPrimitive) {
        this.multiDimBooleanPrimitive = multiDimBooleanPrimitive;
    }

    public double[][][] getMultiDimDoublePrimitive() {
        return multiDimDoublePrimitive;
    }

    public void setMultiDimDoublePrimitive(double[][][] multiDimDoublePrimitive) {
        this.multiDimDoublePrimitive = multiDimDoublePrimitive;
    }

    public float[][][] getMultiDimFloatPrimitive() {
        return multiDimFloatPrimitive;
    }

    public void setMultiDimFloatPrimitive(float[][][] multiDimFloatPrimitive) {
        this.multiDimFloatPrimitive = multiDimFloatPrimitive;
    }

    public String[][][] getMultiDimString() {
        return multiDimString;
    }

    public void setMultiDimString(String[][][] multiDimString) {
        this.multiDimString = multiDimString;
    }

    public Integer[][][] getMultiDimInt() {
        return multiDimInt;
    }

    public void setMultiDimInt(Integer[][][] multiDimInt) {
        this.multiDimInt = multiDimInt;
    }

    public Boolean[][][] getMultiDimBoolean() {
        return multiDimBoolean;
    }

    public void setMultiDimBoolean(Boolean[][][] multiDimBoolean) {
        this.multiDimBoolean = multiDimBoolean;
    }

    public Double[][][] getMultiDimDouble() {
        return multiDimDouble;
    }

    public void setMultiDimDouble(Double[][][] multiDimDouble) {
        this.multiDimDouble = multiDimDouble;
    }

    public Float[][][] getMultiDimFloat() {
        return multiDimFloat;
    }

    public void setMultiDimFloat(Float[][][] multiDimFloat) {
        this.multiDimFloat = multiDimFloat;
    }

    public SimpleObject[][][] getMultiDimSimpleObject() {
        return multiDimSimpleObject;
    }

    public void setMultiDimSimpleObject(SimpleObject[][][] multiDimSimpleObject) {
        this.multiDimSimpleObject = multiDimSimpleObject;
    }
}
