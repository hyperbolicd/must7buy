package com.cathy.shopping.controller;

import com.cathy.shopping.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void whenAnonymousAccessEmployees_thenIsUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenAnonymousAccessEmployeesLogin_thenIsOk() throws Exception {
        String user = "{\"username\":\"E2025004\",\"password\":\"123456\"}";
        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/employees/login")
                        .header("Content-Type", ContentType.APPLICATION_JSON)
                        .content(user))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"EMPLOYEE"})
    @Test
    void whenUserAccessEmployees_thenIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"EMPLOYEE"})
    @Test
    void whenUserDeleteEmployees_thenIsForbidden() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/4"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = {"ADMIN"})
    @Test
    void whenAdminDeleteEmployees_thenIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/4"))
                .andDo(print())
                .andExpect(status().isNoContent()); // postman 204, but was:<403> ??
    }

//    @Autowired
//    private ObjectMapper mapper;

    @WithMockUser(authorities = {"EMPLOYEE"})
    @Test
    void testCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setDisplayName("Test User");
        employee.setEmail("test.user@example.com");

        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // all
        mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL); // recommend: 可以被 @JsonInclude 覆蓋
        String jsonStr = mapper.writeValueAsString(employee);
        jsonStr = jsonStr.replace("}", ",\"password\":\"password\"}");
        System.out.println("ObjectMapper===" + jsonStr);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStr))
                .andExpect(status().isCreated());
    }

}
