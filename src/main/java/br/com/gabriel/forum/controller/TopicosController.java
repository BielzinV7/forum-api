package br.com.gabriel.forum.controller;

import br.com.gabriel.forum.controller.dto.TopicoDto;
import br.com.gabriel.forum.controller.form.AtualizacaoTopicoForm;
import br.com.gabriel.forum.controller.form.TopicoForm;
import br.com.gabriel.forum.model.Curso;
import br.com.gabriel.forum.model.Topico;
import br.com.gabriel.forum.repository.CursoRepository;
import br.com.gabriel.forum.repository.TopicosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    TopicosRepository topicosRepository;

    @Autowired
    CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDto> listar(String nomeCurso){

        if(nomeCurso == null){

            List<Topico> topicos = topicosRepository.findAll();
            return TopicoDto.converter(topicos);
        }

        List<Topico> topicos = topicosRepository.findByCursoNome(nomeCurso);

        return TopicoDto.converter(topicos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> buscarPeloId (@PathVariable Long id){

        Optional<Topico> topico = topicosRepository.findById(id);
       return topico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping
    @Transactional
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder UriBuilder){

        Topico topico = form.converter(cursoRepository);
        topicosRepository.save(topico);

        URI uri = UriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

        return ResponseEntity.created(uri).body(new TopicoDto(topico));

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){

        Optional<Topico> topico = topicosRepository.findById(id);
        if(topico.isPresent()){
        Topico t = form.atualizar(id,topicosRepository);
        return ResponseEntity.ok(new TopicoDto(t));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Topico> deletar(@PathVariable Long id){

       Optional<Topico> topico = topicosRepository.findById(id);
        if(topico.isPresent()){

            topicosRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return  ResponseEntity.notFound().build();

    }

}
