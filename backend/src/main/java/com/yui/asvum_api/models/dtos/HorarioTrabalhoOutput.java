package com.yui.asvum_api.models.dtos;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioTrabalhoOutput(
        Long id,
        DayOfWeek diaDaSemana,
        LocalTime horaInicio,
        LocalTime horaFim
) {}
