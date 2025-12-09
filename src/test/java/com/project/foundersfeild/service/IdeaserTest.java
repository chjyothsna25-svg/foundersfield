package com.project.foundersfeild.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.foundersfeild.model.ideas;
import com.project.foundersfeild.repository.idearepo;

public class IdeaserTest {

    @Mock
    private idearepo repo;

    @InjectMocks
    private ideaser service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetIdeas() {
        ideas idea = new ideas();
        idea.setIdeaId(1L);
        idea.setTitle("App Idea");
        idea.setDescription("Cool Idea");
        when(repo.findAll()).thenReturn(Arrays.asList(idea));

        List<ideas> result = service.getideas();
        assertEquals(1, result.size());
        assertEquals("App Idea", result.get(0).getTitle());
    }

    @Test
    void testGetIdeaById() {
        ideas idea = new ideas();
        idea.setIdeaId(2L);
        idea.setTitle("AI Tool");
        when(repo.findById(2L)).thenReturn(Optional.of(idea));

        ideas result = service.getidea(2L);
        assertNotNull(result);
        assertEquals("AI Tool", result.getTitle());
    }
}
