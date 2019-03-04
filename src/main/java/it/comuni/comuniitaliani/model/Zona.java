package it.comuni.comuniitaliani.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@NoArgsConstructor
public class Zona {
    @Id
    private Integer codice;
    private String nome;
}
