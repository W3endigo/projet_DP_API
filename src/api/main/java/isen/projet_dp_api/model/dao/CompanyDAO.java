package isen.projet_dp_api.model.dao;

import isen.projet_dp_api.model.dto.CompanyDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "company")
@ToString(exclude = {"projects"})
public class CompanyDAO {

    @Id
    private String name;

    public CompanyDAO(@NonNull String name) {
        this.name = name;
    }

    public CompanyDAO(@NonNull CompanyDTO companyDTO) {
        this.name = companyDTO.getName();
    }

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectCompaniesDAO> projects = new ArrayList<>();

}