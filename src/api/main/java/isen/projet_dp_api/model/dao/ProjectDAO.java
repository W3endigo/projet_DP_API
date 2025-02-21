package isen.projet_dp_api.model.dao;

import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Data
@Entity
@NoArgsConstructor
@Table(name = "project")
public class ProjectDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation pour MySQL
    private Integer id;

    private String description;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date start_date;

    private Date end_date;


    @ManyToOne
    @JoinColumn(name = "email_chef_project", referencedColumnName = "email")
    private UserDAO email_chef_project;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ParticipantDAO> participants = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectCompaniesDAO> companies = new HashSet<>();

    public ProjectDAO(ProjectDTO projectDTO, String email) {
        this.description = projectDTO.getDescription();
        this.title = projectDTO.getTitle();
        this.status = projectDTO.getStatus();
        this.start_date = projectDTO.getStart_date();
        this.end_date = projectDTO.getEnd_date();
        this.email_chef_project = new UserDAO(email);
    }

    public void addParticipant(ParticipantDAO participant) {
        participants.add(participant);
        participant.setProject(this);
    }

    public void addCompagnie(ProjectCompaniesDAO companie) {
        companies.add(companie);
        companie.setProject(this);
    }
}
