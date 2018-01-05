package com.bootcamp.service;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.QuestionCRUD;
import com.bootcamp.entities.*;
import com.bootcamp.services.QuestionService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author G3A-GROUP
 */

@RunWith(PowerMockRunner.class)
@WebMvcTest(value = QuestionService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest(QuestionCRUD.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public class QuestionServiceTest {
    private final Logger LOG = LoggerFactory.getLogger(QuestionServiceTest.class);

    @InjectMocks
    private QuestionService questionService;

    @Test
    public void getAllQuestion() throws Exception {
        List<Question> questions = loadDataQuestionFromJsonFile();
        PowerMockito.mockStatic(QuestionCRUD.class);
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.
                when(QuestionCRUD.read()).thenReturn(questions);
        List<Question> resultQuestions = questionService.readAll();
        Assert.assertEquals(questions.size(), resultQuestions.size());
        LOG.info(" get all question test done");

    }

    @Test
    public void create() throws Exception{
        List<Question> questions = loadDataQuestionFromJsonFile();
        Question question = questions.get(1);

        PowerMockito.mockStatic(QuestionCRUD.class);
        Mockito.
                when(QuestionCRUD.create(question)).thenReturn(true);
    }

//    @Test
//    public void getByCriteria() throws Exception{
//        Criterias criterias = new Criterias();
//        criterias.addCriteria(new Criteria(new Rule("entityType", "=", "PROJET"), "AND"));
//        criterias.addCriteria(new Criteria(new Rule("entityId", "=", 2), null));
//        List<Question> questions = loadDataQuestionFromJsonFile();
//        List<Question> questionList = getQuestions("SECTEUR",2);
//
//        PowerMockito.mockStatic(QuestionCRUD.class);
//        Mockito.
//                when(QuestionCRUD.read(criterias)).thenReturn(questionList);
//        Gson gson = new Gson();
//        for(Question current:questionList){
//            System.out.println(gson.toJson(current));
//        }
//       // questionList.forEach(System.out::print);
//        //System.out.println(questionList);
//    }

    @Test
    public void delete() throws Exception{
        List<Question> questions = loadDataQuestionFromJsonFile();
        Question question = questions.get(1);

        PowerMockito.mockStatic(QuestionCRUD.class);
        Mockito.
                when(QuestionCRUD.delete(question)).thenReturn(true);
    }

    @Test
    public void update() throws Exception{
        List<Question> questions = loadDataQuestionFromJsonFile();
        Question question = questions.get(1);

        PowerMockito.mockStatic(QuestionCRUD.class);
        Mockito.
                when(QuestionCRUD.update(question)).thenReturn(true);
    }


    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Question> loadDataQuestionFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile("data-json" + File.separator + "sondages.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Question>>() {
        }.getType();
        List<Question> questions = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return questions;
    }

//    private Secteur getSecteurById(int id) throws Exception {
//        List<Secteur> secteurs = loadDataSecteurFromJsonFile();
//        Secteur secteur = secteurs.stream().filter(item -> item.getId() == id).findFirst().get();
//
//        return secteur;
//    }
    
    public Question getQuestionById(int questionId) throws Exception {
        List<Question> questions = loadDataQuestionFromJsonFile();
        Question question = questions.stream().filter(item -> item.getId() == questionId).findFirst().get();

        return question;
    }
    
//    private List<Question> getAllQuestions(int questionId) throws Exception {
//        List<Question> questions = loadDataQuestionFromJsonFile();
//        List<Question> result = questions.stream().filter(item -> item. getEntityType().equals(questionId)==entityId).collect(Collectors.toList());
//
//        return result;
//    }
    
    private List<Question> getQuestions(String entityType,int entityId) throws Exception {
        List<Question> questions = loadDataQuestionFromJsonFile();
        List<Question> result = questions.stream().filter(item -> item.getEntityType().equals(entityType) && item.getEntityId()==entityId).collect(Collectors.toList());

        return result;
    }


    

}