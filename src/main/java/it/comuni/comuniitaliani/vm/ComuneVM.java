package it.comuni.comuniitaliani.vm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ComuneVM {
    private Integer codice;
    private String nome;

    private ZonaVM zona;
    private RegioneVM regione;
    private ProvinciaVM provincia;

    private String sigla;
    private String codiceCatastale;
    private Set<String> cap;
    private Integer popolazione;
}
