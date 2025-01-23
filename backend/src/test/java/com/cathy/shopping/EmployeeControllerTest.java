package com.cathy.shopping;

import com.cathy.shopping.controller.EmployeeController;
import com.cathy.shopping.model.Employee;
import com.cathy.shopping.security.SecurityConfig;
import com.cathy.shopping.service.EmployeeService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
//@AutoConfigureMockMvc(addFilters = false)
@Import(SecurityConfig.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private EmployeeService service;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setUsername("acc");
        employee.setPassword("pss");
        employee.setFirstName("Foo");
        employee.setLastName("Bar");
        employee.setEmailId("foo.bar@example.com");
        when(service.createEmployee(employee)).thenReturn(employee);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("ObjectMapper===" + objectMapper.writeValueAsString(employee));

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

}
