import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withTests
import io.kotest.matchers.shouldBe

class NumberTestByFunSpec: FunSpec({
    test("isOdd:: when value is odd number then return true"){
        val number = Number(1)
        number.isOdd() shouldBe true
    }
    test("isOdd:: when value is even number then return false"){
        val number = Number(2)
        number.isOdd() shouldBe false
    }

    context("isRange:: when value in range then return true"){
        withTests(
            1,3,7,10
        ){ value ->
            val number = Number(value)
            number.isRange(1,10) shouldBe true
        }
    }
    context("isRange:: when value not in range then return false"){
        withTests(-1,0,11,12){ value ->
            val number = Number(value)
            number.isRange(1,10) shouldBe false
        }
    }
})