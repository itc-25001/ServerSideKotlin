import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class NumberTestByBehaviorSpec: BehaviorSpec({
    given("isOdd"){
        When("num is odd number"){
            val number = Number(1)
            then("returns true"){
                number.isOdd() shouldBe true
            }
        }
        When("num is even number"){
            val number = Number(2)
            Then("returns false"){
                number.isOdd() shouldBe false
            }
        }
    }
})