package com.islandcollaborative.creativeexchange.controllers;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void testUrls() throws Exception {
        //pages to render
        List<String> pages = Arrays.asList(
                //"/users/1","/posts/{postId}","/discover","/privacy",
                "/","/about","/login","/signup");
        List<String> pagesRequiringAuth = Arrays.asList(
                "/users/1/messages",
                "/profile","/messages");

        for (String url: pages) {
            mockMvc.perform(get(url))
                    .andExpect(content().string(containsString("<h1>CreativExchange</h1>")))
                    .andExpect(status().isOk());
        }

        for (String url: pagesRequiringAuth) {
            mockMvc.perform(get(url))
                    .andExpect(status().is3xxRedirection());
        }
    }

    @Test
    @WithMockUser
    public void requestProtectedUrlWithUser() throws Exception {
        List<String> pages = Arrays.asList(
                //"/users/1","/posts/{postId}","/discover","/privacy",
                "/","/about","/login","/signup");
        List<String> pagesRequiringAuth = Arrays.asList(
//                "/users/1/messages","/profile","/messages"
        );

        for (String url: pages) {
            mockMvc.perform(get(url))
                    .andExpect(content().string(containsString("<h1>CreativExchange</h1>")))
                    .andExpect(status().isOk());
        }

        for (String url: pagesRequiringAuth) {
            mockMvc.perform(get(url))
                    .andExpect(content().string(containsString("<h1>CreativExchange</h1>")))
                    .andExpect(status().isOk());
        }
    }
}
