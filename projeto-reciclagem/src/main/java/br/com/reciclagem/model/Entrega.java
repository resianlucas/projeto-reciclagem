package br.com.reciclagem.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do material reciclável é obrigatório.")
    private String material;

    @Min(value = 1, message = "A quantidade precisa ser maior do que zero.")
    private Integer quantidade;

    @Min(value = 0, message = "Os pontos gerados devem ser positivos.")
    private Integer pontosGerados;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataEntrega;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
