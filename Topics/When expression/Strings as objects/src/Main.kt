fun main() {
    val input = readln()
    // write code here

    println(
        when {
            input.isEmpty() -> return
            input.first() == 'i' -> {
                var number = input.drop(1).toInt()
                number++

                number
            }
            input.first() == 's' -> input.drop(1).reversed()
            else -> input
        }
    )
}