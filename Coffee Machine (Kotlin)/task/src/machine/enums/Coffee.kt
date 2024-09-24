package machine.enums

enum class Coffee(
    val type: String,
    val water: Int,
    val milk: Int,
    val beans: Int,
    val price: Int,
) {
    ESPRESSO("espresso", 250, 0, 16, 4),
    LATTE("latte", 350, 75, 20, 7),
    CAPPUCCINO("cappuccino", 200, 100, 12, 6);

    companion object {
        /**
         * Returns a generated menu displaying all options present within the enum.
         * The menu lists all options starting at 1 so the listed items do not directly
         * correspond with their ordinal in the enum.
         */
        fun getMenuPrompt() = String.format(
            "What do you want to buy? %s, back - to main menu:",
            Coffee.values().joinToString(", ") { (it.ordinal + 1).toString() + " - " + it.type }
        )
    }
}