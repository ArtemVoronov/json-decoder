package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 06.08.2015.
 */
@JSONObject
public class SimpleObject {

    @JSONProperty
    private int intPropPrimitive;

    @JSONProperty
    private boolean booleanPropPrimitive;

    @JSONProperty
    private double doublePropPrimitive;

    @JSONProperty
    private float floatPropPrimitive;

    @JSONProperty
    private String stringProp;

    @JSONProperty
    private Integer intProp;

    @JSONProperty
    private Boolean booleanProp;

    @JSONProperty
    private Double doubleProp;

    @JSONProperty
    private Float floatProp;

    public int getIntPropPrimitive() {
        return intPropPrimitive;
    }

    public void setIntPropPrimitive(int intPropPrimitive) {
        this.intPropPrimitive = intPropPrimitive;
    }

    public boolean isBooleanPropPrimitive() {
        return booleanPropPrimitive;
    }

    public void setBooleanPropPrimitive(boolean booleanPropPrimitive) {
        this.booleanPropPrimitive = booleanPropPrimitive;
    }

    public double getDoublePropPrimitive() {
        return doublePropPrimitive;
    }

    public void setDoublePropPrimitive(double doublePropPrimitive) {
        this.doublePropPrimitive = doublePropPrimitive;
    }

    public float getFloatPropPrimitive() {
        return floatPropPrimitive;
    }

    public void setFloatPropPrimitive(float floatPropPrimitive) {
        this.floatPropPrimitive = floatPropPrimitive;
    }

    public String getStringProp() {
        return stringProp;
    }

    public void setStringProp(String stringProp) {
        this.stringProp = stringProp;
    }

    public Integer getIntProp() {
        return intProp;
    }

    public void setIntProp(Integer intProp) {
        this.intProp = intProp;
    }

    public Boolean getBooleanProp() {
        return booleanProp;
    }

    public void setBooleanProp(Boolean booleanProp) {
        this.booleanProp = booleanProp;
    }

    public Double getDoubleProp() {
        return doubleProp;
    }

    public void setDoubleProp(Double doubleProp) {
        this.doubleProp = doubleProp;
    }

    public Float getFloatProp() {
        return floatProp;
    }

    public void setFloatProp(Float floatProp) {
        this.floatProp = floatProp;
    }
}
