package de.fraunhofer.polycare.services;

import de.fraunhofer.polycare.exception.ResourceNotFoundException;
import de.fraunhofer.polycare.models.CareplanTask;
import de.fraunhofer.polycare.persistence.CareplanTaskRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by aw3s0 on 8/27/2017.
 */
public class CareplanTaskServiceTest {
    @Mock
    CareplanTaskRepository repository;

    CareplanTaskService service;

    List<CareplanTask> many;
    CareplanTask one;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new CareplanTaskService(repository);

        // FIXTURES
        many = Arrays.asList(
                new CareplanTask(1, "test1", null, null, "once a day", null),
                new CareplanTask(2, "test2", null, null, "twice a day", null)
        );

        one = new CareplanTask(1, "test", null, null, "once a day", null);
    }

    @Test
    public void getAll() throws Exception {
        when(repository.findAll()).thenReturn(many);

        List<CareplanTask> tasks = service.getAll();
        assertThat(tasks.size(), is(2));
        List<Integer> ids = tasks.stream().map(CareplanTask::getId).collect(toList());

        assertThat(ids, containsInAnyOrder(1, 2));
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void getCareplanTask() throws Exception {
        when(repository.exists(one.getId())).thenReturn(true);
        when(repository.findOne(one.getId())).thenReturn(one);

        CareplanTask fetched = service.getCareplanTask(one.getId());
        assertThat(fetched.getId(), is(fetched.getId()));

        verify(repository, times(1)).exists(one.getId());
        verify(repository, times(1)).findOne(one.getId());
        verifyNoMoreInteractions(repository);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getCodeWhen404() throws Exception {
        when(repository.exists(one.getId())).thenReturn(false);
        when(repository.findOne(one.getId())).thenReturn(null);

        service.getCareplanTask(one.getId());
    }

    @Test
    public void createCareplanTask() throws Exception {
        when(repository.exists(one.getId())).thenReturn(false);
        when(repository.save(one)).thenReturn(one);

        service.createCareplanTask(one);

        verify(repository, times(1)).exists(one.getId());
        verify(repository, times(1)).save(one);
    }

    @Test
    public void deleteCareplanTask() throws Exception {
        when(repository.exists(one.getId())).thenReturn(true);
        doNothing().when(repository).delete(one.getId());

        service.deleteCareplanTask(one.getId());

        verify(repository, times(1)).exists(one.getId());
        verify(repository, times(1)).delete(one.getId());
    }

    @Test
    public void exists() throws Exception {
        when(repository.exists(one.getId())).thenReturn(true);

        service.exists(one.getId());

        verify(repository, times(1)).exists(one.getId());
    }

    @Test
    public void update() throws Exception {
        when(repository.exists(one.getId())).thenReturn(true);
        when(repository.save(one)).thenReturn(one);

        service.updateCareplanTask(one);

        verify(repository, times(1)).exists(one.getId());
        verify(repository, times(1)).save(one);
    }
}
