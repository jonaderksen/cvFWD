package com.cvFWD.cvFWD.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.cvFWD.cvFWD.domain.Project;
import com.cvFWD.cvFWD.service.ProjectService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@RunWith(MockitoJUnitRunner.class)
public class ProjectControllerTest {

    private final static String EMAIL = "test@headfwd.com";
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    ProjectController projectController;
    private ProjectService projectServiceMock;

    @Before
    public void setUp() {
        projectServiceMock = mock(ProjectService.class);
        this.projectController = new ProjectController(projectServiceMock);
        //when(projectServiceMock.get(anyString())).thenReturn(List.of(mock(Project.class), mock(Project.class)));
    }

    @Test
    public void getProjecten_NotAcceptable_emailBlank() {
        ResponseEntity responseEntity = projectController.get("");
        assertThat(responseEntity.getStatusCode().is4xxClientError(), is(true));
    }

    @Test
    public void getProjecten_Acceptable_emailNotBlank() {
        ResponseEntity responseEntity = projectController.get(EMAIL);
        assertThat(responseEntity.getStatusCode().is2xxSuccessful(), is(true));
    }

    @Test
    public void getProjecten_404NoAccount_NoProjectsForEmail() {
        when(projectServiceMock.get(EMAIL)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "No account available"));

        // Assert
        exception.expect(HttpClientErrorException.class);
        exception.expectMessage("404");
        exception.expectMessage("account");

        // Act
        projectController.get(EMAIL);
    }

}