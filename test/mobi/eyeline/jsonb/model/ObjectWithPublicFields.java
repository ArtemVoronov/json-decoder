package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 12.08.2015.
 */
@JSONObject
public class ObjectWithPublicFields {

    @JSONProperty
    public int intPropPrimitive;

    @JSONProperty
    public boolean booleanPropPrimitive;

    @JSONProperty
    public double doublePropPrimitive;

    @JSONProperty
    public float floatPropPrimitive;

    @JSONProperty
    public String stringProp;

    @JSONProperty
    public Integer intProp;

    @JSONProperty
    public Boolean booleanProp;

    @JSONProperty
    public Double doubleProp;

    @JSONProperty
    public Float floatProp;

    @JSONProperty
    public SimpleObject simpleObject;
}
