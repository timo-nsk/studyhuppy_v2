package com.studyhuppy.timer.adapter.db;

import com.studyhuppy.timer.domain.model.TimerBeendetEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerBeendetEventRepository extends JpaRepository<TimerBeendetEvent, Long> {
}
