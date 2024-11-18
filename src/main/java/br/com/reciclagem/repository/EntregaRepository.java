package br.com.reciclagem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reciclagem.model.Entrega;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
	
	public List<Entrega> findByUsuario_Id(Long id);
}
