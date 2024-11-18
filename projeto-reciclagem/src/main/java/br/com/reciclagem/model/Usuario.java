package br.com.reciclagem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "tb_usuario")
public class Usuario {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "O nome é obrigatório para realizar o cadastro.")
	private String name;

	@Email(message = "O email deve ser válido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;
	
	private Integer pontosAcumulados = 0;
	
	@ToString.Exclude
	@OneToMany(
			mappedBy = "usuario",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Entrega> entregas;
}
