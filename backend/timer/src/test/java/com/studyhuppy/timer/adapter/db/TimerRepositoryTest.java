package com.studyhuppy.timer.adapter.db;

import com.studyhuppy.timer.domain.model.Timer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
public class TimerRepositoryTest {

	@Autowired
	private TimerRepository timerRepository;

	@Test
	@DisplayName("Ein Timer kann erstellt werden")
	void test1() {
		Timer timer = new Timer(null, "T1", 1000, true);

		Timer saved = timerRepository.save(timer);

		assertThat(saved.getId()).isNotNull();
	}
}
