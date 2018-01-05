package com.bootcamp.integration;

import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Question;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.jayway.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * <h2> The integration test for Question controller</h2>
 * <p>
 * In this test class, the methods :
 * <ul>
 * <li>create a question </li>
 * <li>get one question by it's id</li>
 * <li>get all question</li>
 * <li>And update a question have been implemented</li>
 * </ul>
 * before getting started , make sure , the question fonctionnel service is
 * deploy and running as well. you can also test it will the online ruuning
 * service As this test interact directly with the local database, make sure
 * that the specific database has been created and all it's tables. If you have
 * data in the table,make sure that before creating a data with it's id, do not
 * use an existing id.
 * </p>
 */
public class QuestionControllerIntegrationTest {

    private static Logger logger = LogManager.getLogger(QuestionControllerIntegrationTest.class);

    /**
     * The Base URI of question fonctionnal service, it can be change with
     * the online URIof this service.
     */
//    private String BASE_URI = "http://104.131.60.151:8083/sondage";
    private String BASE_URI = "http://localhost:8083/sondage";

    /**
     * The path of the Question controller, according to this controller
     * implementation
     */
    private String QUESTION_PATH = "/sondages";

    /**
     * This ID is initialize for create , getById, and update method, you have
     * to change it if you have a save data on this ID otherwise a error or
     * conflit will be note by your test.
     */
    private int questionId = 0;

    /**
     * This entityType is initialize for stats method, you have
     * to change it if you have a save data on this ID otherwise a error or
     * conflit will be note by your test.
     */
    private String entityType = "PROJET";

    /**
     * This ID is initialize for create , getById, and update method, you have
     * to change it if you have a save data on this ID otherwise a error or
     * conflit will be note by your test.
     */
    private int entityId = 1;


    /**
     * The startDate initialize for statistique method, you have
     * make sure that this is correct in one of the value in database
     */
    private long startDate = 1511907379;

    /**
     * The endDate initialize for statistique method, you have
     * make sure that this is correct in one of the value in database
     */
    private long endDate = 1511907390;


    /**
     * This method create a new question with the given id
     *
     * @see Question#id
     * <b>you have to chenge the name of the question if this name already
     * exists in the database
     * @see Question#getContenu() else, the question will be created but not
     * wiht the given ID. and this will accure an error in the getById and
     * update method</b>
     * Note that this method will be the first to execute If every done , it
     * will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 0, groups = {"QuestionTest"})
    public void createQuestion() throws Exception {
        String createURI = BASE_URI + QUESTION_PATH;
        Question question = getQuestionById(4);
        question.setId(questionId);
        Gson gson = new Gson();
        String questionData = gson.toJson(question);
        Response response = given()
                .log().all()
                .contentType("application/json")
                .body(questionData)
                .expect()
                .when()
                .post(createURI);

        questionId = gson.fromJson(response.getBody().print(), Question.class).getId();

        logger.debug(questionId);
        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200);

    }

    /**
     * This method get a question with the given id
     *
     * @see Question#id
     * <b>
     * If the given ID doesn't exist it will log an error
     * </b>
     * Note that this method will be the second to execute If every done , it
     * will return a 200 httpStatus code
     * @throws Exception
     */
    @Test(priority = 1, groups = {"QuestionTest"})
    public void getQuestionByIdTest() throws Exception {

        String getQuestionById = BASE_URI + QUESTION_PATH +"/"+ questionId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getQuestionById);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200);

    }

    /**
     * Get the statistics of the given entity type
     * <b>
     * the comments must exist in the database
     * </b>
     * Note that this method will be the third to execute If every done , it
     * will return a 200 httpStatus code
     *
     * @throws Exception
     */
    @Test(priority = 2, groups = {"QuestionTest"})
    public void statsQuestion() throws Exception {
        String statsURI = BASE_URI + QUESTION_PATH +"/stats/"+entityType;
        Response response = given()
                .queryParam( "startDate",startDate)
                .queryParam( "endDate",endDate )
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(statsURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200);

    }

    /**
     * Get All the questions in the database If every done , it will return a
     * 200 httpStatus code
     *
     * @throws Exception
     */
    @Test(priority = 3, groups = {"QuestionTest"})
    public void getAllQuestions() throws Exception {
        String getAllQuestionURI = BASE_URI + QUESTION_PATH;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getAllQuestionURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200);

    }

    /**
     * Delete a question for the given ID will return a 200 httpStatus code
     * if OK
     *
     * @throws Exception
     */
    @Test(priority = 4, groups = {"QuestionTest"})
    public void deleteQuestion() throws Exception {

        String deleteQuestionUI = BASE_URI + QUESTION_PATH + "/" + questionId;

        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .delete(deleteQuestionUI);

        Assert.assertEquals(response.statusCode(), 200);

    }

    /**
     * Get All the questions related to a specify entity in the database If
     * every done , it will return a 200 httpStatus code
     *
     * @throws Exception
     */
    @Test(priority = 5, groups = {"QuestionTest"})
    public void getQuestionsByEntity() throws Exception {
        String getQuestionsByEntityURI = BASE_URI + QUESTION_PATH +"/"+ entityType+ "/"+ entityId;
        Response response = given()
                .log().all()
                .contentType("application/json")
                .expect()
                .when()
                .get(getQuestionsByEntityURI);

        logger.debug(response.getBody().prettyPrint());

        Assert.assertEquals(response.statusCode(), 200);

    }

    /**
     * Convert a relative path file into a File Object type
     *
     * @param relativePath
     * @return File
     * @throws Exception
     */
    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    /**
     * Get on question by a given ID from the List of questions
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Question getQuestionById(int id) throws Exception {
        List<Question> questions = loadDataQuestionFromJsonFile();
        Question question = questions.stream().filter(item -> item.getId() == id).findFirst().get();

        return question;
    }

    /**
     * Convert a questions json data to a question objet list this json
     * file is in resources
     *
     * @return a list of question in this json file
     * @throws Exception
     */
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
