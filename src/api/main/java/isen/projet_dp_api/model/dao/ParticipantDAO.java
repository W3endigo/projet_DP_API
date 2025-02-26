package isen.projet_dp_api.model.dao;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "participant")
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"project", "user"})
@ToString(exclude = {"project"})
public class ParticipantDAO {

    @EmbeddedId
    private ParticipantId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "id")
    @JsonBackReference
    private ProjectDAO project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("email")
    @JoinColumn(name = "email")
    private UserDAO user;
}
