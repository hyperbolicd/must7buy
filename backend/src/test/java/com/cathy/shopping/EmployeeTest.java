package com.cathy.shopping;

import com.cathy.shopping.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void whenAnonymousAccessEmployees_thenIsUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAnonymousAccessEmployeeCheckEmail_thenIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees/check-email").param("email", "hyperbolicd@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenUserAccessEmployees_thenIsForbidden() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").header("Authorization", "Basic dXNlcjp1c2Vy"))
                .andExpect(status().isForbidden());
    }

    @Test
    void whenAdminAccessEmployees_thenIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees").header("Authorization", "Basic YWRtaW46YWRtaW4="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Disabled
    @Test
    void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setUsername("acc");
        employee.setPassword("pss");
        employee.setFirstName("Foo");
        employee.setLastName("Bar");
        employee.setEmailId("foo.bar@example.com");

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        System.out.println("ObjectMapper===" + objectMapper.writeValueAsString(employee));

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated());
    }

}
