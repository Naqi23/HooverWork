package com.shell.hoover;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(HooverController.class)
public class HooverControllerTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HooverService hooverService;
	
	@Test
	public void getHooverPos_ShouldReturnHooverCurrentPosition() throws Exception {
		when(hooverService.getHooverPosition()).thenReturn(new Hoover(new int[] {0, 1}, 1));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/shell/v1/hoover/position"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("coords").isArray());
	}
	
	@Test 
	public void getPatchesCleaned_ShouldReturnNoPatchesCleaned() throws Exception {
		when(hooverService.getNoOfPatchesCleaned()).thenReturn(new Hoover(new int[] {0, 1}, 1));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/shell/v1/hoover/patchesCleaned"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("patches").value(1));
	}
	
	@Test
	public void cleanRoom_ShouldCleanRoom() throws Exception {
		
		when(hooverService.getCleaningDetails()).thenReturn(new Hoover(new int[] {0, 1}, 1));
		
		String json = "{\r\n" + 
				"\"roomSize\" : [5, 5],\r\n" + 
				"\"coords\" : [1, 2],\r\n" + 
				"\"patches\" : [\r\n" + 
				"[1, 0],\r\n" + 
				"[2, 2],\r\n" + 
				"[2, 3]\r\n" + 
				"],\r\n" + 
				"\"instructions\" : \"NNESEESWNWW\"\r\n" + 
				"}";
		mockMvc.perform(MockMvcRequestBuilders.post("/shell/v1/hoover/clean")
				.content(json)
		        .contentType(MediaType.APPLICATION_JSON)
		        .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	//Converts Object to Json String
	private String convertObjectToJsonString(HooverService hooverService) throws JsonProcessingException{
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.writeValueAsString(hooverService);
	}
	
	
	
}
 