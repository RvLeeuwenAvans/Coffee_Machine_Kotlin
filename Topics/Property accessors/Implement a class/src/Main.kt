// write your class here
class LewisCarrollBook(
    _name: String = "",
    _author: String = "Lewis Carroll",
    _price: Int = 0
) {
    var name: String = _name
        get() {
            println("The name of the book is $field")
            return field
        }
        set(value) {
            println("Now, a book called $value")
            field = value
        }
    val author: String = _author
        get() {
            println("The author of the book is $field")
            return field
        }
    var price: Int = _price
        set(value) {
            println("The new price is $value")
            field = value
        }
        get() {
            println("Putting a new price...")
            return field
        }
}