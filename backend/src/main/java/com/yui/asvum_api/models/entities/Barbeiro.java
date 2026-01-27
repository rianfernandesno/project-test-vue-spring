package com.yui.asvum_api.models.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

import com.yui.asvum_api.models.enums.Especialidade;

@Entity
@Table(name = "barbeiro")
public class Barbeiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String fotoPath;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "barbeiro_especialidades", joinColumns = @JoinColumn(name = "barbeiro_id"))
    @Enumerated(EnumType.STRING)
    private Set<Especialidade> especialidades;

    @OneToMany(mappedBy = "barbeiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HorarioTrabalho> horariosTrabalho;

    private Boolean estaAtivo;

    public Barbeiro() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public Set<Especialidade> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(Set<Especialidade> especialidades) {
        this.especialidades = especialidades;
    }

    public Boolean getEstaAtivo() {
        return estaAtivo;
    }

    public void setEstaAtivo(Boolean estaAtivo) {
        this.estaAtivo = estaAtivo;
    }

    public List<HorarioTrabalho> getHorariosTrabalho() {
        return horariosTrabalho;
    }

    public void setHorariosTrabalho(List<HorarioTrabalho> horariosTrabalho) {
        this.horariosTrabalho = horariosTrabalho;
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
        Barbeiro other = (Barbeiro) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
