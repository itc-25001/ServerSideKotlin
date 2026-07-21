import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class NumberTestByStringSpec: StringSpec({
    "isOdd:: when value is odd number then return true" {
        val number = Number(1)
        number.isOdd() shouldBe true
    }
    "isOdd:: when value is even number then return false" {
        val number = Number(2)
        number.isOdd() shouldBe false
    }
})