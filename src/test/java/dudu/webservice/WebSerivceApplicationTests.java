package dudu.webservice;

import dudu.webservice.web.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(HomeController.class)
class WebServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getMockMvc() throws Exception {
		String hello = "hello";
		mvc.perform(get("/hello"))
				.andExpect(status().isOk())
				.andExpect(content().string("hello"));
	}

	@Test
	public void hello_return() throws Exception {
		String name = "hello";
		int amount = 100;

		mvc.perform(get("/hello/dto").param("name",name).param("amount",String.valueOf(amount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value(name))
				.andExpect(jsonPath("$.amount").value(amount));

	}


}
