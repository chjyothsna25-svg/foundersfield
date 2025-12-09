package com.project.foundersfeild.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.foundersfeild.model.ideas;
import com.project.foundersfeild.service.ideaser;

@WebMvcTest(ideacon.class)
public class IdeaconTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ideaser ser;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetIdeas() throws Exception {
        ideas idea1 = new ideas();
        idea1.setIdeaId(1L);
        idea1.setTitle("AI App");
        idea1.setDescription("Smart AI idea");

        List<ideas> ideaList = Arrays.asList(idea1);

        when(ser.getideas()).thenReturn(ideaList);

        mockMvc.perform(MockMvcRequestBuilders.get("/ideas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("AI App"));
    }

    @Test
    public void testPostIdea() throws Exception {
        ideas idea = new ideas();
        idea.setIdeaId(1L);
        idea.setTitle("New Idea");
        idea.setDescription("Some Description");

        mockMvc.perform(MockMvcRequestBuilders.post("/ideas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idea)))
                .andExpect(status().isOk());
    }
}
