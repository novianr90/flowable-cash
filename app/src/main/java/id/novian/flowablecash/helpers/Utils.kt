package id.novian.flowablecash.helpers

@Suppress("NOTHING_TO_INLINE")
inline fun <T : Any, R : Any> T.sameAndEqual(n: R): Boolean =
    ((this.javaClass == n.javaClass) && (this == n))