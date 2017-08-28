package de.fraunhofer.polycare.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.Set;

/**
 * Created by aw3s0 on 8/26/2017.
 * Tasks in Careplan that are followed by patients
 */
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "careplan_task")
public class CareplanTask {
    @Id
    @GeneratedValue
    @Column(name = "careplan_task_id",
            insertable = false,
            unique = true,
            nullable = false)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @NotNull(message = "error.title.notnull")
    @Size(min = 1, max = 30, message = "error.title.size")
    private String title;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date start;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    private Date end;

    @Getter
    @Setter
    private String posology;

    @OneToMany(
            mappedBy = "careplanTask",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    @Getter
    @Setter
    private Set<Medication> medication;

    @Override
    public String toString() {
        return "CareplanTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", posology='" + posology + '\'' +
                '}';
    }
}
