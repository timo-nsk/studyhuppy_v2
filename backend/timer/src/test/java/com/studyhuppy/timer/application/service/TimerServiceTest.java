package com.studyhuppy.timer.application.service;

import com.studyhuppy.timer.adapter.db.TimerBeendetEventRepository;
import com.studyhuppy.timer.adapter.db.TimerRepository;
import com.studyhuppy.timer.adapter.web.dto.TimerRequest;
import com.studyhuppy.timer.domain.model.Timer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TimerServiceTest {

	TimerRepository timerRepository;

	TimerBeendetEventRepository timerBeendetEventRepository;

	TimerService timerService;

	@BeforeEach
	public void setUp() {
		timerRepository = mock(TimerRepository.class);
		timerBeendetEventRepository = mock(TimerBeendetEventRepository.class);
		timerService = new TimerService(timerRepository, timerBeendetEventRepository);
	}

	@Test
	@DisplayName("Ein Timer wird aus dem TimerRequest abgespeichert")
	void test1() {
		TimerRequest timerRequest = new TimerRequest("testtimer");

		timerService.save(timerRequest);

		verify(timerRepository, times(1)).save(ArgumentMatchers.any(Timer.class));
	}

	@Test
	@DisplayName("Alle Timer werden aus der Datenbank abgerufen")
	void test2() {
		timerService.findAll();

		verify(timerRepository, times(1)).findAll();
	}

	@Test
	@DisplayName("Ein Timer wird anhand seiner Id aus der Datenbank gerufen")
	void test3() {
		Optional<Timer> t = Optional.of(new Timer(1L, "testimer", 10, true));
		when(timerRepository.findById(1L)).thenReturn(t);

		timerService.findById(1L);

		verify(timerRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("Wenn kein Timer durch eine Id aus der Datenbank abgerufen werden kann wird null zurückgegeben")
	void test4() {
		Optional<Timer> t = Optional.empty();
		when(timerRepository.findById(1L)).thenReturn(t);

		Timer received = timerService.findById(1L);

		assertThat(received).isNull();
		verify(timerRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("Wenn kein Timer durch eine Id aus der Datenbank abgerufen werden kann " +
			"gibt addSekundenToTimer false zurück")
	void test5() {
		Optional<Timer> t = Optional.empty();
		when(timerRepository.findById(1L)).thenReturn(t);

		boolean ok = timerService.sekundenAktualisieren(1L, 10);

		assertThat(ok).isFalse();
		verify(timerRepository, times(1)).findById(1L);
	}

	@Test
	@DisplayName("Wenn ein Timer durch eine Id aus der Datenbank abgerufen werden kann " +
			" werden die Sekunden neu gesetzt und addSekundenToTimer gibt true zurück")
	void test6() {
		Optional<Timer> t = Optional.of(new Timer(1L, "testimer", 100, true));
		when(timerRepository.findById(1L)).thenReturn(t);

		boolean ok = timerService.sekundenAktualisieren(1L, 200);

		assertThat(ok).isTrue();
		assertThat(t.get().getSekunden()).isEqualTo(200);
		verify(timerRepository, times(1)).findById(1L);
		verify(timerRepository, times(1)).save(t.get());
	}

	@Test
	@DisplayName("Ein Timer wird anhand seiner Id aus der Datenbank entfernt")
	void test7() {
		boolean ok = timerService.deleteById(1L);

		assertThat(ok).isTrue();
		verify(timerRepository, times(1)).deleteById(1L);
	}
}
