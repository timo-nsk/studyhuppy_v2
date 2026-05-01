package com.studyhuppy.timer.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimerTest {

	@Test
	@DisplayName("Ein Timer kann aktiviert werden")
	void test1() {
		Timer t = new Timer(null, "timer", 1000, false);

		t.aktivieren();

		assertThat(t.getAktiv()).isTrue();
	}

	@Test
	@DisplayName("Ein Timer kann deaktiviert werden")
	void test2() {
		Timer t = new Timer(null, "timer", 1000, true);

		t.deaktivieren();

		assertThat(t.getAktiv()).isFalse();
	}
}
