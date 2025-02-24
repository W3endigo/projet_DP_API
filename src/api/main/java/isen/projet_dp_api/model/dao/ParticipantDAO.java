package isen.projet_dp_api.model.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "participant")
public class ParticipantDAO {

    @EmbeddedId
    private ParticipantId id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "id")
    private ProjectDAO project;

    @ManyToOne
    @MapsId("email")
    @JoinColumn(name = "email")
    private UserDAO user;
}
