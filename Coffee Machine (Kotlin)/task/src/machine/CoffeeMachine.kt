package machine

import machine.enums.Coffee

// The prompt that is displayed, whilst the coffee machine is idling,
// moved to the top of the file for the sake of readability/edit-ability.
private const val IDLE_PROMPT = "Write action (buy, fill, take, remaining, exit):"

class CoffeeMachine(
    private var waterRemaining: Int,
    private var milkRemaining: Int,
    private var coffeeBeansRemaining: Int,
    private var cupsRemaining: Int,
    private var moneyRemaining: Int,
) {
    /** determines whether the machine is on or off, it's set method is set to private,
    it's state should only be altered through the terminal command: exit. */
    var running = true
        private set

    /** Keeps state of what the coffee machine is currently doing, this is necessary as some processes
    depend on one another linearly */
    private var currentState: MachineState = MachineState.IDLE

    init {
        // At program start, we provide the user with a starting prompt
        println(currentState.prompt)
    }

    /**
     * Takes user input,and manages the coffee machine's internal state and processes accordingly.
     */
    fun handleInput(input: String) {
        if (currentState == MachineState.IDLE) {
            currentState = when (input) {
                "buy" -> MachineState.BREWING_COFFEE
                "fill" -> MachineState.PREPARING_RESTOCK
                "take" -> MachineState.DISPENSING_CASH
                "remaining" -> MachineState.DISPLAYING_STOCK_LEVELS
                "exit" -> MachineState.SHUTTING_DOWN
                else -> currentState
            }
        }

        when (currentState) {
            MachineState.PREPARING_RESTOCK, MachineState.IDLE -> progressState()
            MachineState.ADDING_WATER -> increaseStock(water = input.toIntOrNull() ?: 0)
            MachineState.ADDING_MILK -> increaseStock(milk = input.toIntOrNull() ?: 0)
            MachineState.ADDING_BEANS -> increaseStock(coffeeBeans = input.toIntOrNull() ?: 0)
            MachineState.ADDING_CUPS -> increaseStock(cups = input.toIntOrNull() ?: 0)
            MachineState.DISPENSING_CASH -> retrieveMoney().also { progressState() }
            MachineState.DISPLAYING_STOCK_LEVELS -> displayRemainingStock().also { progressState() }
            MachineState.SHUTTING_DOWN -> this.running = false
            MachineState.BREWING_COFFEE -> if (input == "back") {
                currentState = MachineState.IDLE
                progressState()
            } else {
                input.toIntOrNull()?.let {
                    // Menu items are displayed starting at .1, so we need to account for the null-index of the enum.
                    prepareCoffee(Coffee.values()[it - 1])
                    progressState()
                } ?: println(currentState.prompt)
            }
        }
    }

    private fun increaseStock(water: Int = 0, milk: Int = 0, coffeeBeans: Int = 0, cups: Int = 0) {
        waterRemaining += water
        milkRemaining += milk
        coffeeBeansRemaining += coffeeBeans
        cupsRemaining += cups

        progressState()
    }

    private fun displayRemainingStock() {
        println(
            """
            The coffee machine has:
            $waterRemaining ml of water
            $milkRemaining ml of milk
            $coffeeBeansRemaining g of coffee beans
            $cupsRemaining disposable cups
            $$moneyRemaining of money
            """.trimIndent()
        )
    }

    /**
     * Checks whether the machine has enough resources to brew the given coffee.
     * If current stock levels are sufficient it brews the coffee, otherwise it displays the missing ingredient
     */
    private fun prepareCoffee(coffee: Coffee) = when (true) {
        (waterRemaining - coffee.water < 0) -> println("Sorry, not enough water!")
        (milkRemaining - coffee.milk < 0) -> println("Sorry, not enough milk!")
        (coffeeBeansRemaining - coffee.beans < 0) -> println("Sorry, not enough coffee beans!")
        (cupsRemaining - 1 < 0) -> println("Sorry, not enough coffee cups!")
        else -> {
            println("I have enough resources, making you a coffee!")
            milkRemaining -= coffee.milk
            waterRemaining -= coffee.water
            coffeeBeansRemaining -= coffee.beans
            cupsRemaining -= 1
            moneyRemaining += coffee.price
        }
    }

    /**
     * Display the current amount of money in the coffee machine
     * and reset the money counter.
     */
    private fun retrieveMoney() {
        println("I gave you \$$moneyRemaining")
        moneyRemaining = 0
    }

    /**
     * when called the machine progresses into it's next logical state.
     */
    private fun progressState() {
        currentState = currentState.next()
    }

    /**
     * A private enum, purposefully nested withing the coffeeMachine due to its proprietary nature.
     * */
    private enum class MachineState(val prompt: String = IDLE_PROMPT) {
        ADDING_WATER("\n Write how many ml of water you want to add:"),
        ADDING_MILK("\n Write how many ml of milk you want to add:"),
        ADDING_BEANS("\n Write how many grams of coffee beans you want to add:"),
        ADDING_CUPS("\n Write how many disposable cups you want to add:"),
        BREWING_COFFEE(Coffee.getMenuPrompt()),
        DISPLAYING_STOCK_LEVELS,
        PREPARING_RESTOCK,
        DISPENSING_CASH,
        SHUTTING_DOWN,
        IDLE;

        /**
         * Some states are dependant upon the previous one, this function retrieves the next logical state.
         */
        fun next(): MachineState = when (this) {
            PREPARING_RESTOCK -> ADDING_WATER
            ADDING_WATER -> ADDING_MILK
            ADDING_MILK -> ADDING_BEANS
            ADDING_BEANS -> ADDING_CUPS
            BREWING_COFFEE, SHUTTING_DOWN, ADDING_CUPS, DISPENSING_CASH, DISPLAYING_STOCK_LEVELS, IDLE -> IDLE
        }.also { println(it.prompt) }
    }
}