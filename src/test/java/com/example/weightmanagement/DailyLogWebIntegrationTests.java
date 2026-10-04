package com.example.weightmanagement;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.weightmanagement.repository.DailyLogRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // All test writes roll back, including when an assertion fails.
class DailyLogWebIntegrationTests {

    private static final String JQUERY = "/js/vendor/jquery-4.0.0.min.js";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DailyLogRepository repository;

    @Test
    void existingDailyLogFlowRendersThymeleafAndKeepsWorking() throws Exception {
        LocalDate date = LocalDate.of(2200, 1, 1);
        while (repository.existsById(date)) {
            date = date.plusDays(1);
        }
        String day = date.toString();

        mvc.perform(get("/daily-log/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("daily-log/new"))
                .andExpect(content().string(containsString("action=\"/daily-log\"")))
                .andExpect(content().string(containsString("src=\"" + JQUERY + "\"")))
                .andExpect(content().string(not(containsString("th:field"))));

        mvc.perform(post("/daily-log")
                .param("logDate", day).param("weightKg", "65.25")
                .param("sleepHours", "7.5").param("snackType", "phase-0-test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/daily-log"));
        repository.flush();
        assertEquals(0, new BigDecimal("65.25").compareTo(repository.findById(date).orElseThrow().getWeightKg()));

        mvc.perform(get("/daily-log"))
                .andExpect(status().isOk())
                .andExpect(view().name("daily-log/list"))
                .andExpect(content().string(containsString(day)))
                .andExpect(content().string(containsString("65.25")))
                .andExpect(content().string(containsString("src=\"" + JQUERY + "\"")))
                .andExpect(content().string(not(containsString("th:each"))));

        mvc.perform(get("/daily-log/search").param("startDate", day).param("endDate", day))
                .andExpect(status().isOk())
                .andExpect(view().name("daily-log/list"))
                .andExpect(content().string(containsString(day)))
                .andExpect(content().string(containsString("65.25")));

        mvc.perform(get("/daily-log/" + day + "/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("daily-log/edit"))
                .andExpect(content().string(containsString("value=\"65.25\"")))
                .andExpect(content().string(containsString("src=\"" + JQUERY + "\"")))
                .andExpect(content().string(not(containsString("th:field"))));

        mvc.perform(post("/daily-log/" + day + "/edit")
                .param("weightKg", "64.75").param("sleepHours", "8.0").param("snackType", "updated"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/daily-log"));
        repository.flush();
        var updated = repository.findById(date).orElseThrow();
        assertEquals(0, new BigDecimal("64.75").compareTo(updated.getWeightKg()));
        assertEquals("updated", updated.getSnackType());

        mvc.perform(post("/daily-log/" + day + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/daily-log"));
        repository.flush();
        assertFalse(repository.existsById(date));
    }

    @Test
    void jqueryIsServedLocally() throws Exception {
        mvc.perform(get(JQUERY))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("jQuery v4.0.0")));
    }

    @Test
    void missingRequiredFieldsStillRedirectToNewForm() throws Exception {
        mvc.perform(post("/daily-log"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/daily-log/new?error=logDateRequired"));
    }
}
