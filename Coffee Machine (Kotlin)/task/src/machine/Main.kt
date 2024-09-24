package machine

fun main() {
    val coffeeMachine = CoffeeMachine(
        400,
        540,
        120,
        9,
        550
    )

    while (coffeeMachine.running) {
        coffeeMachine.handleInput(readln())
    }
}