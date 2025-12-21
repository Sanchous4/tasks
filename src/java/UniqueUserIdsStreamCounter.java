package java;

import lombok.val;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class UniqueUserIdsStreamCounter {

    private final int[] userIds;

    UniqueUserIdsStreamCounter(final int[] userIds) {
        this.userIds = userIds;
    }

    int[] uniqueSortedUserIdsArray() {
        return Arrays.stream(userIds).parallel().distinct().sorted().toArray();
    }

    int[] uniqueUserIdsArrayWithKeptOrder() {
        val uniqueUserIds = new ConcurrentHashMap<Integer, Integer>();

        IntStream.range(0, userIds.length).parallel().forEach(userIdIndex -> {
            uniqueUserIds.merge(userIds[userIdIndex], userIdIndex, Math::min);
        });

        return uniqueUserIds.entrySet().parallelStream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .mapToInt(Map.Entry::getKey).toArray();
    }

    long countUniqueUserIds() {
        return Arrays.stream(userIds).parallel().distinct().count();
    }
}
