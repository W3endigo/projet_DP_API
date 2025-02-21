package isen.projet_dp_api.model.dao;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ProjectCompaniesId implements Serializable {

    @Column(name = "name")
    private String name;

    @Column(name = "id")
    private Integer projectId;
}
