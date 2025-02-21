package isen.projet_dp_api.model.dao;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Column;
import lombok.Data;
import java.io.Serializable;

@Data
@Embeddable
public class ParticipantId implements Serializable {

    @Column(name = "id")
    private Integer projectId;

    @Column(name = "email")
    private String email;

}
