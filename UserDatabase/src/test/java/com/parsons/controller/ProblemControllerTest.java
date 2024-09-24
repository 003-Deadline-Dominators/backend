package com.parsons.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProblemController.class)
public class ProblemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        // Set up any required data or initialization here
    }

    @Test
    public void testValidInputs() throws Exception {
        // Test with valid variable1 and variable2 inputs
        mockMvc.perform(get("/problem")
                        .param("variable1", "validTopic")
                        .param("variable2", "validContext"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data").isString());
    }

    @Test
    public void testInvalidInputs() throws Exception {
        // Test with invalid variable1 and variable2 inputs
        mockMvc.perform(get("/problem")
                        .param("variable1", "invalidTopic")
                        .param("variable2", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    public void testEmptyInputs() throws Exception {
        // Test with empty variable1 and variable2 inputs
        mockMvc.perform(get("/problem")
                        .param("variable1", "")
                        .param("variable2", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testNullInputs() throws Exception {
        // Test with null values for variable1 and variable2 (spring handles null as missing parameter)
        mockMvc.perform(get("/problem")
                        .param("variable1", (String) null)
                        .param("variable2", (String) null))
                .andExpect(status().isBadRequest());
    }
}
