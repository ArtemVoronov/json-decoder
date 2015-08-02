package mobi.eyeline.jsonb;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class UnmarshallerTest {

  @Test
  public void testDeserialize_SimpleProps() throws UnmarshallerException {
    String json="{" +
        "stringProp:stringValue," +
        "intProp:123," +
        "unknownProp:false,"+
        "booleanProp:true"+
        "}";

    ObjectWithSimpleProps ob = Unmarshaller.unmarshal(json, ObjectWithSimpleProps.class);
    assertNotNull(ob);
    assertEquals("stringValue", ob.getStringProp());
    assertEquals(123, ob.getIntProp());
    assertEquals(true, ob.isBooleanProp());
  }

  @Test
  public void testDeserialize_InnerObjectProp() throws UnmarshallerException {
    String json="{" +
        "innerObject1 : { " +
        "stringProp:stringValue,"+
        "intProp:123,"+
        "booleanProp:true"+
        "}," +
        "innerObject2 : {" +
        "stringProp:stringValue2,"+
        "intProp:1232,"+
        "booleanProp:false"+
        "},"+
        "stringProp:stringVal2,"+
        "unknownProp: {\"key\":false}"+
        "}";

    ObjectWithObjectProp obj = Unmarshaller.unmarshal(json, ObjectWithObjectProp.class);
    assertNotNull(obj);
    assertEquals("stringValue", obj.getInnerObject1().getStringProp());
    assertEquals(123, obj.getInnerObject1().getIntProp());
    assertEquals(true, obj.getInnerObject1().isBooleanProp());
    assertEquals("stringValue2", obj.getInnerObject2().getStringProp());
    assertEquals(1232, obj.getInnerObject2().getIntProp());
    assertEquals(false, obj.getInnerObject2().isBooleanProp());
    assertEquals("stringVal2", obj.getStringProp());
  }

  @Test
  public void testDeserialize_IntArraProp() throws UnmarshallerException {
    String json = "{"+
        "intArray : [1,2],"+
        "stringArray : [\"3\",\"4\",  null],"+
        "booleanArray : [true,false,true],"+
        "objectArray : [{\"intProp\":1}, {\"intProp\" : 2}, null]"+
        "}";

    ObjectWithArrayProp obj = Unmarshaller.unmarshal(json, ObjectWithArrayProp.class);
    assertNotNull(obj);
    assertEquals(1, obj.getIntArray()[0]);
    assertEquals(2, obj.getIntArray()[1]);
    assertEquals("3", obj.getStringArray()[0]);
    assertEquals("4", obj.getStringArray()[1]);
    assertEquals(null, obj.getStringArray()[2]);
    assertEquals(true, obj.getBooleanArray()[0]);
    assertEquals(false, obj.getBooleanArray()[1]);
    assertEquals(true, obj.getBooleanArray()[2]);
    assertEquals(1, obj.getObjectArray()[0].getIntProp());
    assertEquals(2, obj.getObjectArray()[1].getIntProp());
    assertNull(obj.getObjectArray()[2]);
  }
}
