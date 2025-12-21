package java;

import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;

public class UniqueUserIdsCounter {
    private final ConcurrentHashMap<Integer, Integer> uniqueUserIds = new ConcurrentHashMap<>();

    private final Integer[] userIds;

    UniqueUserIdsCounter(final int[] userIds) {
        val userIdsTemp = new Integer[userIds.length];
        for (int i = 0; i < userIds.length; i++) {
            userIdsTemp[i] = userIds[i];
        }
        this.userIds = userIdsTemp;
    }

    Integer[] uniqueUserIdsArraySortedById() {
        return QuickSort.sort(uniqueUserIdsArraySortedByIndex(), (element, pivotElement) -> element - pivotElement);
    }

    long countUniqueUserIds() {
        return uniqueUserIdsArraySortedByIndex().length;
    }

    @SuppressWarnings("unchecked")
    Integer[] uniqueUserIdsArraySortedByIndex() {
        new ParallelStream<Integer>(userIds).forEach(index -> uniqueUserIds.merge(userIds[index], index, Math::min));

        Map.Entry<Integer, Integer>[] uniqueUserIdsArr = uniqueUserIds.entrySet().toArray(Map.Entry[]::new);
        uniqueUserIds.clear();

        val sortedArray = QuickSort.sort(uniqueUserIdsArr, (element, pivotElement) -> element.getValue() - pivotElement.getValue());

        val result = new Integer[sortedArray.length];
        new ParallelStream<Map.Entry<Integer, Integer>>(sortedArray).forEach(index -> result[index] = sortedArray[index].getKey());
        return result;
    }

    @RequiredArgsConstructor
    static
    class ParallelStream<T> {

        private final T[] array;

        private final Queue<Integer> chunkStart = new ConcurrentLinkedQueue<>();

        private final int CHUNK_SIZE = 10;
        private final int MAX_THREADS = 4;

        void forEach(Consumer<Integer> action) {
            prepareChunks();
            val forkJoinTasks = prepareForkJoinTasks(action);
            ForkJoinTask.invokeAll(forkJoinTasks);
        }

        List<ForkJoinTask<Void>> prepareForkJoinTasks(Consumer<Integer> action) {
            val maxForkJoinTasks = Math.min(chunkStart.size(), MAX_THREADS);
            val actions = new ArrayList<ForkJoinTask<Void>>(maxForkJoinTasks);
            for (int i = 0; i < maxForkJoinTasks; i++) {
                actions.add(prepareForkJoinTask(action));
            }
            return actions;
        }

        ForkJoinTask<Void> prepareForkJoinTask(Consumer<Integer> action) {
            return new RecursiveAction() {
                @Override
                protected void compute() {
                    executeChunk(action);
                }
            };
        }

        void prepareChunks() {
            val length = array.length;
            for (int i = 0; i < length; i += CHUNK_SIZE) {
                chunkStart.add(i);
            }
        }

        void executeChunk(Consumer<Integer> action) {
            val chunkStart = this.chunkStart.poll();

            if (Objects.isNull(chunkStart)) return;

            val chunkEnd = Math.min(chunkStart + CHUNK_SIZE, array.length);

            for (int i = chunkStart; i < chunkEnd; i++) {
                action.accept(i);
            }

            prepareForkJoinTask(action).fork();
        }
    }

    private static class QuickSort {
        @FunctionalInterface
        private interface QuickSortComparator<T> {
            int compare(T element, T pivotElement);
        }

        static <T> T[] sort(T[] arr, QuickSortComparator<T> comparator) {
            val copyArr = arr.clone();
            quickSort(copyArr, 0, arr.length - 1, comparator);
            return copyArr;
        }

        private static <T> void quickSort(T[] arr, int low, int high, QuickSortComparator<T> comparator) {
            if (low >= high) return;

            val midPoint = findMidPoint(arr, low, high, comparator);

            ForkJoinTask.invokeAll(
                    new RecursiveAction() {
                        @Override
                        protected void compute() {
                            quickSort(arr, low, midPoint - 1, comparator);
                        }
                    }, new RecursiveAction() {
                        @Override
                        protected void compute() {
                            quickSort(arr, midPoint + 1, high, comparator);
                        }
                    });
        }

        private static int randomPivot(int low, int high) {
            return low + (int) Math.round(Math.random() * (high - low));
        }

        private static <T> int findMidPoint(T[] arr, int low, int high, QuickSortComparator<T> comparator) {
            val pivotPoint = arr[randomPivot(low, high)];
            var midPoint = low;

            for (int i = low; i <= high; i++) {
                if (comparator.compare(arr[i], pivotPoint) < 0) {
                    swap(arr, i, midPoint);
                    midPoint += 1;
                }
            }

            swap(arr, midPoint, high);
            return midPoint;
        }

        private static <T> void swap(T[] arr, int i, int j) {
            T temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
