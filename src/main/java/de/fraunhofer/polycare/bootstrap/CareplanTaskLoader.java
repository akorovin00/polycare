package de.fraunhofer.polycare.bootstrap;

import de.fraunhofer.polycare.models.CareplanTask;
import de.fraunhofer.polycare.models.Medication;
import de.fraunhofer.polycare.persistence.CareplanTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Created by aw3s0 on 8/27/2017.
 * Loads initial data for careplan tasks
 */
@Component
@Slf4j
public class CareplanTaskLoader implements ApplicationListener<ContextRefreshedEvent> {
    private CareplanTaskRepository repository;

    @Autowired
    public void setRepository(CareplanTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("Bootstrapping spring app with sample data");

        CareplanTask task1 = new CareplanTask(1, "careplan1", null, null, "once a day", null);
        HashSet<Medication> medications1 = new HashSet<Medication>() {{
            add(new Medication(1, "Aspirin", task1));
            //add(new Medication(2, "Galoperidol", task1));
        }};
        task1.setMedication(medications1);

        CareplanTask task2 = new CareplanTask(2, "careplan2", null, null, "once a week", null);
        HashSet<Medication> medications2 = new HashSet<Medication>() {{
            add(new Medication(3, "Paracetomol", task2));
            //add(new Medication(4, "Brand new drug 1", task2));
        }};

        task2.setMedication(medications2);

        this.repository.save(task1);
        this.repository.save(task2);
    }
}
