package aston.task1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomHashMapTest {

  @Test
  void whenPutValuesToHashMap_theGetValuesByKeys() {
    CustomMap<String, Integer> map = new CustomHashMap<>();

    map.put("keyA", 10);
    map.put("keyB", 20);

    assertEquals(10, map.get("keyA"));
    assertEquals(20, map.get("keyB"));
  }

  @Test
  void whenPutValuesWithEqualKey_thenGetOverwriteValue() {
    CustomMap<String, Integer> map = new CustomHashMap<>();

    map.put("keyA", 10);
    map.put("keyA", 20);

    assertEquals(20, map.get("keyA"));
  }

  @Test
  void whenRemoveValueFromHashMap_theGetMapDecreasedOnOne() {
    CustomMap<String, Integer> map = new CustomHashMap<>();

    map.put("keyA", 10);
    map.put("keyB", 20);

    assertEquals(2, map.size());
    map.remove("keyA");
    Assertions.assertNull(map.get("keyA"));
    assertEquals(1, map.size());
  }

  @Test
  void whenCollisionHappened_thenValuesPutAndGetCorrectly() {
    CustomHashMap<String, Integer> map =
        new CustomHashMap() {
          @Override
          int hash(Object key) {
            return 1;
          }
        };

    map.put("keyA", 10);
    map.put("keyB", 20);

    assertEquals(10, map.get("keyA"));
    assertEquals(20, map.get("keyB"));

    map.remove("keyA");
    Assertions.assertNull(map.get("keyA"));
    assertEquals(20, map.get("keyB"));
  }
}
