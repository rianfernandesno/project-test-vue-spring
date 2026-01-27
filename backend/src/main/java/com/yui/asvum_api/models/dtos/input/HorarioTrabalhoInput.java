package com.yui.asvum_api.models.dtos.input;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioTrabalhoInput(
        DayOfWeek diaDaSemana,
        LocalTime horaInicio,
        LocalTime horaFim
) {}
