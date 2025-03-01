package isen.projet_dp_api.model.dao;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Log4j2
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

    public ProjectDAO(ProjectDTO projectDTO, UserDAO user) {
        this.description = projectDTO.getDescription();
        this.title = projectDTO.getTitle();
        this.status = projectDTO.getStatus();
        this.start_date = projectDTO.getStart_date();
        this.end_date = projectDTO.getEnd_date();
        this.email = user;
    }

    public void addParticipant(UserDAO user) {
        if (user == null) return;

        boolean exists = participants.stream().anyMatch(p -> p.getUser().equals(user));
        if (exists) return;

        ParticipantId participantId = new ParticipantId();
        participantId.setProjectId(this.id);
        participantId.setEmail(user.getEmail());

        ParticipantDAO participant = new ParticipantDAO();
        participant.setId(participantId);
        participant.setProject(this);
        participant.setUser(user);
        participants.add(participant);
        /*user.addParticipant(participant);*/
    }

    //FUTURE USAGE
    public void removeParticipant(UserDAO user) {
        if (user == null) return;

        participants.removeIf(participant -> {
            boolean toRemove = participant.getUser().equals(user);
            if (toRemove) {
                participant.setProject(null);
                participant.setUser(null);
            }
            return toRemove;
        });
    }


    public void addCompagnie(CompanyDAO company) {
        if (company == null) return;

        boolean exists = companies.stream().anyMatch(pc -> pc.getCompany().equals(company));
        if (exists) return;

        ProjectCompaniesId companiesId = new ProjectCompaniesId();
        companiesId.setProjectId(this.id);
        companiesId.setName(company.getName());

        ProjectCompaniesDAO projectCompany = new ProjectCompaniesDAO();
        projectCompany.setId(companiesId);
        projectCompany.setProject(this);
        projectCompany.setCompany(company);

        companies.add(projectCompany);
    }


    //FUTURE USAGE
    public void removeCompagnie(CompanyDAO company) {
        if (company == null) return;

        companies.removeIf(pc -> {
            boolean toRemove = pc.getCompany().equals(company);
            if (toRemove) {
                pc.setProject(null);
                pc.setCompany(null);
            }
            return toRemove;
        });
    }
}
