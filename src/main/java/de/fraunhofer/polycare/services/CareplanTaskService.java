package de.fraunhofer.polycare.services;

import com.google.common.collect.ImmutableList;
import de.fraunhofer.polycare.exception.ResourceNotFoundException;
import de.fraunhofer.polycare.models.CareplanTask;
import de.fraunhofer.polycare.persistence.CareplanTaskRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/**
 * Created by aw3s0 on 8/26/2017.
 * Implementation of CareplanTask Service
 */
@Service
@Slf4j
public class CareplanTaskService implements ICareplanTaskService {
    CareplanTaskRepository repository;

    public CareplanTaskService(CareplanTaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CareplanTask> getAll() {
        return IteratorUtils.toList(repository.findAll().iterator())
                .stream()
                .collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    @Override
    public CareplanTask getCareplanTask(@NonNull Integer id) {
        if (!repository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        log.info("Fetching a careplan with id: " + id);
        return repository.findOne(id);
    }

    @Override
    public void createCareplanTask(@NonNull CareplanTask task) {
        if (repository.exists(task.getId())) {
            throw new DuplicateKeyException("Entry already exists");
        }

        initTasksForCareplan(task);

        log.info("Creating a careplan with id: " + task.getId());
        repository.save(task);
    }

    @Override
    public void deleteCareplanTask(@NonNull Integer id) {
        if (!repository.exists(id)) {
            throw new ResourceNotFoundException();
        }

        log.info("Removing a task with id:" + id);
        repository.delete(id);
    }

    @Override
    public boolean exists(@NonNull Integer id) {
        return repository.exists(id);
    }

    @Override
    public void updateCareplanTask(@NonNull CareplanTask task) {
        if (!repository.exists(task.getId())) {
            throw new ResourceNotFoundException();
        }

        initTasksForCareplan(task);

        log.info("Updating a task with id:" + task.getId());
        repository.save(task);
    }

    /**
     * Initializes a careplanTask field for every medication in task
     * @param task
     */
    private void initTasksForCareplan(@NonNull CareplanTask task) {
        if (task.getMedication() != null && task.getMedication().size() > 0) {
            task.getMedication().stream().forEach(medication -> {
                medication.setCareplanTask(task);
            });
        }
    }
}
