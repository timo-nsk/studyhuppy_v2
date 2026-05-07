package com.studyhuppy.timer.application.service;

import com.studyhuppy.timer.adapter.db.TimerBeendetEventRepository;
import com.studyhuppy.timer.adapter.db.TimerRepository;
import com.studyhuppy.timer.adapter.web.dto.TimerRequest;
import com.studyhuppy.timer.domain.model.Timer;
import com.studyhuppy.timer.domain.model.TimerBeendetEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TimerService {

	private TimerRepository timerRepository;

	private TimerBeendetEventRepository timerBeendetEventRepository;

	public TimerService(
			TimerRepository timerRepository,
			TimerBeendetEventRepository timerBeendetEventRepository) {
		this.timerRepository = timerRepository;
		this.timerBeendetEventRepository = timerBeendetEventRepository;
	}

	public void save(Timer timer) {
		timerRepository.save(timer);
	}

	public void save(TimerRequest request) {
		Timer timer = new Timer(null, request.titel(), 0, true);
		Timer neuerTimer = timerRepository.save(timer);
		log.info("SAVED neuer Timer: " + neuerTimer);
	}

	public List<Timer> findAll() {
		return timerRepository.findAll();
	}

	public Timer findById(Long id) {
		Optional<Timer> t = timerRepository.findById(id);
		return t.orElse(null);
	}

	public boolean sekundenAktualisieren(Long id, Integer sekundenNeu) {
		Timer t = timerRepository.findById(id).orElse(null);

		if(t == null) {
			log.error("Timer mit id=" + id + " nicht gefunden");
			return false;
		}

		Integer sekundenAlt = t.getSekunden();
		Integer deltaSekunden = sekundenNeu - sekundenAlt;
		TimerBeendetEvent beendetEvent = new TimerBeendetEvent(
				null,
				t.getId(),
				LocalDateTime.now(),
				deltaSekunden
		);

		t.setSekunden(sekundenNeu);

		timerRepository.save(t);
		timerBeendetEventRepository.save(beendetEvent);
		log.info("Sekunden von Timer= " + id + "gesetzt auf " + sekundenNeu);
		return true;
	}

	public boolean sekundenHinzufuegen(Long id, Integer sekunden) {
		Timer t = timerRepository.findById(id).orElse(null);

		if(t == null) {
			log.error("Timer mit id=" + id + " nicht gefunden");
			return false;
		}


		t.sekundenHinzufuegen(sekunden);

		timerRepository.save(t);
		log.info("Sekunden von Timer= " + id + "gesetzt auf " + t.getSekunden());
		return true;
	}

	public boolean deleteById(Long id) {
		try {
			timerRepository.deleteById(id);
			log.info("Timer id=" + id + " gelöscht");
			return true;
		} catch (EmptyResultDataAccessException exception) {
			log.error("Fehler beim Löschen des Timers id=" + id + ". Existiert nicht.");
			return false;
		}
	}
}
