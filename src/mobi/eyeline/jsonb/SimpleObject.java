package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by voronov on 06.08.2015.
 */
@JSONObject
public class SimpleObject {

//    @JSONProperty(serializeIfNull = false)
//    private String stringProp;
//    @JSONProperty
//    private int intProp;
//    @JSONProperty
//    private boolean booleanProp;
//
//    public String getStringProp() {
//        return stringProp;
//    }
//
//    public void setStringProp(String stringProp) {
//        this.stringProp = stringProp;
//    }
//
//    public int getIntProp() {
//        return intProp;
//    }
//
//    public void setIntProp(int intProp) {
//        this.intProp = intProp;
//    }
//
//    public boolean isBooleanProp() {
//        return booleanProp;
//    }
//
//    public void setBooleanProp(boolean booleanProp) {
//        this.booleanProp = booleanProp;
//    }

    @JSONProperty
    private int intProp_primitive;

    @JSONProperty
    private boolean booleanProp_primitive;

    @JSONProperty
    private double doubleProp_primitive;

    @JSONProperty
    private float floatProp_primitive;

    @JSONProperty(serializeIfNull = false)
    private String stringProp;

    @JSONProperty
    private Integer intProp;

    @JSONProperty
    private Boolean booleanProp;

    @JSONProperty
    private Double doubleProp;

    @JSONProperty
    private Float floatProp;

    public int getIntProp_primitive() {
        return intProp_primitive;
    }

    public void setIntProp_primitive(int intProp_primitive) {
        this.intProp_primitive = intProp_primitive;
    }

    public boolean isBooleanProp_primitive() {
        return booleanProp_primitive;
    }

    public void setBooleanProp_primitive(boolean booleanProp_primitive) {
        this.booleanProp_primitive = booleanProp_primitive;
    }

    public double getDoubleProp_primitive() {
        return doubleProp_primitive;
    }

    public void setDoubleProp_primitive(double doubleProp_primitive) {
        this.doubleProp_primitive = doubleProp_primitive;
    }

    public float getFloatProp_primitive() {
        return floatProp_primitive;
    }

    public void setFloatProp_primitive(float floatProp_primitive) {
        this.floatProp_primitive = floatProp_primitive;
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
