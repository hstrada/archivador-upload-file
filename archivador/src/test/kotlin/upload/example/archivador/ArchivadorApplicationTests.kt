package upload.example.archivador

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ArchivadorApplicationTests {

	@Autowired
	private lateinit var mvc: MockMvc

	@Test
	fun testDataController() {
		mvc.perform(MockMvcRequestBuilders.get("/entities"))
			.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Teacher")))
			.andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Student")))
	}

}
