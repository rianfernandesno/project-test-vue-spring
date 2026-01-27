package com.yui.asvum_api.models.entities;

import jakarta.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horario_trabalho")
public class HorarioTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek diaDaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFim;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "barbeiro_id", nullable = false)
    private Barbeiro barbeiro;

    public HorarioTrabalho() {
    }

    public HorarioTrabalho(DayOfWeek diaDaSemana, LocalTime horaInicio, LocalTime horaFim, Barbeiro barbeiro) {
        this.diaDaSemana = diaDaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.barbeiro = barbeiro;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(DayOfWeek diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public Barbeiro getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(Barbeiro barbeiro) {
        this.barbeiro = barbeiro;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HorarioTrabalho other = (HorarioTrabalho) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

 
}
