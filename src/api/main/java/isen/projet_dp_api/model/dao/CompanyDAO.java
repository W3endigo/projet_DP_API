package isen.projet_dp_api.model.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
@Table(name = "company")
public class CompanyDAO {

    @Id
    private String name;

    public CompanyDAO(@NonNull String name) {
        this.name = name;
    }

}