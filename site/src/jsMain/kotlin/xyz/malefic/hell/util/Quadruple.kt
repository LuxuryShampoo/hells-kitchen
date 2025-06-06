package xyz.malefic.hell.util

class Quadruple<A, B, C, D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) {
    operator fun component1(): A = first

    operator fun component2(): B = second

    operator fun component3(): C = third

    operator fun component4(): D = fourth

    override fun toString(): String = "Quadruple($first, $second, $third, $fourth)"
}

fun Pair<Pair<Pair<Int, Int>, Int>, Int>.toQuadruple(): Quadruple<Int, Int, Int, Int> {
    val (firstPair, fourth) = this
    val (secondPair, third) = firstPair
    val (first, second) = secondPair
    return Quadruple(first, second, third, fourth)
}
