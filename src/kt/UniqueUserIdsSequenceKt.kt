package kt


class UniqueUserIdsSequenceKt : IUniqueUserIdsKt {
    override fun count(userIds: IntArray): Int {
        return Utils.uniqueIdsSequence(userIds).count()
    }

    override fun uniqueIds(userIds: IntArray): IntArray {
        return Utils.uniqueIdsSequence(userIds).toList().toIntArray()
    }

    override fun uniqueSortedIds(userIds: IntArray): IntArray {
        return Utils.uniqueIdsSequence(userIds).sorted().toList().toIntArray()
    }

    override fun uniqueIdsWithPreservedOrder(userIds: IntArray): IntArray {
        val uniqueIdsSet = LinkedHashSet<Int>()
        userIds.asSequence().forEach { uniqueIdsSet.add(it); }
        return uniqueIdsSet.toIntArray()
    }

    object Utils {
        fun uniqueIdsSequence(userIds: IntArray): Sequence<Int> {
            return userIds.asSequence().distinct()
        }
    }
}
