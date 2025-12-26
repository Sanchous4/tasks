package kt

interface IUniqueUserIdsKt {
    fun count(userIds: IntArray): Int
    fun uniqueIds(userIds: IntArray): IntArray
    fun uniqueSortedIds(userIds: IntArray): IntArray
    fun uniqueIdsWithPreservedOrder(userIds: IntArray): IntArray
}
