package mobi.eyeline.jsonb.model;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

@JSONObject
public class ObjectWithSimpleProps {

  @JSONProperty(serializeIfNull = false)
  private String stringProp;
  @JSONProperty
  private int intProp;
  @JSONProperty
  private boolean booleanProp;

  public String getStringProp() {
    return stringProp;
  }

  public void setStringProp(String stringProp) {
    this.stringProp = stringProp;
  }

  public int getIntProp() {
    return intProp;
  }

  public void setIntProp(int intProp) {
    this.intProp = intProp;
  }

  public boolean isBooleanProp() {
    return booleanProp;
  }

  public void setBooleanProp(boolean booleanProp) {
    this.booleanProp = booleanProp;
  }
}
