package mobi.eyeline.jsonb;

import mobi.eyeline.jsonb.model.ComplexObject;
import mobi.eyeline.jsonb.model.ManyInnerObjects;
import mobi.eyeline.jsonb.model.SimpleObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Artem Voronov on 12.08.2015.
 */
@RunWith(Parameterized.class)
public class LoadTest {

    private static String jsonSimpleObject;
    private static String JSONWithHugeArray;
    private static String JSONWithManyObjects;
    private static final double delta = 1e-15;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[100][0]);
    }

    public LoadTest() {
    }

    @BeforeClass
    public static void initJSONStrings() {
        jsonSimpleObject = "{" +
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{" );
        stringBuilder.append("\"arraySimpleObject\"");
        stringBuilder.append(":");
        stringBuilder.append("[");
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append(jsonSimpleObject);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        stringBuilder.append("}");
        JSONWithHugeArray = stringBuilder.toString();

        stringBuilder.setLength(0);

        stringBuilder.append("{");
        for (int i = 0; i < 100; i++) {
            stringBuilder.append("\"inner").append(i).append("\"");
            stringBuilder.append(":");
            stringBuilder.append(jsonSimpleObject);
            stringBuilder.append(",");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("}");
        JSONWithManyObjects = stringBuilder.toString();
    }

    @Test(timeout=2000)
    public void testCreatingJSONWithHugeArray() throws IllegalAccessException, UnmarshallerException, InstantiationException {

        ComplexObject obj = Unmarshaller.unmarshal(JSONWithHugeArray, ComplexObject.class);
        assertNotNull(obj);
        assertEquals(1000, obj.getArraySimpleObject().length);

        SimpleObject[] array = obj.getArraySimpleObject();

        for (SimpleObject sob : array) {
            assertNotNull(sob);
            assertEquals("stringValue", sob.getStringProp());
            assertEquals(1, sob.getIntPropPrimitive());
            assertEquals(new Integer(2), sob.getIntProp());
            assertEquals(3.5, sob.getDoublePropPrimitive(), delta);
            assertEquals(new Double(4.5), sob.getDoubleProp(), delta);
            assertEquals(5.5, sob.getFloatPropPrimitive(), delta);
            assertEquals(new Float(6.5), sob.getFloatProp(), delta);
            assertEquals(true, sob.getBooleanProp());
            assertEquals(true, sob.isBooleanPropPrimitive());
        }
    }

    @Test(timeout=2000)
    public void testCreatingJSONWithManyObjects() throws IllegalAccessException,
            UnmarshallerException, InstantiationException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException {

        ManyInnerObjects obj = Unmarshaller.unmarshal(JSONWithManyObjects, ManyInnerObjects.class);
        assertNotNull(obj);

        for (int i = 0; i < 100; i++) {
            Method method = obj.getClass().getMethod("getInner"+i);
            SimpleObject sob = (SimpleObject) method.invoke(obj);
            assertNotNull(sob);
            assertEquals("stringValue", sob.getStringProp());
            assertEquals(1, sob.getIntPropPrimitive());
            assertEquals(new Integer(2), sob.getIntProp());
            assertEquals(3.5, sob.getDoublePropPrimitive(), delta);
            assertEquals(new Double(4.5), sob.getDoubleProp(), delta);
            assertEquals(5.5, sob.getFloatPropPrimitive(), delta);
            assertEquals(new Float(6.5), sob.getFloatProp(), delta);
            assertEquals(true, sob.getBooleanProp());
            assertEquals(true, sob.isBooleanPropPrimitive());
        }
    }
}
