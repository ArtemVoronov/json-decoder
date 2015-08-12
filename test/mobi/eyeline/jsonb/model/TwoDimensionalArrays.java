package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 11.08.2015.
 */
@JSONObject
public class TwoDimensionalArrays {

    @JSONProperty
    private int[][] twoDimIntPrimitive;

    @JSONProperty
    private boolean[][] twoDimBooleanPrimitive;

    @JSONProperty
    private double[][] twoDimDoublePrimitive;

    @JSONProperty
    private float[][] twoDimFloatPrimitive;

    @JSONProperty
    private String[][] twoDimString;

    @JSONProperty
    private Integer[][] twoDimInt;

    @JSONProperty
    private Boolean[][] twoDimBoolean;

    @JSONProperty
    private Double[][] twoDimDouble;

    @JSONProperty
    private Float[][] twoDimFloat;

    @JSONProperty
    private SimpleObject[][] twoDimSimpleObject;

    public int[][] getTwoDimIntPrimitive() {
        return twoDimIntPrimitive;
    }

    public void setTwoDimIntPrimitive(int[][] twoDimIntPrimitive) {
        this.twoDimIntPrimitive = twoDimIntPrimitive;
    }

    public boolean[][] getTwoDimBooleanPrimitive() {
        return twoDimBooleanPrimitive;
    }

    public void setTwoDimBooleanPrimitive(boolean[][] twoDimBooleanPrimitive) {
        this.twoDimBooleanPrimitive = twoDimBooleanPrimitive;
    }

    public double[][] getTwoDimDoublePrimitive() {
        return twoDimDoublePrimitive;
    }

    public void setTwoDimDoublePrimitive(double[][] twoDimDoublePrimitive) {
        this.twoDimDoublePrimitive = twoDimDoublePrimitive;
    }

    public float[][] getTwoDimFloatPrimitive() {
        return twoDimFloatPrimitive;
    }

    public void setTwoDimFloatPrimitive(float[][] twoDimFloatPrimitive) {
        this.twoDimFloatPrimitive = twoDimFloatPrimitive;
    }

    public String[][] getTwoDimString() {
        return twoDimString;
    }

    public void setTwoDimString(String[][] twoDimString) {
        this.twoDimString = twoDimString;
    }

    public Integer[][] getTwoDimInt() {
        return twoDimInt;
    }

    public void setTwoDimInt(Integer[][] twoDimInt) {
        this.twoDimInt = twoDimInt;
    }

    public Boolean[][] getTwoDimBoolean() {
        return twoDimBoolean;
    }

    public void setTwoDimBoolean(Boolean[][] twoDimBoolean) {
        this.twoDimBoolean = twoDimBoolean;
    }

    public Double[][] getTwoDimDouble() {
        return twoDimDouble;
    }

    public void setTwoDimDouble(Double[][] twoDimDouble) {
        this.twoDimDouble = twoDimDouble;
    }

    public Float[][] getTwoDimFloat() {
        return twoDimFloat;
    }

    public void setTwoDimFloat(Float[][] twoDimFloat) {
        this.twoDimFloat = twoDimFloat;
    }

    public SimpleObject[][] getTwoDimSimpleObject() {
        return twoDimSimpleObject;
    }

    public void setTwoDimSimpleObject(SimpleObject[][] twoDimSimpleObject) {
        this.twoDimSimpleObject = twoDimSimpleObject;
    }
}
