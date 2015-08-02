package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

@JSONObject
public class ObjectWithObjectProp {

  @JSONProperty
  private ObjectWithSimpleProps innerObject1;
  @JSONProperty
  private ObjectWithSimpleProps innerObject2;
  @JSONProperty
  private String stringProp;

  public ObjectWithSimpleProps getInnerObject1() {
    return innerObject1;
  }

  public void setInnerObject1(ObjectWithSimpleProps innerObject1) {
    this.innerObject1 = innerObject1;
  }

  public ObjectWithSimpleProps getInnerObject2() {
    return innerObject2;
  }

  public void setInnerObject2(ObjectWithSimpleProps innerObject2) {
    this.innerObject2 = innerObject2;
  }

  public String getStringProp() {
    return stringProp;
  }

  public void setStringProp(String stringProp) {
    this.stringProp = stringProp;
  }
}