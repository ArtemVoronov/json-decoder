package mobi.eyeline.jsonb;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class UnmarshallerTest {

    @Ignore
    @Test
    public void testDeserialize_SimpleProps() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json="{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intProp\":123," +
                "\"unknownProp\":false,"+
                "\"booleanProp\":true"+
                "}";

        ObjectWithSimpleProps obj = Unmarshaller.unmarshal(json, ObjectWithSimpleProps.class);
        assertNotNull(obj);
        assertEquals("stringValue", obj.getStringProp());
        assertEquals(123, obj.getIntProp());
        assertEquals(true, obj.isBooleanProp());
    }

    @Ignore
    @Test
    public void testDeserialize_InnerObjectProp() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json="{" +
                "\"innerObject1\" : { " +
                "\"stringProp\":\"stringValue\","+
                "\"intProp\":123,"+
                "\"booleanProp\":true"+
                "}," +
                "\"innerObject2\" : {" +
                "\"stringProp\":\"stringValue2\","+
                "\"intProp\":1232,"+
                "\"booleanProp\":false"+
                "},"+
                "\"stringProp\":\"stringVal2\","+
                "\"unknownProp\": {\"key\":false}"+
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

    @Ignore
    @Test
    public void testDeserialize_IntArrayProp() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{"+
                "\"intArray\" : [1,2],"+
                "\"stringArray\" : [\"3\",\"4\",  null],"+
                "\"booleanArray\" : [true,false,true],"+
                "\"objectArray\" : [{\"intProp\":1}, {\"intProp\" : 2}, null]"+
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

    @Ignore
    @Test
    public void testDeserialize_SimpleObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intPropPrimitive\":1," +
                "\"intProp\":2," +
                "\"doublePropPrimitive\":3.5," +
                "\"doubleProp\":4.5," +
                "\"floatPropPrimitive\":5.5," +
                "\"floatProp\":6.5," +
                "\"booleanPropPrimitive\":true,"+
                "\"booleanProp\":true"+
                "}";
        double delta = 1e-15;

        SimpleObject obj = Unmarshaller.unmarshal(json, SimpleObject.class);
        assertNotNull(obj);
        assertEquals("stringValue", obj.getStringProp());
        assertEquals(1, obj.getIntPropPrimitive());
        assertEquals(new Integer(2), obj.getIntProp());
        assertEquals(3.5, obj.getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), obj.getDoubleProp(), delta);
        assertEquals(5.5, obj.getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), obj.getFloatProp(), delta);
        assertEquals(true, obj.getBooleanProp());
        assertEquals(true, obj.isBooleanPropPrimitive());
    }

    @Ignore
    @Test
    public void testDeserialize_ComplexObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String jsonSimpleObject = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intPropPrimitive\":1," +
                "\"intProp\":2," +
                "\"doublePropPrimitive\":3.5," +
                "\"doubleProp\":4.5," +
                "\"floatPropPrimitive\":5.5," +
                "\"floatProp\":6.5," +
                "\"booleanPropPrimitive\":true,"+
                "\"booleanProp\":true"+
                "}";
        String json = "{"+
                "\"innerSimpleObject\" : " + jsonSimpleObject + "," +
                "\"arraySimpleObject\" : [" + jsonSimpleObject + "," + jsonSimpleObject + "]" +
                "}";
        double delta = 1e-15;

        ComplexObject obj = Unmarshaller.unmarshal(json, ComplexObject.class);

        assertNotNull(obj);

        SimpleObject inner = obj.getInnerSimpleObject();
        SimpleObject[] array = obj.getArraySimpleObject();

        assertNotNull(inner);
        assertNotNull(array);

        assertEquals(2, array.length);

        assertEquals("stringValue", inner.getStringProp());
        assertEquals(1, inner.getIntPropPrimitive());
        assertEquals(new Integer(2), inner.getIntProp());
        assertEquals(3.5, inner.getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), inner.getDoubleProp(), delta);
        assertEquals(5.5, inner.getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), inner.getFloatProp(), delta);
        assertEquals(true, inner.getBooleanProp());
        assertEquals(true, inner.isBooleanPropPrimitive());

        assertEquals("stringValue", array[0].getStringProp());
        assertEquals(1, array[0].getIntPropPrimitive());
        assertEquals(new Integer(2), array[0].getIntProp());
        assertEquals(3.5, array[0].getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), array[0].getDoubleProp(), delta);
        assertEquals(5.5, array[0].getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), array[0].getFloatProp(), delta);
        assertEquals(true, array[0].getBooleanProp());
        assertEquals(true, array[0].isBooleanPropPrimitive());

        assertEquals("stringValue", array[1].getStringProp());
        assertEquals(1, array[1].getIntPropPrimitive());
        assertEquals(new Integer(2), array[1].getIntProp());
        assertEquals(3.5, array[1].getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), array[1].getDoubleProp(), delta);
        assertEquals(5.5, array[1].getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), array[1].getFloatProp(), delta);
        assertEquals(true, array[1].getBooleanProp());
        assertEquals(true, array[1].isBooleanPropPrimitive());
    }

    @Ignore
    @Test
    public void testDeserialize_TheMostComplexObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String jsonSimpleObject = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intPropPrimitive\":1," +
                "\"intProp\":2," +
                "\"doublePropPrimitive\":3.5," +
                "\"doubleProp\":4.5," +
                "\"floatPropPrimitive\":5.5," +
                "\"floatProp\":6.5," +
                "\"booleanPropPrimitive\":true,"+
                "\"booleanProp\":true"+
                "}";

        String jsonComplexObject = "{"+
                "\"innerSimpleObject\" : " + jsonSimpleObject + "," +
                "\"arraySimpleObject\" : [" + jsonSimpleObject + "," + jsonSimpleObject + "]" +
                "}";

        String json = "{"+
                "\"innerComplexObject\" :" + jsonComplexObject + "," +
                "\"arrayComplexObject\" : [" + jsonComplexObject + "," + jsonComplexObject + "]" +
                "}";
        double delta = 1e-15;

        TheMostComplexObject obj = Unmarshaller.unmarshal(json, TheMostComplexObject.class);

        assertNotNull(obj);

        ComplexObject innerComplex = obj.getInnerComplexObject();
        ComplexObject[] arrayComplex = obj.getArrayComplexObject();

        assertNotNull(innerComplex);
        assertNotNull(arrayComplex);

        assertEquals(2, arrayComplex.length);

        ArrayList<SimpleObject> simpleObjects = new ArrayList<>();

        SimpleObject innerSimple1 = innerComplex.getInnerSimpleObject();
        SimpleObject[] arraySimple1 = innerComplex.getArraySimpleObject();

        SimpleObject innerSimple2 = arrayComplex[0].getInnerSimpleObject();
        SimpleObject[] arraySimple2 = arrayComplex[0].getArraySimpleObject();

        SimpleObject innerSimple3 = arrayComplex[1].getInnerSimpleObject();
        SimpleObject[] arraySimple3 = arrayComplex[1].getArraySimpleObject();

        assertNotNull(innerSimple1);
        assertNotNull(arraySimple1);

        assertNotNull(innerSimple2);
        assertNotNull(arraySimple2);

        assertNotNull(innerSimple3);
        assertNotNull(arraySimple3);

        assertEquals(2, arraySimple1.length);
        assertEquals(2, arraySimple2.length);
        assertEquals(2, arraySimple3.length);

        assertNotNull(arraySimple1[0]);
        assertNotNull(arraySimple1[1]);

        assertNotNull(arraySimple2[0]);
        assertNotNull(arraySimple2[1]);

        assertNotNull(arraySimple3[0]);
        assertNotNull(arraySimple3[1]);

        simpleObjects.add(innerSimple1);
        simpleObjects.add(innerSimple2);
        simpleObjects.add(innerSimple3);

        simpleObjects.add(arraySimple1[0]);
        simpleObjects.add(arraySimple1[1]);

        simpleObjects.add(arraySimple2[0]);
        simpleObjects.add(arraySimple2[1]);

        simpleObjects.add(arraySimple3[0]);
        simpleObjects.add(arraySimple3[1]);

        for (SimpleObject object : simpleObjects) {
            assertEquals("stringValue", object.getStringProp());
            assertEquals(1, object.getIntPropPrimitive());
            assertEquals(new Integer(2), object.getIntProp());
            assertEquals(3.5, object.getDoublePropPrimitive(), delta);
            assertEquals(new Double(4.5), object.getDoubleProp(), delta);
            assertEquals(5.5, object.getFloatPropPrimitive(), delta);
            assertEquals(new Float(6.5), object.getFloatProp(), delta);
            assertEquals(true, object.getBooleanProp());
            assertEquals(true, object.isBooleanPropPrimitive());
        }
    }

    @Ignore
    @Test
    public void testDeserialize_SimpleObject_Null() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{" +
                "\"stringProp\":null," +
                "\"intPropPrimitive\":null," +
                "\"intProp\":null," +
                "\"doublePropPrimitive\":null," +
                "\"doubleProp\":null," +
                "\"floatPropPrimitive\":null," +
                "\"floatProp\":null," +
                "\"booleanPropPrimitive\":null," +
                "\"booleanProp\":null" +
                "}";
        double delta = 1e-15;

        SimpleObject obj = Unmarshaller.unmarshal(json, SimpleObject.class);

        assertNotNull(obj);

        assertEquals(0, obj.getIntPropPrimitive());
        assertEquals(0., obj.getDoublePropPrimitive(), delta);
        assertEquals(0., obj.getFloatPropPrimitive(), delta);
        assertEquals(false, obj.isBooleanPropPrimitive());
        assertEquals(null, obj.getStringProp());
        assertEquals(null, obj.getIntProp());
        assertEquals(null, obj.getDoubleProp());
        assertEquals(null, obj.getFloatProp());
        assertEquals(null, obj.getBooleanProp());
    }

    @Ignore
    @Test
    public void testDeserialize_SerializableArrays_Null() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{" +
                "\"intPropPrimitiveArray\" : [1, null, 2]," +
                "\"intPropArray\" : [1, null, 2]," +
                "\"stringPropArray\" : [\"text1\", null, \"text2\"]," +
                "\"doublePropPrimitiveArray\" : [1.5, null, 2.5]," +
                "\"doublePropArray\" : [1.5, null, 2.5]," +
                "\"floatPropPrimitiveArray\" : [1.5, null, 2.5]," +
                "\"floatPropArray\" : [1.5, null, 2.5]," +
                "\"booleanPropPrimitiveArray\" : [true, null, true]," +
                "\"booleanPropArray\" : [true, null, true]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

        assertNotNull(obj);

        int[] intPropPrimitiveArray = obj.getIntPropPrimitiveArray();
        Integer[] intPropArray = obj.getIntPropArray();
        double[] doublePropPrimitiveArray = obj.getDoublePropPrimitiveArray();
        Double[] doublePropArray = obj.getDoublePropArray();
        float[] floatPropPrimitiveArray = obj.getFloatPropPrimitiveArray();
        Float[] floatPropArray = obj.getFloatPropArray();
        boolean[] booleanPropPrimitiveArray = obj.getBooleanPropPrimitiveArray();
        Boolean[] booleanPropArray = obj.getBooleanPropArray();
        String[] stringPropArray = obj.getStringPropArray();

        assertNotNull(intPropPrimitiveArray);
        assertNotNull(intPropArray);
        assertNotNull(doublePropPrimitiveArray);
        assertNotNull(doublePropArray);
        assertNotNull(floatPropPrimitiveArray);
        assertNotNull(floatPropArray);
        assertNotNull(booleanPropPrimitiveArray);
        assertNotNull(booleanPropArray);
        assertNotNull(stringPropArray);

        assertEquals(3, intPropPrimitiveArray.length);
        assertEquals(3, intPropArray.length);
        assertEquals(3, doublePropPrimitiveArray.length);
        assertEquals(3, doublePropArray.length);
        assertEquals(3, floatPropPrimitiveArray.length);
        assertEquals(3, floatPropArray.length);
        assertEquals(3, booleanPropPrimitiveArray.length);
        assertEquals(3, booleanPropArray.length);
        assertEquals(3, stringPropArray.length);

        assertEquals(0, intPropPrimitiveArray[1]);
        assertEquals(null, intPropArray[1]);
        assertEquals(0., doublePropPrimitiveArray[1], delta);
        assertEquals(null, doublePropArray[1]);
        assertEquals(0., floatPropPrimitiveArray[1], delta);
        assertEquals(null, floatPropArray[1]);
        assertEquals(false, booleanPropPrimitiveArray[1]);
        assertEquals(null, booleanPropArray[1]);
        assertEquals(null, stringPropArray[1]);

    }

    @Ignore
    @Test
    public void testDeserialize_NonSerializableArrays_Null() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{" +
                "\"intPropPrimitiveArray\" : [1, null, 2]," +
                "\"intPropArray\" : [1, null, 2]," +
                "\"stringPropArray\" : [\"text1\", null, \"text2\"]," +
                "\"doublePropPrimitiveArray\" : [1.5, null, 2.5]," +
                "\"doublePropArray\" : [1.5, null, 2.5]," +
                "\"floatPropPrimitiveArray\" : [1.5, null, 2.5]," +
                "\"floatPropArray\" : [1.5, null, 2.5]," +
                "\"booleanPropPrimitiveArray\" : [true, null, true]," +
                "\"booleanPropArray\" : [true, null, true]" +
                "}";

        double delta = 1e-15;

        NonSerializableArrays obj = Unmarshaller.unmarshal(json, NonSerializableArrays.class);

        assertNotNull(obj);

        int[] intPropPrimitiveArray = obj.getIntPropPrimitiveArray();
        Integer[] intPropArray = obj.getIntPropArray();
        double[] doublePropPrimitiveArray = obj.getDoublePropPrimitiveArray();
        Double[] doublePropArray = obj.getDoublePropArray();
        float[] floatPropPrimitiveArray = obj.getFloatPropPrimitiveArray();
        Float[] floatPropArray = obj.getFloatPropArray();
        boolean[] booleanPropPrimitiveArray = obj.getBooleanPropPrimitiveArray();
        Boolean[] booleanPropArray = obj.getBooleanPropArray();
        String[] stringPropArray = obj.getStringPropArray();

        assertNotNull(intPropPrimitiveArray);
        assertNotNull(intPropArray);
        assertNotNull(doublePropPrimitiveArray);
        assertNotNull(doublePropArray);
        assertNotNull(floatPropPrimitiveArray);
        assertNotNull(floatPropArray);
        assertNotNull(booleanPropPrimitiveArray);
        assertNotNull(booleanPropArray);
        assertNotNull(stringPropArray);

        assertEquals(2, intPropPrimitiveArray.length);
        assertEquals(2, intPropArray.length);
        assertEquals(2, doublePropPrimitiveArray.length);
        assertEquals(2, doublePropArray.length);
        assertEquals(2, floatPropPrimitiveArray.length);
        assertEquals(2, floatPropArray.length);
        assertEquals(2, booleanPropPrimitiveArray.length);
        assertEquals(2, booleanPropArray.length);
        assertEquals(2, stringPropArray.length);
    }

    @Ignore
    @Test
    public void testDeserialize_EmptyObjects1() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{}";
        double delta = 1e-15;

        SimpleObject obj = Unmarshaller.unmarshal(json, SimpleObject.class);
        assertNotNull(obj);

        assertEquals(0, obj.getIntPropPrimitive());
        assertEquals(0., obj.getDoublePropPrimitive(), delta);
        assertEquals(0., obj.getFloatPropPrimitive(), delta);
        assertEquals(false, obj.isBooleanPropPrimitive());
        assertEquals(null, obj.getStringProp());
        assertEquals(null, obj.getIntProp());
        assertEquals(null, obj.getDoubleProp());
        assertEquals(null, obj.getFloatProp());
        assertEquals(null, obj.getBooleanProp());
    }

    @Ignore
    @Test
    public void testDeserialize_EmptyObjects2() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{" +
                "\"innerSimpleObject\" : {}," +
                "\"arraySimpleObject\" : []" +
                "}";
        double delta = 1e-15;

        ComplexObject obj = Unmarshaller.unmarshal(json, ComplexObject.class);

        assertNotNull(obj);

        assertNotNull(obj.getInnerSimpleObject());
        assertNotNull(obj.getArraySimpleObject());

        assertEquals(0, obj.getIntPropPrimitive());
        assertEquals(0., obj.getDoublePropPrimitive(), delta);
        assertEquals(0., obj.getFloatPropPrimitive(), delta);
        assertEquals(false, obj.isBooleanPropPrimitive());
        assertEquals(null, obj.getStringProp());
        assertEquals(null, obj.getIntProp());
        assertEquals(null, obj.getDoubleProp());
        assertEquals(null, obj.getFloatProp());
        assertEquals(null, obj.getBooleanProp());
    }

    @Ignore
    @Test
    public void testDeserialize_EmptyArrays1() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {
        String json = "{" +
                "\"intPropPrimitiveArray\" : []," +
                "\"intPropArray\" : []," +
                "\"stringPropArray\" : []," +
                "\"doublePropPrimitiveArray\" : []," +
                "\"doublePropArray\" : []," +
                "\"floatPropPrimitiveArray\" : []," +
                "\"floatPropArray\" : []," +
                "\"booleanPropPrimitiveArray\" : []," +
                "\"booleanPropArray\" : []," +
                "}";
        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
        assertNotNull(obj);

        assertNotNull(obj.getIntPropArray());
        assertNotNull(obj.getIntPropPrimitiveArray());
        assertNotNull(obj.getDoublePropArray());
        assertNotNull(obj.getDoublePropPrimitiveArray());
        assertNotNull(obj.getFloatPropArray());
        assertNotNull(obj.getFloatPropPrimitiveArray());
        assertNotNull(obj.getBooleanPropArray());
        assertNotNull(obj.getBooleanPropPrimitiveArray());
        assertNotNull(obj.getStringPropArray());

        assertEquals(0, obj.getIntPropPrimitive());
        assertEquals(0., obj.getDoublePropPrimitive(), delta);
        assertEquals(0., obj.getFloatPropPrimitive(), delta);
        assertEquals(false, obj.isBooleanPropPrimitive());
        assertEquals(null, obj.getStringProp());
        assertEquals(null, obj.getIntProp());
        assertEquals(null, obj.getDoubleProp());
        assertEquals(null, obj.getFloatProp());
        assertEquals(null, obj.getBooleanProp());
    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_String() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"stringPropArray\" : [\"text1\", \"\", 556, 22.5, false]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Integer() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"intPropArray\" : [1, 2.5, \"text1\", true]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Boolean() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"booleanPropArray\" : [true, \"false\", 6, 2.5]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Double() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"doublePropArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Float() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"floatPropArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_IntegerPrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"intPropPrimitiveArray\" : [1, 2.5, \"text1\", true]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_BooleanPrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"booleanPropPrimitiveArray\" : [true, \"false\", 6, 2.5]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_DoublePrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"doublePropPrimitiveArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

    @Ignore
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_FloatPrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"floatPropPrimitiveArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        double delta = 1e-15;

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);

    }

//    @Ignore
    @Test
    public void testDeserialize_MultiDimensional() throws UnmarshallerException,
            InstantiationException, IllegalAccessException {

        String json = "{" +
                "\"multiString\" : [ [\"text1\"] , [\"text2\"] ]" +
                "}";

        double delta = 1e-15;

        MultiDimensionalArrays obj = Unmarshaller.unmarshal(json, MultiDimensionalArrays.class);

    }
}
