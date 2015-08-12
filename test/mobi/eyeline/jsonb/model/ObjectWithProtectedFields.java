package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 12.08.2015.
 */
@JSONObject
public class ObjectWithProtectedFields {

    @JSONProperty
    protected int intPropPrimitive;

    @JSONProperty
    protected boolean booleanPropPrimitive;

    @JSONProperty
    protected double doublePropPrimitive;

    @JSONProperty
    protected float floatPropPrimitive;

    @JSONProperty
    protected String stringProp;

    @JSONProperty
    protected Integer intProp;

    @JSONProperty
    protected Boolean booleanProp;

    @JSONProperty
    protected Double doubleProp;

    @JSONProperty
    protected Float floatProp;

    @JSONProperty
    protected SimpleObject simpleObject;

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
