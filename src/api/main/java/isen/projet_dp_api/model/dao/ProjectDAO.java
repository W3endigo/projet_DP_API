package isen.projet_dp_api.model.dao;

import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@Entity
@NoArgsConstructor
@Table(name = "project")
public class ProjectDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation pour MySQL
    private Long id;

    private String participants;

    private String description;

    private String companies;

    private String title;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date start_date;

    private Date end_date;


    @ManyToOne
    @JoinColumn(name = "email_chef_project", referencedColumnName = "email")
    private UserDAO email_chef_project;

    public ProjectDAO(ProjectDTO projectDTO, String email) {
        this.participants = projectDTO.getParticipants();
        this.description = projectDTO.getDescription();
        this.companies = projectDTO.getCompanies();
        this.title = projectDTO.getTitle();
        this.status = projectDTO.getStatus();
        this.start_date = projectDTO.getStart_date();
        this.end_date = projectDTO.getEnd_date();
        this.email_chef_project = new UserDAO(email);
        System.out.println(this.email_chef_project + "" + this.id);
    }
}
