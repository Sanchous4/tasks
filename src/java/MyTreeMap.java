package java;

import lombok.val;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class MyTreeMap<Key extends Comparable<? super Key>, Value> {

    static final boolean RED = true;
    static final boolean BLACK = false;

    int size = 0;

    private transient Entry<Key, Value> root;

    private final @Nullable Comparator<? super Key> comparator;

    public MyTreeMap() {
        this.comparator = null;
    }

    public MyTreeMap(final @NotNull Comparator<? super Key> comparator) {
        this.comparator = comparator;
    }

    private int compare(final @Nullable Key key1, final @Nullable Key key2) {
        if (comparator != null) {
            return comparator.compare(key1, key2);
        }

        if (key1 == null || key2 == null) {
            throw new NullPointerException("Keys cannot be null");
        }

        return key1.compareTo(key2);
    }

    void put(final @Nullable Key key, final @Nullable Value value) {
        if (root == null) {
            size++;
            root = new Entry<>(key, value, BLACK);
            return;
        }

        var entry = root;

        while (true) {
            val result = compare(key, entry.key);

            if (result == 0) {
                entry.value = value;
                break;
            }

            if (result < 0) {
                val entryLeft = entry.left;

                if (entryLeft == null) {
                    size++;
                    val insertedEntry = new Entry<>(key, value, entry, RED);
                    correctTree(insertedEntry);
                    entry.left = insertedEntry;
                    break;
                }

                entry = entry.left;
            } else {
                val entryRight = entry.right;

                if (entryRight == null) {
                    size++;
                    val insertedEntry = new Entry<>(key, value, entry, RED);
                    correctTree(insertedEntry);
                    entry.right = insertedEntry;
                    break;
                }

                entry = entry.right;
            }
        }


    }

//    boolean correctEntry(final @Nullable Entry<Key, Value> entry) {
//        if (entry == null || entry.parent == null) return false;
//
//        if (entry.parent.red) {
//            throw new IllegalStateException("Violation found!");
//        }
//
//        return true;
//    }

    void correctTree(final @NotNull Entry<Key, Value> entry) {
        while (entry != root && entry.isParentRed()) {
            if()
        }
    }

    static final class Entry<K, V> {
        final @Nullable K key;
        @Nullable V value;
        @Nullable Entry<K, V> left;
        @Nullable Entry<K, V> right;
        @Nullable Entry<K, V> parent;
        boolean red;

        private boolean isParentRed() {
            return parent != null && parent.red;
        }

        private boolean parentIsLeftChild() {
            if(parent == null || parent.parent == null) return false;

            val grandparent = parent.parent;

            return grandparent.left == this;
        }

        private boolean parentIsRightChild() {
            if(parent == null || parent.parent == null) return false;

            val grandparent = parent.parent;

            return grandparent.right == this;
        }


        private Entry(final @Nullable K key, final @Nullable V value) {
            this.key = key;
            this.value = value;
        }

        private Entry(final @Nullable K key, final @Nullable V value, final boolean red) {
            this.key = key;
            this.value = value;
            this.red = red;
        }

        private Entry(final @Nullable K key, final @Nullable V value, final @NotNull Entry<K, V> parent, final boolean red) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.red = red;
        }
    }
}
