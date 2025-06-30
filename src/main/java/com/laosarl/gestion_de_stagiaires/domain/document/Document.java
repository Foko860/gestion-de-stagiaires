package com.laosarl.gestion_de_stagiaires.domain.document;

import com.laosarl.gestion_de_stagiaires.domain.common.EntityBase;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document extends EntityBase {
    private String originalFilename;
    private long fileSize;
    private String contentType;
}
