fun main() {
    val userInput = readln()

    println(
        when (userInput) {
            "gryffindor" -> "bravery"
            "hufflepuff" -> "loyalty"
            "slytherin" -> "cunning"
            "ravenclaw" -> "intellect"
            else -> "not a valid house"
        }
    )
}