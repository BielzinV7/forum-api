package br.com.gabriel.forum.repository;

import br.com.gabriel.forum.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository  extends JpaRepository<Curso,Long> {

    Curso findByNome(String nomeCurso);
}
