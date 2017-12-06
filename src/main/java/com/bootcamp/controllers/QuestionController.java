package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.ws.usecases.pivotone.QuestionWS;
import com.bootcamp.entities.Question;
import com.bootcamp.services.QuestionService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@RestController("QuestionController")
@RequestMapping("/questions")
@Api(value = "Question API", description = "Question API")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new question", notes = "Create a new question")
    public ResponseEntity<Question> create(@RequestBody @Valid Question question) throws SQLException {

        HttpStatus httpStatus = null;

            questionService.create(question);
            httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(question, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a question", notes = "Read a question")
    public ResponseEntity<List<QuestionWS>> read() throws SQLException {

        List<QuestionWS> questionWSs = new ArrayList<QuestionWS>();
        HttpStatus httpStatus = null;
            questionWSs = questionService.readAll();
            httpStatus = HttpStatus.OK;

        return new ResponseEntity<>(questionWSs, httpStatus);
    }
}
