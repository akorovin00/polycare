package de.fraunhofer.polycare.controllers;

import com.google.common.collect.ImmutableList;
import de.fraunhofer.polycare.controllers.advice.AdviceRestHandler;
import de.fraunhofer.polycare.exception.ResourceNotFoundException;
import de.fraunhofer.polycare.models.CareplanTask;
import de.fraunhofer.polycare.services.CareplanTaskService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static de.fraunhofer.polycare.controllers.helpers.JSONConverter.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by aw3s0 on 8/27/2017.
 */
public class CareplanTaskControllerTest {
    @Mock
    CareplanTaskService service;

    CareplanTaskController controller;

    MockMvc mockMvc;

    List<CareplanTask> many;
    CareplanTask one;

    private static final String API = "/api/careplan/";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new CareplanTaskController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(
                new AdviceRestHandler()).build();

        // FIXTURES
        many = ImmutableList.of(
                new CareplanTask(1, "test1", null, null, "once a day",null),
                new CareplanTask(2, "test2", null, null, "twice a day", null)
        );

        one = new CareplanTask(1, "test", null, null, "once a day", null);
    }

    @Test
    public void getAllTasks() throws Exception {
        when(service.getAll()).thenReturn(many);

        mockMvc.perform(get(API))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("test1")))
                .andExpect(jsonPath("$[1].title", is("test2")));
        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getCareplanTaskById() throws Exception {
        when(service.getCareplanTask(1)).thenReturn(one);

        mockMvc.perform(get(API + "{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")));
        verify(service, times(1)).getCareplanTask(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getCareplanFail404ById() throws Exception {
        when(service.getCareplanTask(1)).thenThrow(new ResourceNotFoundException());
        mockMvc.perform(get(API + "{id}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
        verify(service, times(1)).getCareplanTask(1);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteCareplanTask() throws Exception {
        doNothing().when(service).deleteCareplanTask(one.getId());
        mockMvc.perform(delete(API + "{id}", one.getId()))
                .andExpect(status().isOk());
        verify(service, times(1)).deleteCareplanTask(one.getId());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void deleteCareplanTaskWhichNotFound() throws Exception {
        doThrow(new ResourceNotFoundException()).when(service).deleteCareplanTask(one.getId());
        mockMvc.perform(delete(API + "{id}", one.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Sorry the resource was not found.")));
        verify(service, times(1)).deleteCareplanTask(one.getId());
        verifyNoMoreInteractions(service);
    }

    @Test
    public void createCareplanTask() throws Exception {
        doNothing().when(service).createCareplanTask(one);
        mockMvc.perform(post(API).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(one)))
                .andExpect(status().isCreated());
        verify(service, times(1)).createCareplanTask(one);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void createCareplanTaskWhichAlreadyExists() throws Exception {
        doThrow(new DuplicateKeyException("Entry already exists")).when(service).createCareplanTask(one);
        mockMvc.perform(post(API).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(one)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("Sorry the resource already exists.")));;
        verify(service, times(1)).createCareplanTask(one);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void createCareplanTaskWithWrongFields() throws Exception {
        doThrow(new DataIntegrityViolationException("Title is empty")).when(service).createCareplanTask(one);
        mockMvc.perform(post(API).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(one)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("The data is not valid")));;
        verify(service, times(1)).createCareplanTask(one);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateCareplanTask() throws Exception {
        doNothing().when(service).updateCareplanTask(one);
        mockMvc.perform(put(API).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(one)))
                .andExpect(status().isOk());
        verify(service, times(1)).updateCareplanTask(one);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateCareplanTaskThatDoesNotExists() throws Exception {
        doThrow(new ResourceNotFoundException()).when(service).updateCareplanTask(one);
        mockMvc.perform(put(API).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(one)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail", is("Sorry the resource was not found.")));;
        verify(service, times(1)).updateCareplanTask(one);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void updateCareplanTaskWithWrongFields() throws Exception {
        doThrow(new DataIntegrityViolationException("Title is empty")).when(service).updateCareplanTask(one);
        mockMvc.perform(put(API).contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(asJsonString(one)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail", is("The data is not valid")));;
        verify(service, times(1)).updateCareplanTask(one);
        verifyNoMoreInteractions(service);
    }
}
