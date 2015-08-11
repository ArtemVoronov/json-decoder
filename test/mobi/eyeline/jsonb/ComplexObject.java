package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

/**
 * Created by Artem Voronov on 06.08.2015.
 */
@JSONObject
public class ComplexObject extends SimpleObject {

    @JSONProperty
    private SimpleObject innerSimpleObject;

    @JSONProperty
    private SimpleObject[] arraySimpleObject;

    public SimpleObject getInnerSimpleObject() {
        return innerSimpleObject;
    }

    public void setInnerSimpleObject(SimpleObject innerSimpleObject) {
        this.innerSimpleObject = innerSimpleObject;
    }

    public SimpleObject[] getArraySimpleObject() {
        return arraySimpleObject;
    }

    public void setArraySimpleObject(SimpleObject[] arraySimpleObject) {
        this.arraySimpleObject = arraySimpleObject;
    }
}
