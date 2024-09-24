// Declare a function to determine age category based on the given age

fun main() {
    // Read the person's age
    val age = readln().toInt()

    println(determineAgeBracket(age))
}

fun determineAgeBracket(age: Int): String =
    when (true) {
        (age < 18) -> "Minor"
        (age in 18..64) -> "Adult"
        else -> "Senior"
    }