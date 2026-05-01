package com.studyhuppy.timer.adapter.web.controller;

import com.studyhuppy.timer.adapter.web.dto.TimerRequest;
import com.studyhuppy.timer.application.service.TimerService;
import com.studyhuppy.timer.domain.model.Timer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {
		"http://localhost:3000",
		"http://localhost:7760"
})
@RestController
@RequestMapping("/api/v1")
public class TimerController {

	private final TimerService timerService;

	public TimerController(TimerService timerService) {
		this.timerService = timerService;
	}

	@GetMapping("/timers")
	public ResponseEntity<List<Timer>> getTimers() {
		return ResponseEntity.ok(timerService.findAll());
	}

	@PostMapping("/timers")
	public ResponseEntity<Void> postTimer(@RequestBody @Valid TimerRequest request, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) return ResponseEntity.badRequest().build();
		timerService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/timers/{id}")
	public ResponseEntity<Timer> getTimer(@PathVariable Long id) {
		Timer t = timerService.findById(id);
		if(t == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(t);
	}

	@DeleteMapping("/timers/{id}")
	public ResponseEntity<Void> deleteTimer(@PathVariable Long id) {
		boolean ok = timerService.deleteById(id);
		if (!ok) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/timers/{id}/sekunden/{sekunden}")
	public ResponseEntity<Void> updateSekunden(
			@PathVariable Long id,
            @PathVariable Integer sekunden) {
		boolean ok = timerService.sekundenHinzufuegen(id, sekunden);
		if(!ok) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}

	@PostMapping("/timers/{id}/sekunden/{sekunden}")
	public ResponseEntity<Void> setSekunden(
			@PathVariable Long id,
			@PathVariable Integer sekunden) {
		boolean ok = timerService.sekundenAktualisieren(id, sekunden);
		if(!ok) return ResponseEntity.notFound().build();
		return ResponseEntity.ok().build();
	}

}
