package br.com.gabriel.forum.repository;

import br.com.gabriel.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicosRepository extends JpaRepository<Topico,Long> {

    List<Topico> findByCursoNome(String nomeCurso);
}
