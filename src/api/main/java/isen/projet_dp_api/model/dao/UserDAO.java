package isen.projet_dp_api.model.dao;

import isen.projet_dp_api.model.dto.RegisterDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipantDAO> participants = new ArrayList<>();

    public UserDAO(RegisterDTO userDTO) {
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.name = userDTO.getCompany() != null ? new CompanyDAO(userDTO.getCompany()) : null;
    }

    public UserDAO(@NonNull String email) {
        this.email = email;
    }

    public void addParticipant(ParticipantDAO participant) {
        participants.add(participant);
        participant.setUser(this);
    }

    public void removeParticipant(ParticipantDAO participant) {
        participants.remove(participant);
        participant.setUser(null);
    }
}
