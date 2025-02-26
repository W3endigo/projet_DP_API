package isen.projet_dp_api.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "project_companies")
public class ProjectCompaniesDAO {

    @EmbeddedId
    private ProjectCompaniesId id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "id")
    private ProjectDAO project;

    @ManyToOne
    @MapsId("name")
    @JoinColumn(name = "name")
    private CompanyDAO company;

    public ProjectCompaniesDAO(CompanyDAO company) {
        this.company = company;
    }
}
