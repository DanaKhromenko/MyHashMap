import collection.impl.MyHashMap;
import collection.MyMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;

public class MyHashMapTest {
    private static MyMap<Long, String> map;

    @Before
    public void setUp() {
        map = new MyHashMap<>();
    }

    @Test
    public void getByNonExistedKey() {
        Assert.assertNull("Test failed! If key doesn't exist, we should return null.",
                map.getValue(100L));
    }

    @Test
    public void putAndGetOk() {
        map.put(1L, "FIRST");
        map.put(2L, "SECOND");
        map.put(3L, "THIRD");

        Assert.assertEquals("Test failed! The size isn't correct. Expected 3 but was "
                + map.getSize(), 3, map.getSize());

        String firstActualValue = map.getValue(1L);
        String secondActualValue = map.getValue(2L);
        String thirdActualValue = map.getValue(3L);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'FIRST' for key 1L,"
                + " but was " + firstActualValue, "FIRST", firstActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'SECOND' for key 2L,"
                + " but was " + secondActualValue, "SECOND", secondActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'THIRD' for key 3L,"
                + " but was " + thirdActualValue, "THIRD", thirdActualValue);
    }

    @Test
    public void putTheSameElement() {
        map.put(1L, "ONE");
        map.put(2L, "TWO");
        map.put(3L, "THREE");
        map.put(1L, "ONE");
        map.put(2L, "TWO");
        map.put(3L, "THREE");

        Assert.assertEquals("Test failed! We should add checking if the same element "
                + "exists in the map", 3, map.getSize());

        String firstActualValue = map.getValue(1L);
        String secondActualValue = map.getValue(2L);
        String thirdActualValue = map.getValue(3L);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'ONE' for key 1L`,"
                + " but was " + firstActualValue, "ONE", firstActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'TWO' for key 2L,"
                + " but was " + secondActualValue, "TWO", secondActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'THREE' for key 3L,"
                + " but was " + thirdActualValue, "THREE", thirdActualValue);
    }

    @Test
    public void putAndGetByNullKey() {
        map.put(null, "FIRST");
        String firstActualValue = map.getValue(null);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'FIRST' for null key,"
                + " but was " + firstActualValue, "FIRST", firstActualValue);
        Assert.assertEquals("Test failed! The size isn't correct. Expected 1 but was "
                + map.getSize(), 1, map.getSize());

        map.put(null, "SECOND");
        String secondActualValue = map.getValue(null);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'SECOND' for null key,"
                + " but was " + secondActualValue, "SECOND", secondActualValue);
        Assert.assertEquals("Test failed! The size isn't correct. Expected 1 but was "
                + map.getSize(), 1, map.getSize());
    }

    @Test
    public void putAndGetByNullKeyWithCollision() {
        map.put(1L, "FIRST");
        map.put(null, "NULL #1");
        map.put(2L, "SECOND");
        map.put(null, "NULL #2");
        map.put(3L, "THIRD");

        Assert.assertEquals("Test failed! The size isn't correct. Expected 4 but was "
                + map.getSize(), 4, map.getSize());

        String firstActualValue = map.getValue(1L);
        String secondActualValue = map.getValue(2L);
        String thirdActualValue = map.getValue(3L);
        String fourthActualValue = map.getValue(null);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'FIRST' for key 1L,"
                + " but was " + firstActualValue, "FIRST", firstActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'SECOND' for key 2L,"
                + " but was " + secondActualValue, "SECOND", secondActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'THIRD' for key 3L,"
                + " but was " + thirdActualValue, "THIRD", thirdActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'NULL #2' for null key,"
                + " but was " + fourthActualValue, "NULL #2", fourthActualValue);
    }

    @Test
    public void putAndGetTheOverriddenValueByKey() {
        map.put(1L, "ONE");
        map.put(2L, "TWO");
        map.put(3L, "THREE");

        map.put(1L, "FIRST");
        map.put(2L, "SECOND");
        map.put(3L, "THIRD");

        Assert.assertEquals("Test failed! We should add checking if the same element "
                + "exists in the map", 3, map.getSize());

        String firstActualValue = map.getValue(1L);
        String secondActualValue = map.getValue(2L);
        String thirdActualValue = map.getValue(3L);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'FIRST' for key 1L`,"
                + " but was " + firstActualValue, "FIRST", firstActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'SECOND' for key 2L,"
                + " but was " + secondActualValue, "SECOND", secondActualValue);
        Assert.assertEquals("Test failed! HashMap expects to contain value 'THIRD' for key 3L,"
                + " but was " + thirdActualValue, "THIRD", thirdActualValue);
    }

    @Test
    public void checkTheHashMapIncrease() {
        for (int i = 0; i < 1000; i++) {
            String word = "word_" + i;
            map.put((long) i, word);
        }
        Assert.assertEquals("Test failed! The size isn't correct. Expected 1000 but was "
                + map.getSize(), 1000, map.getSize());
        for (int i = 0; i < 1000; i++) {
            Assert.assertEquals("word_" + i,
                    map.getValue((long) i));
        }
    }

    @Test
    public void getSizeOfEmptyHashMap() {
        Assert.assertEquals("Test failed! The size isn't correct. Expected 0 but was "
                + map.getSize(), 0, map.getSize());
    }

    @Test
    public void existOnlyOneArrayFieldTest() {
        Field[] declaredFields = map.getClass().getDeclaredFields();
        int count = 0;
        for (Field field : declaredFields) {
            if (field.getType().isArray()) {
                count++;
            }
        }
        Assert.assertEquals("Class MyHashMap shouldn't consist more then one array as a field",
                1, count);
    }
}
