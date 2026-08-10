package com.example.meuTreino.model.entidade;

import com.example.meuTreino.model.cargos.NomesCargos;
import jakarta.persistence.*;

@Entity
@Table(name="cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long cargoId;

    @Enumerated(EnumType.STRING)
    private NomesCargos nome;

    public Cargo() {}

    public Cargo(Long cargoId, NomesCargos nome) {
        this.cargoId = cargoId;
        this.nome = nome;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public NomesCargos getNome() {
        return nome;
    }

    public void setNome(NomesCargos nome) {
        this.nome = nome;
    }
}
