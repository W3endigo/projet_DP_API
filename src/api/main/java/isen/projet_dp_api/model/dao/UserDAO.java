package isen.projet_dp_api.model.dao;

import isen.projet_dp_api.model.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class UserDAO {

    @Id
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @ManyToOne
    @JoinColumn(name = "name", referencedColumnName = "name")
    private CompanyDAO name;

    public UserDAO(UserDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.name = userDTO.getCompany() != null ? new CompanyDAO(userDTO.getCompany()) : null;
    }
}
