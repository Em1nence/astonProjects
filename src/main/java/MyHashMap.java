

import java.util.LinkedList;
/**
 * Класс MyHashMap представляет простую реализацию хеш-карты в Java.
 *
 * @author Сайфутдияров Аскар
 * @param <K> Тип ключа.
 * @param <V> Тип значения.
 */
public class MyHashMap<K, V> {
    /**
     * Размер по умолчанию для массива бакетов.
     */
    private static final int DEFAULT_CAPACITY = 16;
    /**
     * Коэффициент загрузки, определяющий, когда производить увеличение размера хеш-карты.
     */
    private static final double LOAD_FACTOR = 0.75;
    /**
     * Текущий размер массива бакетов.
     */
    private int capacity;
    /**
     * Текущее количество элементов в хеш-карте.
     */
    private int size = 0;
    private LinkedList<Entry<K, V>>[] table;
    /**
     * Создает экземпляр MyHashMap с размером по умолчанию.
     */
    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }
    /**
     * Создает экземпляр MyHashMap с указанным начальным размером.
     *
     * @param capacity Начальный размер MyHashMap.
     */
    public MyHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new LinkedList[capacity];
    }
    /**
     * Добавляет значение в хеш-карту с указанным ключом.
     *
     * @param key   Ключ, по которому добавляется значение.
     * @param value Значение, которое добавляется в хеш-карту.
     */
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
    /**
     * Возвращает значение из хеш-карты по указанному ключу.
     *
     * @param key Ключ, по которому происходит поиск значения.
     * @return Значение, связанное с указанным ключом, или null, если ключ не найден.
     */
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
    /**
     * Удаляет запись из хеш-карты по указанному ключу.
     *
     * @param key Ключ, по которому происходит удаление записи.
     */
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
    /**
     * Проверяет, содержится ли ключ в хеш-карте.
     *
     * @param key Ключ, который проверяется на наличие в хеш-карте.
     * @return true, если ключ содержится в хеш-карте, иначе false.
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }
    /**
     * Возвращает текущий размер хеш-карты.
     *
     * @return Текущий размер хеш-карты.
     */
    public int size() {
        return size;
    }
    /**
     * Добавляет значение в хеш-карту с ключом null.
     *
     * @param value Значение, которое добавляется в хеш-карту.
     */
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
    /**
     * Возвращает значение из хеш-карты по ключу null.
     *
     * @return Значение, связанное с ключом null, или null, если ключ не найден.
     */
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
    /**
     * Удаляет запись из хеш-карты с ключом null.
     */
    private void removeForNullKey() {
        if (table[0] != null) {
            table[0].removeIf(entry -> entry.getKey() == null);
            size--;
        }
    }
    /**
     * Изменяет размер хеш-карты, удваивая его.
     */
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
    /**
     * Возвращает хеш-код для указанного ключа.
     *
     * @param key Ключ, для которого вычисляется хеш-код.
     * @return Хеш-код для указанного ключа.
     */
    private int hash(K key) {
        return key == null ? 0 : key.hashCode();
    }
    /**
     * Возвращает индекс бакета для указанного хеш-кода и размера хеш-карты.
     *
     * @param hash     Хеш-код ключа.
     * @param capacity Размер хеш-карты.
     * @return Индекс бакета для указанного хеш-кода и размера хеш-карты.
     */
    private int indexFor(int hash, int capacity) {
        return hash & (capacity - 1);
    }
    /**
     * Внутренний статический класс, представляющий запись в хеш-карте.
     *
     * @param <K> Тип ключа.
     * @param <V> Тип значения.
     */
    private static class Entry<K, V> {
        private final K key;
        private V value;
        /**
         * Конструктор записи. Создает новую запись с указанным ключом и значением.
         *
         * @param key   Ключ записи.
         * @param value Значение записи.
         */
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        /**
         * Возвращает ключ записи.
         *
         * @return Ключ записи.
         */
        public K getKey() {
            return key;
        }
        /**
         * Возвращает значение записи.
         *
         * @return Значение записи.
         */
        public V getValue() {
            return value;
        }
        /**
         * Устанавливает новое значение для записи.
         *
         * @param value Новое значение для записи.
         */
        public void setValue(V value) {
            this.value = value;
        }
    }

}