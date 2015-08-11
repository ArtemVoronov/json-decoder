package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 11.08.2015.
 */
@JSONObject
public class MultiDimensionalArrays {

    @JSONProperty
    private int[][] multiIntPrimitive;

    @JSONProperty
    private boolean[][] multiBooleanPrimitive;

    @JSONProperty
    private double[][] multiDoublePrimitive;

    @JSONProperty
    private float[][] multiFloatPrimitive;

    @JSONProperty
    private String[][] multiString;

    @JSONProperty
    private Integer[][] multiInt;

    @JSONProperty
    private Boolean[][] multiBoolean;

    @JSONProperty
    private Double[][] multiDouble;

    @JSONProperty
    private Float[][] multiFloat;

    public int[][] getMultiIntPrimitive() {
        return multiIntPrimitive;
    }

    public void setMultiIntPrimitive(int[][] multiIntPrimitive) {
        this.multiIntPrimitive = multiIntPrimitive;
    }

    public boolean[][] getMultiBooleanPrimitive() {
        return multiBooleanPrimitive;
    }

    public void setMultiBooleanPrimitive(boolean[][] multiBooleanPrimitive) {
        this.multiBooleanPrimitive = multiBooleanPrimitive;
    }

    public double[][] getMultiDoublePrimitive() {
        return multiDoublePrimitive;
    }

    public void setMultiDoublePrimitive(double[][] multiDoublePrimitive) {
        this.multiDoublePrimitive = multiDoublePrimitive;
    }

    public float[][] getMultiFloatPrimitive() {
        return multiFloatPrimitive;
    }

    public void setMultiFloatPrimitive(float[][] multiFloatPrimitive) {
        this.multiFloatPrimitive = multiFloatPrimitive;
    }

    public String[][] getMultiString() {
        return multiString;
    }

    public void setMultiString(String[][] multiString) {
        this.multiString = multiString;
    }

    public Integer[][] getMultiInt() {
        return multiInt;
    }

    public void setMultiInt(Integer[][] multiInt) {
        this.multiInt = multiInt;
    }

    public Boolean[][] getMultiBoolean() {
        return multiBoolean;
    }

    public void setMultiBoolean(Boolean[][] multiBoolean) {
        this.multiBoolean = multiBoolean;
    }

    public Double[][] getMultiDouble() {
        return multiDouble;
    }

    public void setMultiDouble(Double[][] multiDouble) {
        this.multiDouble = multiDouble;
    }

    public Float[][] getMultiFloat() {
        return multiFloat;
    }

    public void setMultiFloat(Float[][] multiFloat) {
        this.multiFloat = multiFloat;
    }
}
