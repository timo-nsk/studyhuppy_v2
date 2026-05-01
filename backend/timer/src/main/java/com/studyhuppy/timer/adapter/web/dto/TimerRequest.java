package com.studyhuppy.timer.adapter.web.dto;

import jakarta.validation.constraints.NotBlank;

public record TimerRequest(
		@NotBlank
		String titel
) {
}
