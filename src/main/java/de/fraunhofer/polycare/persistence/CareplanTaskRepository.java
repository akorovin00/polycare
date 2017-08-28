package de.fraunhofer.polycare.persistence;

import de.fraunhofer.polycare.models.CareplanTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by aw3s0 on 8/26/2017.
 * CRUD repository for Careplan tasks
 */
@Repository
@Transactional
public interface CareplanTaskRepository extends CrudRepository<CareplanTask, Integer> {
}
