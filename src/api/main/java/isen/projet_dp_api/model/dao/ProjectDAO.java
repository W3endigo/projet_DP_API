package isen.projet_dp_api.model.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Entity
@Table(name = "project")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(exclude = {"participants", "companies"})
@ToString(exclude = {"participants", "companies"})
public class ProjectDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date start_date;

    private Date end_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_chef_project", referencedColumnName = "email")
    private UserDAO email;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ParticipantDAO> participants = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProjectCompaniesDAO> companies = new ArrayList<>();

    public ProjectDAO(ProjectDTO projectDTO, String email) {
        this.description = projectDTO.getDescription();
        this.title = projectDTO.getTitle();
        this.status = projectDTO.getStatus();
        this.start_date = projectDTO.getStart_date();
        this.end_date = projectDTO.getEnd_date();
        this.email = new UserDAO(email);
    }

    public void addParticipant(ParticipantDAO participant) {
        participants.add(participant);
        participant.setProject(this);
    }

    public void addCompagnie(ProjectCompaniesDAO companie) {
        companies.add(companie);
        companie.setProject(this);
    }

    public void removeCompagnie(ProjectCompaniesDAO companie) {
        companies.remove(companie);
        companie.setProject(null);
    }

    public void removeParticipant(ParticipantDAO participant) {
        participants.remove(participant);
        participant.setProject(null);
    }
}
