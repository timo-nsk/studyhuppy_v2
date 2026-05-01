package com.studyhuppy.timer.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "timers")
public class Timer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "titel", nullable = false)
	private String titel;

	@Column(name = "sekunden", nullable = false)
	private Integer sekunden;

	@Column(name = "aktiv")
	private Boolean aktiv;

	public void aktivieren() {
		this.aktiv = true;
	}

	public void deaktivieren() {
		this.aktiv = false;
	}

	public void sekundenHinzufuegen(Integer sekunden) {
		this.sekunden += sekunden;
	}
}
