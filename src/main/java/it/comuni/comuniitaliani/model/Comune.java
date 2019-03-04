package it.comuni.comuniitaliani.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Document
@NoArgsConstructor
public class Comune {
    @Id
    private Integer codice;
    private String nome;

    private Zona zona;
    private Regione regione;
    private Provincia provincia;

    private String sigla;
    private String codiceCatastale;
    private Set<String> cap;
    private Integer popolazione;
}
