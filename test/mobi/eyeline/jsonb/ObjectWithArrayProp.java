package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.annotations.JSONObject;
import mobi.eyeline.jsonb.annotations.JSONProperty;

@JSONObject
public class ObjectWithArrayProp {

  @JSONProperty
  private int intArray[];

  @JSONProperty
  private String stringArray[];

  @JSONProperty
  private boolean booleanArray[];

  @JSONProperty
  private ObjectWithSimpleProps[] objectArray;

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

  public ObjectWithSimpleProps[] getObjectArray() {
    return objectArray;
  }

  public void setObjectArray(ObjectWithSimpleProps[] objectArray) {
    this.objectArray = objectArray;
  }
}
