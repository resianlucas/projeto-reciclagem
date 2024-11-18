package br.com.reciclagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.reciclagem.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

}
