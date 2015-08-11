package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 10.08.2015.
 */
@JSONObject
public class TheMostComplexObject extends ComplexObject{

    @JSONProperty
    private ComplexObject innerComplexObject;

    @JSONProperty
    private ComplexObject[] arrayComplexObject;

    public ComplexObject getInnerComplexObject() {
        return innerComplexObject;
    }

    public void setInnerComplexObject(ComplexObject innerComplexObject) {
        this.innerComplexObject = innerComplexObject;
    }

    public ComplexObject[] getArrayComplexObject() {
        return arrayComplexObject;
    }

    public void setArrayComplexObject(ComplexObject[] arrayComplexObject) {
        this.arrayComplexObject = arrayComplexObject;
    }
}
