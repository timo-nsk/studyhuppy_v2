package com.studyhuppy.timer.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "timer_beendet_event")
public class TimerBeendetEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column( nullable = false)
	private Long timerId;

	@Column( nullable = false)
	private LocalDateTime datum;

	private Integer sekundenGesamt;
}
