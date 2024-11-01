import kotlin.math.hypot

fun perimeter(
    x1: Double = 0.0,
    y1: Double = 0.0,
    x2: Double = x1,
    y2: Double = y1,
    x3: Double = x1,
    y3: Double = y1,
    x4: Double = x1,
    y4: Double = y1
): Double {
    return listOf(
        hypot(x1 - x2, y1 - y2),
        hypot(x2 - x3, y2 - y3),
        hypot(x3 - x4, y3 - y4),
        hypot(x4 - x1, y4 - y1)
    ).sum()
}