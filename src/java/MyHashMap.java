package java; // don't care about the package it is my own package

import lombok.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@NoArgsConstructor
public class MyHashMap<Key, Value> {

    private int size = 0;

    private int capacity = 16;
    private float loadFactor = 0.75f;
    private int threshold = computeThreshold();

    @SuppressWarnings("unchecked")
    private @Nullable Entry<Key, Value>[] table = (Entry<Key, Value>[]) new Entry[capacity];

    public MyHashMap(final int capacity) {
        this.capacity = checkCapacity(capacity);
        this.threshold = this.computeThreshold();

        this.table = resizeTable();
    }

    public MyHashMap(final int capacity, final float loadFactor) {
        this.capacity = checkCapacity(capacity);
        this.loadFactor = checkLoadFactor(loadFactor);
        this.threshold = this.computeThreshold();

        this.table = resizeTable();
    }

    private int computeThreshold() {
        return (int) (capacity * loadFactor);
    }

    @SuppressWarnings("unchecked")
    private Entry<Key, Value>[] resizeTable() {
        val oldTable = table;
        table = new Entry[capacity];

        if (Objects.isNull(oldTable)) {
            return table;
        }

        for (final @Nullable Entry<Key, Value> entry : oldTable) {
            this.reAdEntry(entry);
        }

        return table;
    }

    private void reAdEntry(final @Nullable Entry<Key, Value> entry) {
        if (Objects.isNull(entry)) {
            return;
        }

        this.rePut(entry);

        var nextEntry = entry.getNext();
        while (Objects.nonNull(nextEntry)) {
            val next = nextEntry.getNext();
            this.rePut(nextEntry);
            nextEntry = next;
        }
    }

    private void rePut(final @NotNull Entry<Key, Value> newEntry) {
        val bucketIndex = calculateBucketIndex(newEntry.getHash());

        newEntry.setNext(table[bucketIndex]);
        table[bucketIndex] = newEntry;
    }


    private int checkCapacity(int capacity) {
        val isGreaterZero = capacity > 0;

        if (!isGreaterZero) throw new IllegalArgumentException("Capacity must be greater than zero");

        val isPowerOfTwo = (capacity & (capacity - 1)) == 0;

        if (!isPowerOfTwo) {
            return findNextPowerOfTwo(capacity);
        }

        return capacity;
    }

    private int findNextPowerOfTwo(int capacity) {
        int nextPowerOfTwo = 1;
        while (nextPowerOfTwo < capacity) {
            nextPowerOfTwo <<= 1;
        }
        return nextPowerOfTwo;
    }

    private float checkLoadFactor(float loadFactor) {
        val isGreaterZero = loadFactor > 0;

        if (isGreaterZero) return loadFactor;

        throw new IllegalArgumentException("Load factor must be greater than zero");
    }

    public void checkThreshold() {
        if (size < threshold) return;

        this.capacity <<= 1;
        this.threshold = this.computeThreshold();

        this.table = resizeTable();
    }

    private int hashCode(Key key) {
        val rawHash = Objects.isNull(key) ? 0 : key.hashCode();
        return rawHash & 0x7FFFFFFF;
    }

    private int calculateBucketIndex(int hash) {
        return hash & (capacity - 1);
    }

    private BucketCalculationResult calculateBucket(Key key) {
        val hash = hashCode(key);
        val bucketIndex = calculateBucketIndex(hash);
        return new BucketCalculationResult(hash, bucketIndex);
    }

    public void put(Key key, Value value) {
        val bucketResult = calculateBucket(key);

        var existingEntry = table[bucketResult.bucketIndex];
        val newEntry = Entry.buildNewEntry(key, value, bucketResult.hash);

        val updateResult = updateExistingEntry(existingEntry, newEntry);

        if (updateResult.isSizeModified()) {
            size += 1;
        }

        table[bucketResult.bucketIndex] = updateResult.entry();
        checkThreshold();
    }

    private boolean areEntryKeysEqual(final @NotNull Entry<Key, Value> existingEntry, final @NotNull Entry<Key, Value> newEntry) {
        return existingEntry.hash == newEntry.hash && Objects.equals(existingEntry.key, newEntry.key);
    }

    private @NotNull EntryModificationResult<Key, Value> updateExistingEntry(final @Nullable Entry<Key, Value> existingEntry, final @NotNull Entry<Key, Value> newEntry) {
        if (Objects.isNull(existingEntry)) return new EntryModificationResult<>(newEntry, true);

        var current = existingEntry;

        do {
            if (areEntryKeysEqual(current, newEntry)) {
                current.setValue(newEntry.value);
                return new EntryModificationResult<>(existingEntry, false);
            }

            if (!Objects.isNull(current.next)) {
                current = current.next;
            }

        } while (current.next != null);

        current.next = newEntry;
        return new EntryModificationResult<>(existingEntry, true);
    }

    record BucketCalculationResult(int hash, int bucketIndex) {
    }

    record EntryModificationResult<Key, Value>(@NonNull Entry<Key, Value> entry, boolean isSizeModified) {
    }

    @Data
    static class Entry<Key, Value> {
        @Nullable
        private Key key;

        @Nullable
        private Value value;

        private final int hash;

        @Nullable
        private Entry<Key, Value> next;

        public Entry(final @Nullable Key key, final @Nullable Value value, int hash, final @Nullable Entry<Key, Value> next) {
            this.key = key;
            this.value = value;
            this.hash = hash;
            this.next = next;
        }

        static <Key, Value> Entry<Key, Value> buildNewEntry(Key key, Value value, int hash) {
            return new Entry<>(key, value, hash, null);
        }
    }
}
