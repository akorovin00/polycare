package de.fraunhofer.polycare.services;

import de.fraunhofer.polycare.models.CareplanTask;
import lombok.NonNull;

import java.util.List;

/**
 * Created by aw3s0 on 8/26/2017.
 * Service for handling tasks of a careplan
 */
public interface ICareplanTaskService {
    /**
     * Fetching all careplan tasks
     * @return
     */
    List<CareplanTask> getAll();

    /**
     * Gets a task of a careplan by a task id
     * @param id Task id
     * @return
     */
    CareplanTask getCareplanTask(@NonNull Integer id);

    /**
     * Creates a careplan task
     * @param task Task to be created
     */
    void createCareplanTask(@NonNull CareplanTask task);

    /**
     * Deletes a careplan task
     * @param id Task id
     */
    void deleteCareplanTask(@NonNull Integer id);

    /**
     * Checks whether a task exists
     * @param id Task id
     * @return
     */
    boolean exists(@NonNull Integer id);

    /**
     * Updates a careplan task
     * @param task Task of careplan with a new information
     */
    void updateCareplanTask(@NonNull CareplanTask task);
}
