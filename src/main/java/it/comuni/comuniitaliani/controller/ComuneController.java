package it.comuni.comuniitaliani.controller;

import it.comuni.comuniitaliani.model.Comune;
import it.comuni.comuniitaliani.repository.ComuneRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comuni")
public class ComuneController {

    private final ComuneRepository comuneRepository;

    public ComuneController(ComuneRepository comuneRepository) {this.comuneRepository = comuneRepository;}

    @GetMapping("")
    public Flux<Comune> getAll(@RequestParam String orderBy, @RequestParam Direction type) {
        return comuneRepository.findAll(Sort.by(type, orderBy));
    }

    @GetMapping("/{codice}")
    public Mono<ResponseEntity<Comune>> get(@PathVariable("codice") Integer codice) {
        return comuneRepository.findById(codice)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public Mono<Comune> add(@Valid @RequestBody Comune comune) {
        return comuneRepository.save(comune);
    }

    @PostMapping("/all")
    public Flux<Comune> addAll(@Valid @RequestBody List<Comune> comuni) {
        return comuneRepository.saveAll(comuni);
    }

    @PutMapping("/{codice}")
    public Mono<ResponseEntity<Comune>> update(@PathVariable("codice") Integer codice,
                                               @Valid @RequestBody Comune comune) {
        return comuneRepository.findById(codice)
                .flatMap(existingComune -> {
                    existingComune.setNome(comune.getNome());
                    existingComune.setZona(comune.getZona());
                    existingComune.setRegione(comune.getRegione());
                    existingComune.setProvincia(comune.getProvincia());
                    existingComune.setSigla(comune.getSigla());
                    existingComune.setCodiceCatastale(comune.getCodiceCatastale());
                    existingComune.setCap(comune.getCap());
                    existingComune.setPopolazione(comune.getPopolazione());
                    return comuneRepository.save(existingComune);
                })
                .map(updatedComune -> new ResponseEntity<>(updatedComune, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{codice}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") Integer codice) {
        return comuneRepository.findById(codice)
                .flatMap(existingComune ->
                        comuneRepository.delete(existingComune)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Sent to the client as Server Sent Events
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Comune> streamAll() {
        return comuneRepository.findAll();
    }
}
