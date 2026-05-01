package com.studyhuppy.timer.adapter.db;

import com.studyhuppy.timer.domain.model.Timer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Long> {
}
