package com.bootcamp.controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;

import com.bootcamp.entities.Question;
import com.bootcamp.services.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
 *
 * Created by Ibrahim on 12/5/17.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(value = QuestionController.class, secure = false)
@ContextConfiguration(classes={Application.class})
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;


    @Test
    public void getQuestions() throws Exception{
        List<Question> questions =  loadDataQuestionFromJsonFile();
        System.out.println(questions.size());
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        when(questionService.readAll()).thenReturn(questions);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/sondages")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

    }

    @Test
    public void getQuestionByIdForController() throws Exception{
        int id = 1;
        Question question = getQuestionById(id);

        when(questionService.read(id)).thenReturn(question);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/questions/{id}",id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for get a question by id in question controller done *******************");

    }

    @Test
    public void CreateQuestion() throws Exception{
        Question question = getQuestionById(2);

//        when(questionService.create(question)).thenReturn(question);

        RequestBuilder requestBuilder =
                post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectToJson(question));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for create question in question controller done *******************");

    }


    @Test
    public void deleteQuestion() throws Exception{
        int id = 3;
        Question question = getQuestionById(id);
        when(questionService.exists(id)).thenReturn(true);
        when(questionService.delete(question)).thenReturn(question);

        RequestBuilder requestBuilder =
                delete("/questions/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete question in question controller done *******************");


    }
    private List<Question> getQuestionsByEnity(String entityType,int entityId) throws Exception {
        List<Question> questions = loadDataQuestionFromJsonFile();
        List<Question> result = questions.stream().filter(item -> item.getEntityType().equals(entityType) && item.getEntityId()==entityId).collect(Collectors.toList());

        return result;
    }

    private Question getQuestionById(int id) throws Exception {
        List<Question> questions = loadDataQuestionFromJsonFile();
        Question question = questions.stream().filter(item -> item.getId() == id).findFirst().get();
        return question;
    }

    public static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if(!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Question> loadDataQuestionFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "questions.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Question>>() {
        }.getType();
        List<Question> questions = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return questions;
    }
}