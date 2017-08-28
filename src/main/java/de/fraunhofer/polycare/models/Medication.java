package de.fraunhofer.polycare.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by aw3s0 on 8/26/2017.
 * Entity that describes medication
 */
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "medication")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @NotNull(message = "error.title.notnull")
    @Size(min = 1, max = 30, message = "error.title.size")
    private String title;

    @ManyToOne
    @JoinColumn(name = "careplan_task_id", nullable = false)
    @Setter
    @XmlTransient
    private CareplanTask careplanTask;

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
