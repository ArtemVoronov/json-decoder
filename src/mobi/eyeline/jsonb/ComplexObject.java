package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by voronov on 06.08.2015.
 */
@JSONObject
public class ComplexObject {

//    @JSONProperty
    private SimpleObject innerObject1;
//    @JSONProperty
    private SimpleObject innerObject2;
//    @JSONProperty
    private String stringProp;

    @JSONProperty
    private int intArray[];

    @JSONProperty
    private String stringArray[];

    @JSONProperty
    private boolean booleanArray[];

    @JSONProperty
    private SimpleObject[] objectArray;

    public int[] getIntArray() {
        return intArray;
    }

    public void setIntArray(int[] intArray) {
        this.intArray = intArray;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public boolean[] getBooleanArray() {
        return booleanArray;
    }

    public void setBooleanArray(boolean[] booleanArray) {
        this.booleanArray = booleanArray;
    }

    public SimpleObject[] getObjectArray() {
        return objectArray;
    }

    public void setObjectArray(SimpleObject[] objectArray) {
        this.objectArray = objectArray;
    }

    public SimpleObject getInnerObject1() {
        return innerObject1;
    }

    public void setInnerObject1(SimpleObject innerObject1) {
        this.innerObject1 = innerObject1;
    }

    public SimpleObject getInnerObject2() {
        return innerObject2;
    }

    public void setInnerObject2(SimpleObject innerObject2) {
        this.innerObject2 = innerObject2;
    }

    public String getStringProp() {
        return stringProp;
    }

    public void setStringProp(String stringProp) {
        this.stringProp = stringProp;
    }
}
