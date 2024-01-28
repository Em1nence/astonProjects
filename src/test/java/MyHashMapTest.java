
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyHashMapTest {

    @Test
    public void testPutAndGet() {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        myMap.put("one", 1);
        myMap.put("two", 2);
        myMap.put("three", 3);

        Assertions.assertEquals(1, myMap.get("one").intValue());
        Assertions.assertEquals(2, myMap.get("two").intValue());
        Assertions.assertEquals(3, myMap.get("three").intValue());
    }

    @Test
    public void testRemove() {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        myMap.put("one", 1);
        myMap.put("two", 2);
        myMap.put("three", 3);

        myMap.remove("two");
        Assertions.assertNull(myMap.get("two"));
        Assertions.assertEquals(2, myMap.size());
    }

    @Test
    public void testContainsKey() {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        myMap.put("one", 1);
        myMap.put("two", 2);

        Assertions.assertTrue(myMap.containsKey("one"));
        Assertions.assertFalse(myMap.containsKey("three"));
    }

    @Test
    public void testSize() {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();
        Assertions.assertEquals(0, myMap.size());

        myMap.put("one", 1);
        Assertions.assertEquals(1, myMap.size());

        myMap.remove("one");
        Assertions.assertEquals(0, myMap.size());
    }

    @Test
    public void testLargeData() {
        MyHashMap<Integer, String> myMap = new MyHashMap<>();
        int dataSize = 100000;

        // Вставляем большое количество данных
        for (int i = 0; i < dataSize; i++) {
            myMap.put(i, "Value" + i);
        }

        // Проверяем, что данные успешно добавлены
        Assertions.assertEquals(dataSize, myMap.size());

        // Проверяем, что мы можем получить значения
        for (int i = 0; i < dataSize; i++) {
            Assertions.assertEquals("Value" + i, myMap.get(i));
        }

        // Проверяем, что размер не изменился после чтения
        Assertions.assertEquals(dataSize, myMap.size());
    }
}
