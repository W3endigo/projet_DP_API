package isen.projet_dp_api.model.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.ToString;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_companies")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"project", "company"})
@ToString(exclude = {"project"})
public class ProjectCompaniesDAO {

    @EmbeddedId
    private ProjectCompaniesId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "id")
    @JsonBackReference
    private ProjectDAO project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("name")
    @JoinColumn(name = "name")
    private CompanyDAO company;

    public ProjectCompaniesDAO(CompanyDAO company) {
        this.company = company;
    }
}
