import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserServiceTest : FunSpec({
    val userRepository: UserRepository = mockk()
    val target = UserService(userRepository)

    test("createMessage:: when user name is exist then return message") {
        every { userRepository.findName(any()) } returns "Kotest"
        val id = 100

        val result = target.createMessage(id)
        result shouldBe "Hello Kotest"

        verify { userRepository.findName(id) }
    }
    afterTest {
        clearMocks(userRepository)
    }
})