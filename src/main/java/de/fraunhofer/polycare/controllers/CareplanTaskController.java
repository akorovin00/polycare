package de.fraunhofer.polycare.controllers;

import de.fraunhofer.polycare.models.CareplanTask;
import de.fraunhofer.polycare.services.ICareplanTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by aw3s0 on 8/26/2017.
 * Main CRUD controller to work with tasks of careplan
 */
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/careplan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CareplanTaskController {
    private ICareplanTaskService service;

    @Autowired
    public CareplanTaskController(ICareplanTaskService service) {
        this.service = service;
    }

    /**
     * Fetches all available tasks
     * @return list of careplan tasks in a json format
     */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<CareplanTask> getAllTasks() {
        return service.getAll();
    }

    /**
     * Fetches a careplan task by id
     * @param id Careplan task id
     * @return Task
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getCareplanTaskById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.getCareplanTask(id), HttpStatus.OK);
    }

    /**
     * Removes a careplan task by an id
     * @param id Careplan task id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteCareplanTask(@PathVariable Integer id) {
        service.deleteCareplanTask(id);
    }

    /**
     * Creates a new careplan task
     * @param careplanTask
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createCareplanTask(@RequestBody CareplanTask careplanTask) {
        service.createCareplanTask(careplanTask);
    }

    /**
     * Update a careplan task
     * @param careplanTask
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCareplanTask(@RequestBody CareplanTask careplanTask) {
        service.updateCareplanTask(careplanTask);
    }
}
