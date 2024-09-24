// complete this function, add default values
fun carPrice(old: Int = 5, kilometers: Int = 100000, maximumSpeed: Int = 120, automatic: Boolean = false){
    val carValue = calculateCurrentPriceByAge(
        old,
        calculateCurrentPriceByMillage(
            kilometers,
            calculateCurrentPriceByTopSpeed(
                maximumSpeed,
                calculateCurrentPriceByTransmission(automatic)
            )
        )
    )

    println(carValue)
}

fun calculateCurrentPriceByAge(age: Int, carValue: Int = 20000): Int = carValue - (age * 2000)

fun calculateCurrentPriceByMillage(millage: Int, carValue: Int = 20000): Int = carValue - (200 * (millage/10000))

fun calculateCurrentPriceByTopSpeed(topSpeed: Int, carValue: Int = 20000): Int = carValue + (topSpeed - 120) * 100

fun calculateCurrentPriceByTransmission(hasManualTransmission: Boolean, carValue: Int = 20000): Int = if (hasManualTransmission) carValue + 1500 else carValue