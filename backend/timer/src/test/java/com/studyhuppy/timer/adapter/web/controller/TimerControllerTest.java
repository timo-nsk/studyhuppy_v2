package com.studyhuppy.timer.adapter.web.controller;

import com.studyhuppy.timer.adapter.web.dto.TimerRequest;
import com.studyhuppy.timer.application.service.TimerService;
import com.studyhuppy.timer.domain.model.Timer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TimerController.class)
public class TimerControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private TimerService timerService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@DisplayName("Ein POST-Request auf /timers ist mit gültigem Request erfolgreich und ruft den TimerService auf")
	void test1() throws Exception {
		TimerRequest request = new TimerRequest("testtimer");

		mvc.perform(post("/api/v1/timers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated());

		verify(timerService).save(request);
	}

	@Test
	@DisplayName("Ein POST-Request auf /timers löst mit ungültigem Request einen BadRequest aus " +
			"und der TimerService wird nicht aufgerufen")
	void test2() throws Exception {
		TimerRequest request = new TimerRequest("");

		mvc.perform(post("/api/v1/timers")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());

		verify(timerService, times(0)).save(request);
	}

	@Test
	@DisplayName("Ein GET-Request auf /timers gibt eine Liste mit zwei Timer zurück")
	void test3() throws Exception {
		Timer t1 = new Timer(1L, "t1", 10, true);
		Timer t2 = new Timer(2L, "t2", 10, true);
		List<Timer> timers = List.of(t1, t2);
		when(timerService.findAll()).thenReturn(timers);

		mvc.perform(get("/api/v1/timers")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(timers)));
	}

	@Test
	@DisplayName("Ein Timer mit der id=1 wird mit dem GET-Request auf /api/v1/timers/1 zurückgegeben")
	void test4() throws Exception {
		Long id = 1L;
		Timer t = new Timer(id, "t1", 10, true);
		when(timerService.findById(id)).thenReturn(t);

		mvc.perform(get("/api/v1/timers/"+id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(t)));
	}

	@Test
	@DisplayName("Ein nicht existierender Timer gibt ein NotFound zurück")
	void test5() throws Exception {
		Long id = 1L;
		when(timerService.findById(id)).thenReturn(null);

		mvc.perform(get("/api/v1/timers/"+id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Ein PATCH-Request auf /api/v1/timers/1/sekunden/1000 ist erfolgreich und ruft erfolgreich den TimerService zum" +
			"hinzufügen der Sekunden auf, sodass Status 200 zurückgegeben wird")
	void test6() throws Exception {
		Long id = 1L;
		Integer sekunden = 1000;
		when(timerService.sekundenHinzufuegen(id, sekunden)).thenReturn(true);

		mvc.perform(patch("/api/v1/timers/"+id+"/sekunden/"+sekunden))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Ein PATCH-Request auf /api/v1/timers/1/sekunden/1000 schlägt fehl mit NotFound, " +
			"wenn der Timer nicht existiert")
	void test7() throws Exception {
		Long id = 1L;
		Integer sekunden = 1000;
		when(timerService.sekundenAktualisieren(id, sekunden)).thenReturn(false);

		mvc.perform(patch("/api/v1/timers/"+id+"/sekunden/"+sekunden))
				.andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("Ein DELETE-Request auf /api/v1/timers/1 ist erfolgreich " +
			"und ruft die Service-Methode zum Löschen des Timers aus")
	void test8() throws Exception {
		Long id = 1L;
		when(timerService.deleteById(id)).thenReturn(true);

		mvc.perform(delete("/api/v1/timers/"+id))
				.andExpect(status().isOk());

		verify(timerService, times(1)).deleteById(id);
	}

	@Test
	@DisplayName("Ein DELETE-Request auf /api/v1/timers/1 ist erfolglos " +
			"und ruft die Service-Methode zum Löschen des Timers nicht aus")
	void test9() throws Exception {
		Long id = 1L;
		when(timerService.deleteById(id)).thenReturn(false);

		mvc.perform(delete("/api/v1/timers/"+id))
				.andExpect(status().isNotFound());
	}
}
