package aston.task1;

import java.util.Objects;

public class CustomHashMap<K, V> implements CustomMap<K, V> {
  private static final int CAPACITY = 16;
  private Entry<K, V>[] table;
  private int size;

  @SuppressWarnings("unchecked")
  public CustomHashMap() {
    table = (Entry<K, V>[]) new Entry[CAPACITY];
    size = 0;
  }

  @Override
  public void put(K key, V value) {
    int index = indexFor(hash(key));
    Entry<K, V> current = table[index];

    while (current != null) {
      if (Objects.equals(current.key, key)) {
        current.value = value;
        return;
      }
      current = current.next;
    }

    Entry<K, V> newEntry = new Entry<>(key, value);
    newEntry.next = table[index];
    table[index] = newEntry;
    size++;
  }

  @Override
  public V get(K key) {
    int index = indexFor(hash(key));
    Entry<K, V> current = table[index];

    while (current != null) {
      if (Objects.equals(current.key, key)) {
        return current.value;
      }
      current = current.next;
    }
    return null;
  }

  @Override
  public void remove(K key) {
    int index = indexFor(hash(key));
    Entry<K, V> current = table[index];
    Entry<K, V> prev = null;

    while (current != null) {
      if (Objects.equals(current.key, key)) {
        if (prev == null) {
          table[index] = current.next;
        } else {
          prev.next = current.next;
        }
        size--;
        return;
      }
      prev = current;
      current = current.next;
    }
  }

  @Override
  public int size() {
    return size;
  }

  private static class Entry<K, V> {
    K key;
    V value;
    Entry<K, V> next;

    Entry(K key, V value) {
      this.key = key;
      this.value = value;
      this.next = null;
    }
  }

  int hash(K key) {
    return (key == null) ? 0 : key.hashCode();
  }

  private int indexFor(int hash) {
    return Math.floorMod(hash, table.length);
  }
}
