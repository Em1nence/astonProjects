import java.util.LinkedList;

public class MyHashMap<K, V> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private int capacity;
    private int size = 0;
    private LinkedList<Entry<K, V>>[] table;

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
    }

    public void put(K key, V value) {
        if (key == null) {
            putForNullKey(value);
            return;
        }

        int hash = hash(key);
        int index = indexFor(hash, capacity);

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }

        for (Entry<K, V> entry : table[index]) {
            if (key.equals(entry.getKey())) {
                entry.setValue(value);
                return;
            }
        }

        table[index].add(new Entry<>(key, value));
        size++;

        if (size > capacity * LOAD_FACTOR) {
            resize();
        }
    }

    public V get(K key) {
        if (key == null) {
            return getForNullKey();
        }

        int hash = hash(key);
        int index = indexFor(hash, capacity);

        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (key.equals(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    public void remove(K key) {
        if (key == null) {
            removeForNullKey();
            return;
        }

        int hash = hash(key);
        int index = indexFor(hash, capacity);

        if (table[index] != null) {
            table[index].removeIf(entry -> key.equals(entry.getKey()));
            size--;
        }
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public int size() {
        return size;
    }

    private void putForNullKey(V value) {
        if (table[0] == null) {
            table[0] = new LinkedList<>();
        }

        for (Entry<K, V> entry : table[0]) {
            if (entry.getKey() == null) {
                entry.setValue(value);
                return;
            }
        }

        table[0].add(new Entry<>(null, value));
        size++;

        if (size > capacity * LOAD_FACTOR) {
            resize();
        }
    }

    private V getForNullKey() {
        if (table[0] != null) {
            for (Entry<K, V> entry : table[0]) {
                if (entry.getKey() == null) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    private void removeForNullKey() {
        if (table[0] != null) {
            table[0].removeIf(entry -> entry.getKey() == null);
            size--;
        }
    }

    private void resize() {
        int newCapacity = capacity * 2;
        LinkedList<Entry<K, V>>[] newTable = new LinkedList[newCapacity];

        for (LinkedList<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    int hash = hash(entry.getKey());
                    int index = indexFor(hash, newCapacity);

                    if (newTable[index] == null) {
                        newTable[index] = new LinkedList<>();
                    }

                    newTable[index].add(entry);
                }
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    private int hash(K key) {
        return key == null ? 0 : key.hashCode();
    }

    private int indexFor(int hash, int capacity) {
        return hash & (capacity - 1);
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> myMap = new MyHashMap<>();

        myMap.put("one", 1);
        myMap.put("two", 2);
        myMap.put("three", 3);

        System.out.println(myMap.size());
        System.out.println(myMap.get("two"));
        myMap.remove("two");
        System.out.println(myMap.containsKey("two"));
        System.out.println(myMap.size());
    }
}