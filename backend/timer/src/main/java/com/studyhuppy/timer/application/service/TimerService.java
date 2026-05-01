package com.studyhuppy.timer.application.service;

import com.studyhuppy.timer.adapter.db.TimerRepository;
import com.studyhuppy.timer.adapter.web.dto.TimerRequest;
import com.studyhuppy.timer.domain.model.Timer;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class TimerService {

	private TimerRepository timerRepository;

	public TimerService(TimerRepository timerRepository) {
		this.timerRepository = timerRepository;
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

	public boolean sekundenAktualisieren(Long id, Integer sekunden) {
		Timer t = timerRepository.findById(id).orElse(null);

		if(t == null) {
			log.error("Timer mit id=" + id + " nicht gefunden");
			return false;
		}

		t.setSekunden(sekunden);

		timerRepository.save(t);
		log.info("Sekunden von Timer= " + id + "gesetzt auf " + sekunden);
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
