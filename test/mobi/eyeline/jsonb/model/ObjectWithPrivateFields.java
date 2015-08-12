package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 12.08.2015.
 */
@JSONObject
public class ObjectWithPrivateFields {

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

    @JSONProperty
    private SimpleObject simpleObject;

    public int getIntPropPrimitive() {
        return intPropPrimitive;
    }

    public boolean isBooleanPropPrimitive() {
        return booleanPropPrimitive;
    }

    public double getDoublePropPrimitive() {
        return doublePropPrimitive;
    }

    public float getFloatPropPrimitive() {
        return floatPropPrimitive;
    }

    public String getStringProp() {
        return stringProp;
    }

    public Integer getIntProp() {
        return intProp;
    }

    public Boolean getBooleanProp() {
        return booleanProp;
    }

    public Double getDoubleProp() {
        return doubleProp;
    }

    public Float getFloatProp() {
        return floatProp;
    }

    public SimpleObject getSimpleObject() {
        return simpleObject;
    }
}
