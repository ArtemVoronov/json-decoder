package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Тесты для "интерпретатора" Unmarshaller
 */
public class UnmarshallerTest {

    /**
     * проверяем создание несложного объекта
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_SimpleProps() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверям создание объекта с вложенными объектами и неизвестным полем
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_InnerObjectProp() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверям создание объекта с массивами
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_IntArrayProp() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверям создание объекта со всеми базовыми типами
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_SimpleObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверям создание объекта с вложенными обьъеком, массивом из двух объектов, каждый из которых
     * содержит переменные базовых типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_ComplexObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

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

    /**
     * проверям создание объекта с вложенными обьъеком, массивом из двух объектов, каждый из которых
     * содержит ещё одив вложенный объект и массив из двух объектов, которые в свою очередь хранят
     * переменные базовых типов (~тройная вложенность)
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_TheMostComplexObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверяем создание объекта со переменными базовых типов, при условии что у входной
     * JSON-строки все поля имеют значение null
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_SimpleObject_Null() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверям создание объекта у которого полям являются массивы базовых типов, и каждый из них
     * сериализует значение null (@JSONProperty.serializeIfNull() == true)
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_SerializableArrays_Null() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверям создание объекта у которого полям являются массивы базовых типов, и каждый из них
     * НЕ сериализует значение null (@JSONProperty.serializeIfNull() == false)
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_NonSerializableArrays_Null() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверяем создание пустого объекта
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_EmptyObjects1() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверяем создание пустых объектов типа SimpleObject (в JSON-строке пустой массив и пустой объект)
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_EmptyObjects2() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

    /**
     * проверяем создание пустых массиов базовых типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_EmptyArrays1() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
        String json = "{" +
                "\"intPropPrimitiveArray\" : []," +
                "\"intPropArray\" : []," +
                "\"stringPropArray\" : []," +
                "\"doublePropPrimitiveArray\" : []," +
                "\"doublePropArray\" : []," +
                "\"floatPropPrimitiveArray\" : []," +
                "\"floatPropArray\" : []," +
                "\"booleanPropPrimitiveArray\" : []," +
                "\"booleanPropArray\" : []" +
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

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении 556, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_String1() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"stringPropArray\" : [\"text1\", \"\", 556, 22.5, false]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении 5, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_String2() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"stringPropArray\" : [\"text1\", 5]" +
                "}";


        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении without_quotes, должно выброситься LexerException т.к. неизвестная лексема
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=LexerException.class)
    public void testDeserialize_Exception_TypeMismatch_String3() throws LexerException,
            InstantiationException, IllegalAccessException, UnmarshallerException, ParserException {

        String json = "{" +
                "\"stringPropArray\" : [\"text1\", without_quotes]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении 2.5, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Integer() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"intPropArray\" : [1, 2.5, \"text1\", true]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении "false", должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Boolean() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"booleanPropArray\" : [true, \"false\", 6, 2.5]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении true, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Double() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"doublePropArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении true, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_Float() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"floatPropArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении 2.5, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_IntegerPrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"intPropPrimitiveArray\" : [1, 2.5, \"text1\", true]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении "false", должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_BooleanPrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"booleanPropPrimitiveArray\" : [true, \"false\", 6, 2.5]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении true, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_DoublePrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"doublePropPrimitiveArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * * проверяем корректность работы при несоответствии типов Java-объекта и  данных из JSON-строки на входе:
     * на значении true, должно выброситься UnmarshallerException из-за несовпадения типов
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test (expected=UnmarshallerException.class)
    public void testDeserialize_Exception_TypeMismatch_FloatPrimitive() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"floatPropPrimitiveArray\" : [5.6, true, \"false\", 6, 2.5]" +
                "}";

        SerializableArrays obj = Unmarshaller.unmarshal(json, SerializableArrays.class);
    }

    /**
     * проверяем создние объекта с двумерными массивами
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_TwoDimensional() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"twoDimString\" : [ [\"text1\", \"text2\"] , [\"text3\", \"text4\"] ]," +
                "\"twoDimIntPrimitive\" : [ [1, 2] , [3, 4] ]," +
                "\"twoDimBooleanPrimitive\" : [ [true, true] , [true, true] ]," +
                "\"twoDimDoublePrimitive\" : [ [1.5, 2.5] , [3.5, 4.5] ]," +
                "\"twoDimFloatPrimitive\" :[ [1.5, 2.5] , [3.5, 4.5] ]," +
                "\"twoDimInt\" : [ [11, 12] , [13, 14] ]," +
                "\"twoDimBoolean\" :  [ [true, true] , [true, true] ]," +
                "\"twoDimDouble\" : [ [11.5, 12.5] , [13.5, 14.5] ]," +
                "\"twoDimFloat\" : [ [11.5, 12.5] , [13.5, 14.5] ]," +
                "\"twoDimSimpleObject\" : [ " +
                    "[{\"stringProp\":\"stringValue\"}] , " +
                    "[{\"intPropPrimitive\":1}] " +
                  "]" +
                "}";
        double delta = 1e-15;

        TwoDimensionalArrays obj = Unmarshaller.unmarshal(json, TwoDimensionalArrays.class);

        assertNotNull(obj);

        int[][] twoDimIntPrimitive = obj.getTwoDimIntPrimitive();
        Integer[][] twoDimInt = obj.getTwoDimInt();
        double[][] twoDimDoublePrimitive = obj.getTwoDimDoublePrimitive();
        Double[][] twoDimDouble = obj.getTwoDimDouble();
        float[][] twoDimFloatPrimitive = obj.getTwoDimFloatPrimitive();
        Float[][] twoDimFloat = obj.getTwoDimFloat();
        boolean[][] twoDimBooleanPrimitive = obj.getTwoDimBooleanPrimitive();
        Boolean[][] twoDimBoolean = obj.getTwoDimBoolean();
        String[][] twoDimString = obj.getTwoDimString();
        SimpleObject[][] twoDimSimpleObject = obj.getTwoDimSimpleObject();

        assertNotNull(twoDimIntPrimitive);
        assertNotNull(twoDimInt);
        assertNotNull(twoDimDoublePrimitive);
        assertNotNull(twoDimDouble);
        assertNotNull(twoDimFloatPrimitive);
        assertNotNull(twoDimFloat);
        assertNotNull(twoDimBooleanPrimitive);
        assertNotNull(twoDimBoolean);
        assertNotNull(twoDimString);
        assertNotNull(twoDimSimpleObject);

        assertEquals(2, twoDimIntPrimitive.length);
        assertEquals(2, twoDimInt.length);
        assertEquals(2, twoDimDoublePrimitive.length);
        assertEquals(2, twoDimDouble.length);
        assertEquals(2, twoDimFloatPrimitive.length);
        assertEquals(2, twoDimFloat.length);
        assertEquals(2, twoDimBooleanPrimitive.length);
        assertEquals(2, twoDimBoolean.length);
        assertEquals(2, twoDimString.length);
        assertEquals(2, twoDimSimpleObject.length);

        assertEquals("text1", obj.getTwoDimString()[0][0]);
        assertEquals("text2", obj.getTwoDimString()[0][1]);
        assertEquals("text3", obj.getTwoDimString()[1][0]);
        assertEquals("text4", obj.getTwoDimString()[1][1]);

        assertEquals(1, obj.getTwoDimIntPrimitive()[0][0]);
        assertEquals(2, obj.getTwoDimIntPrimitive()[0][1]);
        assertEquals(3, obj.getTwoDimIntPrimitive()[1][0]);
        assertEquals(4, obj.getTwoDimIntPrimitive()[1][1]);

        assertEquals(new Integer(11), obj.getTwoDimInt()[0][0]);
        assertEquals(new Integer(12), obj.getTwoDimInt()[0][1]);
        assertEquals(new Integer(13), obj.getTwoDimInt()[1][0]);
        assertEquals(new Integer(14), obj.getTwoDimInt()[1][1]);

        assertEquals(1.5, obj.getTwoDimDoublePrimitive()[0][0], delta);
        assertEquals(2.5, obj.getTwoDimDoublePrimitive()[0][1], delta);
        assertEquals(3.5, obj.getTwoDimDoublePrimitive()[1][0], delta);
        assertEquals(4.5, obj.getTwoDimDoublePrimitive()[1][1], delta);

        assertEquals(new Double(11.5), obj.getTwoDimDouble()[0][0]);
        assertEquals(new Double(12.5), obj.getTwoDimDouble()[0][1]);
        assertEquals(new Double(13.5), obj.getTwoDimDouble()[1][0]);
        assertEquals(new Double(14.5), obj.getTwoDimDouble()[1][1]);

        assertEquals(1.5, obj.getTwoDimFloatPrimitive()[0][0], delta);
        assertEquals(2.5, obj.getTwoDimFloatPrimitive()[0][1], delta);
        assertEquals(3.5, obj.getTwoDimFloatPrimitive()[1][0], delta);
        assertEquals(4.5, obj.getTwoDimFloatPrimitive()[1][1], delta);

        assertEquals(new Float(11.5), obj.getTwoDimFloat()[0][0]);
        assertEquals(new Float(12.5), obj.getTwoDimFloat()[0][1]);
        assertEquals(new Float(13.5), obj.getTwoDimFloat()[1][0]);
        assertEquals(new Float(14.5), obj.getTwoDimFloat()[1][1]);

        assertEquals(true, obj.getTwoDimBooleanPrimitive()[0][0]);
        assertEquals(true, obj.getTwoDimBooleanPrimitive()[0][1]);
        assertEquals(true, obj.getTwoDimBooleanPrimitive()[1][0]);
        assertEquals(true, obj.getTwoDimBooleanPrimitive()[1][1]);

        assertEquals(true, obj.getTwoDimBoolean()[0][0]);
        assertEquals(true, obj.getTwoDimBoolean()[0][1]);
        assertEquals(true, obj.getTwoDimBoolean()[1][0]);
        assertEquals(true, obj.getTwoDimBoolean()[1][1]);

        SimpleObject first = obj.getTwoDimSimpleObject()[0][0];
        SimpleObject second = obj.getTwoDimSimpleObject()[1][0];
        assertNotNull(first);
        assertNotNull(second);
        assertEquals("stringValue", first.getStringProp());
        assertEquals(1, second.getIntPropPrimitive());

    }

    /**
     * проверяем создние объекта с трёхмерными массивами
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_MultiDimensional() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

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

        String json = "{" +
                "\"multiDimString\" : [ [ [ \"text1\" ] ] ]," +
                "\"multiDimIntPrimitive\" : [ [ [ 1 ] ] ]," +
                "\"multiDimBooleanPrimitive\" : [ [ [ true ] ] ]," +
                "\"multiDimDoublePrimitive\" : [ [ [ 1.5 ] ] ]," +
                "\"multiDimFloatPrimitive\" :[ [ [ 1.5 ] ] ]," +
                "\"multiDimInt\" : [ [ [ 11 ] ] ]," +
                "\"multiDimBoolean\" :  [ [ [ true ] ] ]," +
                "\"multiDimDouble\" : [ [ [ 11.5 ] ] ]," +
                "\"multiDimFloat\" : [ [ [ 11.5 ] ] ]," +
                "\"multiDimSimpleObject\" : [ [ [ " + jsonSimpleObject +" ] ] ]" +
                "}";

        double delta = 1e-15;

        MultiDimensionalArrays obj = Unmarshaller.unmarshal(json, MultiDimensionalArrays.class);

        assertNotNull(obj);

        int[][][] multiDimIntPrimitive = obj.getMultiDimIntPrimitive();
        Integer[][][] multiDimInt = obj.getMultiDimInt();
        double[][][] multiDimDoublePrimitive = obj.getMultiDimDoublePrimitive();
        Double[][][] multiDimDouble = obj.getMultiDimDouble();
        float[][][] multiDimFloatPrimitive = obj.getMultiDimFloatPrimitive();
        Float[][][] multiDimFloat = obj.getMultiDimFloat();
        boolean[][][] multiDimBooleanPrimitive = obj.getMultiDimBooleanPrimitive();
        Boolean[][][] multiDimBoolean = obj.getMultiDimBoolean();
        String[][][] multiDimString = obj.getMultiDimString();
        SimpleObject[][][] multiDimSimpleObject = obj.getMultiDimSimpleObject();

        assertNotNull(multiDimIntPrimitive);
        assertNotNull(multiDimInt);
        assertNotNull(multiDimDoublePrimitive);
        assertNotNull(multiDimDouble);
        assertNotNull(multiDimFloatPrimitive);
        assertNotNull(multiDimFloat);
        assertNotNull(multiDimBooleanPrimitive);
        assertNotNull(multiDimBoolean);
        assertNotNull(multiDimString);
        assertNotNull(multiDimSimpleObject);

        assertEquals(1, multiDimIntPrimitive.length);
        assertEquals(1, multiDimInt.length);
        assertEquals(1, multiDimDoublePrimitive.length);
        assertEquals(1, multiDimDouble.length);
        assertEquals(1, multiDimFloatPrimitive.length);
        assertEquals(1, multiDimFloat.length);
        assertEquals(1, multiDimBooleanPrimitive.length);
        assertEquals(1, multiDimBoolean.length);
        assertEquals(1, multiDimString.length);
        assertEquals(1, multiDimSimpleObject.length);

        assertEquals(1, obj.getMultiDimIntPrimitive()[0][0][0]);
        assertEquals(new Integer(11), obj.getMultiDimInt()[0][0][0]);
        assertEquals(1.5, obj.getMultiDimDoublePrimitive()[0][0][0], delta);
        assertEquals(new Double(11.5), obj.getMultiDimDouble()[0][0][0]);
        assertEquals(1.5, obj.getMultiDimFloatPrimitive()[0][0][0], delta);
        assertEquals(new Float(11.5), obj.getMultiDimFloat()[0][0][0]);
        assertEquals(true, obj.getMultiDimBooleanPrimitive()[0][0][0]);
        assertEquals(true, obj.getMultiDimBoolean()[0][0][0]);
        assertEquals("text1", obj.getMultiDimString()[0][0][0]);

        SimpleObject simpleObject = multiDimSimpleObject[0][0][0];

        assertNotNull(simpleObject);
        assertEquals("stringValue", simpleObject.getStringProp());
        assertEquals(1, simpleObject.getIntPropPrimitive());
        assertEquals(new Integer(2), simpleObject.getIntProp());
        assertEquals(3.5, simpleObject.getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), simpleObject.getDoubleProp(), delta);
        assertEquals(5.5, simpleObject.getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), simpleObject.getFloatProp(), delta);
        assertEquals(true, simpleObject.getBooleanProp());
        assertEquals(true, simpleObject.isBooleanPropPrimitive());
    }

    /**
     * проверяем инициализацию полей при наследовании
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_Hierarchy() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
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

        TheMostComplexObject obj = Unmarshaller.unmarshal(json, TheMostComplexObject.class);
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
        assertNull(obj.getArrayComplexObject());
        assertNull(obj.getArraySimpleObject());
        assertNull(obj.getInnerComplexObject());
        assertNull(obj.getInnerSimpleObject());
    }

    /**
     * проверяем инициализацию полей, которые не объявлены в Java-объекте
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_SimpleObject_MissedFields() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
        String json = "{" +
                "\"stringProp_unknown\":\"stringValue\"," +
                "\"intPropPrimitive_unknown\":1," +
                "\"intProp_unknown\":2," +
                "\"doublePropPrimitive_unknown\":3.5," +
                "\"doubleProp_unknown\":4.5," +
                "\"floatPropPrimitive_unknown\":5.5," +
                "\"floatProp_unknown\":6.5," +
                "\"booleanPropPrimitive_unknown\":true,"+
                "\"booleanProp_unknown\":true"+
                "}";
        double delta = 1e-15;

        SimpleObject obj = Unmarshaller.unmarshal(json, SimpleObject.class);
        assertNotNull(obj);
        assertEquals(null, obj.getStringProp());
        assertEquals(0, obj.getIntPropPrimitive());
        assertEquals(null, obj.getIntProp());
        assertEquals(0., obj.getDoublePropPrimitive(), delta);
        assertEquals(null, obj.getDoubleProp());
        assertEquals(0., obj.getFloatPropPrimitive(), delta);
        assertEquals(null, obj.getFloatProp());
        assertEquals(null, obj.getBooleanProp());
        assertEquals(false, obj.isBooleanPropPrimitive());
    }

    /**
     * проверяем создание объекта с полями public
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_ObjectWithPublicFields() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intPropPrimitive\":1," +
                "\"intProp\":2," +
                "\"doublePropPrimitive\":3.5," +
                "\"doubleProp\":4.5," +
                "\"floatPropPrimitive\":5.5," +
                "\"floatProp\":6.5," +
                "\"booleanPropPrimitive\":true,"+
                "\"booleanProp\":true,"+
                "\"simpleObject\": {\"stringProp\":\"stringValue\"}"+
                "}";
        double delta = 1e-15;

        ObjectWithPublicFields obj = Unmarshaller.unmarshal(json, ObjectWithPublicFields.class);
        assertNotNull(obj);

        SimpleObject sob = obj.simpleObject;

        assertEquals("stringValue", obj.stringProp);
        assertEquals(1, obj.intPropPrimitive);
        assertEquals(new Integer(2), obj.intProp);
        assertEquals(3.5, obj.doublePropPrimitive, delta);
        assertEquals(new Double(4.5), obj.doubleProp, delta);
        assertEquals(5.5, obj.floatPropPrimitive, delta);
        assertEquals(new Float(6.5), obj.floatProp, delta);
        assertEquals(true, obj.booleanProp);
        assertEquals(true, obj.booleanPropPrimitive);
        assertEquals("stringValue", sob.getStringProp());

//        assertEquals("stringValue", obj.getStringProp());
//        assertEquals(1, obj.getIntPropPrimitive());
//        assertEquals(new Integer(2), obj.getIntProp());
//        assertEquals(3.5, obj.getDoublePropPrimitive(), delta);
//        assertEquals(new Double(4.5), obj.getDoubleProp(), delta);
//        assertEquals(5.5, obj.getFloatPropPrimitive(), delta);
//        assertEquals(new Float(6.5), obj.getFloatProp(), delta);
//        assertEquals(true, obj.getBooleanProp());
//        assertEquals(true, obj.isBooleanPropPrimitive());
    }

    /**
     * проверяем создание объекта с полями protected
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_ObjectWithProtectedFields() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intPropPrimitive\":1," +
                "\"intProp\":2," +
                "\"doublePropPrimitive\":3.5," +
                "\"doubleProp\":4.5," +
                "\"floatPropPrimitive\":5.5," +
                "\"floatProp\":6.5," +
                "\"booleanPropPrimitive\":true,"+
                "\"booleanProp\":true,"+
                "\"simpleObject\": {\"stringProp\":\"stringValue\"}"+
                "}";
        double delta = 1e-15;

        ObjectWithProtectedFields obj = Unmarshaller.unmarshal(json, ObjectWithProtectedFields.class);
        assertNotNull(obj);

        SimpleObject sob = obj.getSimpleObject();

        assertEquals("stringValue", obj.getStringProp());
        assertEquals(1, obj.getIntPropPrimitive());
        assertEquals(new Integer(2), obj.getIntProp());
        assertEquals(3.5, obj.getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), obj.getDoubleProp(), delta);
        assertEquals(5.5, obj.getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), obj.getFloatProp(), delta);
        assertEquals(true, obj.getBooleanProp());
        assertEquals(true, obj.isBooleanPropPrimitive());
        assertEquals("stringValue", sob.getStringProp());
    }

    /**
     * проверяем создание объекта с полями private
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_ObjectWithPrivateFields() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {
        String json = "{" +
                "\"stringProp\":\"stringValue\"," +
                "\"intPropPrimitive\":1," +
                "\"intProp\":2," +
                "\"doublePropPrimitive\":3.5," +
                "\"doubleProp\":4.5," +
                "\"floatPropPrimitive\":5.5," +
                "\"floatProp\":6.5," +
                "\"booleanPropPrimitive\":true,"+
                "\"booleanProp\":true,"+
                "\"simpleObject\": {\"stringProp\":\"stringValue\"}"+
                "}";
        double delta = 1e-15;

        ObjectWithPrivateFields obj = Unmarshaller.unmarshal(json, ObjectWithPrivateFields.class);
        assertNotNull(obj);

        SimpleObject sob = obj.getSimpleObject();

        assertEquals("stringValue", obj.getStringProp());
        assertEquals(1, obj.getIntPropPrimitive());
        assertEquals(new Integer(2), obj.getIntProp());
        assertEquals(3.5, obj.getDoublePropPrimitive(), delta);
        assertEquals(new Double(4.5), obj.getDoubleProp(), delta);
        assertEquals(5.5, obj.getFloatPropPrimitive(), delta);
        assertEquals(new Float(6.5), obj.getFloatProp(), delta);
        assertEquals(true, obj.getBooleanProp());
        assertEquals(true, obj.isBooleanPropPrimitive());
        assertEquals("stringValue", sob.getStringProp());
    }

    /**
     * проверяем инициализацию полей класса Object, преобразования должны быть по принципу:
     * JSON_STRING -> String.class
     * JSON_NUMBER -> Double.class (берем самый "обширный" числовой класс)
     * JSON_TRUE, JSON_FALSE -> Boolean.class
     * JSON_NULL -> null
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_MixedObject() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String jsonString = "{" +
                "\"object\" : \"text1\"" +
                "}";

        String jsonInt = "{" +
                "\"object\" : 1" +
                "}";

        String jsonFractional = "{" +
                "\"object\" : 1.5" +
                "}";

        String jsonBoolean = "{" +
                "\"object\" : true" +
                "}";

        String jsonNull = "{" +
                "\"object\" : null" +
                "}";

        Mixed obj1 = Unmarshaller.unmarshal(jsonString, Mixed.class);
        Mixed obj2 = Unmarshaller.unmarshal(jsonInt, Mixed.class);
        Mixed obj3 = Unmarshaller.unmarshal(jsonFractional, Mixed.class);
        Mixed obj4 = Unmarshaller.unmarshal(jsonBoolean, Mixed.class);
        Mixed obj5 = Unmarshaller.unmarshal(jsonNull, Mixed.class);

        assertNotNull(obj1);
        assertNotNull(obj2);
        assertNotNull(obj3);
        assertNotNull(obj4);
        assertNotNull(obj5);

        assertEquals("text1", obj1.getObject());
        assertEquals(1.0, obj2.getObject());
        assertEquals(1.5, obj3.getObject());
        assertEquals(true, obj4.getObject());
        assertEquals(null, obj5.getObject());
    }

    /**
     * проверяем инициализацию полей класса Object на массиве, преобразования должны быть по принципу:
     * JSON_STRING -> String.class
     * JSON_NUMBER -> Double.class (берем самый "обширный" числовой класс)
     * JSON_TRUE, JSON_FALSE -> Boolean.class
     * JSON_NULL -> null
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_MixedArray() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"array\" : [ \"text\", 11, false, 22.5, true, null]" +
                "}";

        Mixed obj = Unmarshaller.unmarshal(json, Mixed.class);

        assertNotNull(obj);

        assertEquals("text", obj.getArray()[0]);
        assertEquals(11.0, obj.getArray()[1]);
        assertEquals(false, obj.getArray()[2]);
        assertEquals(22.5, obj.getArray()[3]);
        assertEquals(true, obj.getArray()[4]);
        assertEquals(null, obj.getArray()[5]);
    }

    /**
     * проверяем инициализацию полей класса Object на двумерном массиве, преобразования должны быть по принципу:
     * JSON_STRING -> String.class
     * JSON_NUMBER -> Double.class (берем самый "обширный" числовой класс)
     * JSON_TRUE, JSON_FALSE -> Boolean.class
     * JSON_NULL -> null
     * @throws UnmarshallerException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws LexerException
     * @throws ParserException
     */
    @Test
    public void testDeserialize_MixedGrid() throws UnmarshallerException,
            InstantiationException, IllegalAccessException, LexerException, ParserException {

        String json = "{" +
                "\"grid\" : [ " +
                "[\"text1\", 1], " +
                "[1, true], " +
                "[null, 2.5]," +
                "[true, \"text2\"]" +
                "]" +
                "}";
        Mixed obj = Unmarshaller.unmarshal(json, Mixed.class);

        assertNotNull(obj);

        assertEquals("text1", obj.getGrid()[0][0]);
        assertEquals(1., obj.getGrid()[1][0]);
        assertEquals(null, obj.getGrid()[2][0]);
        assertEquals(true, obj.getGrid()[3][0]);

        assertEquals(1., obj.getGrid()[0][1]);
        assertEquals(true, obj.getGrid()[1][1]);
        assertEquals(2.5, obj.getGrid()[2][1]);
        assertEquals("text2", obj.getGrid()[3][1]);
    }
}
