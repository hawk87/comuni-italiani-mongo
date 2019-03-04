package it.comuni.comuniitaliani.controller;

import it.comuni.comuniitaliani.mapper.Mapper;
import it.comuni.comuniitaliani.model.Comune;
import it.comuni.comuniitaliani.repository.ComuneRepository;
import it.comuni.comuniitaliani.vm.ComuneVM;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comuni")
public class ComuneController {

    private final ComuneRepository comuneRepository;
    private final Mapper<ComuneVM, Comune> comuneMapper;

    public ComuneController(ComuneRepository comuneRepository, Mapper<ComuneVM, Comune> comuneMapper) {
        this.comuneRepository = comuneRepository;
        this.comuneMapper = comuneMapper;
    }

    @GetMapping("")
    public Flux<ComuneVM> getAll(@RequestParam String orderBy, @RequestParam Direction type) {
        return comuneRepository
                .findAll(Sort.by(type, orderBy))
                .map(comuneMapper::dto2vm);
    }

    @GetMapping("/{codice}")
    public Mono<ResponseEntity<ComuneVM>> get(@PathVariable("codice") Integer codice) {
        return comuneRepository.findById(codice)
                .map(comuneMapper::dto2vm)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public Mono<ComuneVM> add(@Valid @RequestBody ComuneVM vm) {
        final Comune comune = comuneMapper.vm2dto(vm);
        return comuneRepository
                .save(comune)
                .map(comuneMapper::dto2vm);
    }

    @PostMapping("/all")
    public Flux<ComuneVM> addAll(@Valid @RequestBody List<ComuneVM> comuni) {
        return comuneRepository
                .saveAll(comuni.stream()
                        .map(comuneMapper::vm2dto)
                        .collect(Collectors.toList()))
                .map(comuneMapper::dto2vm);
    }

    @PutMapping("/{codice}")
    public Mono<ResponseEntity<ComuneVM>> update(@PathVariable("codice") Integer codice,
                                                 @Valid @RequestBody ComuneVM vm) {
        final Comune comune = comuneMapper.vm2dto(vm);
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
                .map(comuneMapper::dto2vm)
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
    public Flux<ComuneVM> streamAll() {
        return comuneRepository
                .findAll()
                .map(comuneMapper::dto2vm);
    }
}
