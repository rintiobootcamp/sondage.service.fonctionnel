package com.bootcamp.controllers;

import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.entities.Secteur;
import com.bootcamp.services.ReponseService;
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

@RestController("SecteurController")
@RequestMapping("/secteurs")
@Api(value = "Secteur API", description = "Secteur API")
public class SecteurController {

    @Autowired
    ReponseService secteurService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(method = RequestMethod.POST,value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new Secteur", notes = "Create a new Secteur")
    public ResponseEntity<Secteur> create(@RequestBody @Valid Secteur secteur) {

        HttpStatus httpStatus = null;

        try {
            secteurService.create(secteur);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
            
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Secteur>(secteur, httpStatus);
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Update a new Secteur", notes = "Update a new Secteur")
    public ResponseEntity<Secteur> update(@RequestBody @Valid Secteur Secteur) {

        HttpStatus httpStatus = null;

        try {
            secteurService.update(Secteur);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Secteur>(Secteur, httpStatus);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Delete a Secteur", notes = "Delete a Secteur")
    public ResponseEntity<Secteur> delete(@PathVariable(name = "id") int id) {
        Secteur secteur = new Secteur();
        HttpStatus httpStatus = null;

        try {
            secteur = secteurService.delete(id);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Secteur>(secteur, httpStatus);
    }


    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a Secteur", notes = "Read a Secteur")
    public ResponseEntity<Secteur> read(@PathVariable(name = "id") int id) {

        Secteur secteur = new Secteur();
        HttpStatus httpStatus = null;

        try {
            secteur = secteurService.read(id);
            httpStatus = HttpStatus.OK;
        }catch (SQLException exception){
            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Secteur>(secteur, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Read a Secteur", notes = "Read a Secteur")
    public ResponseEntity<List<Secteur>> read() {
        List<Secteur> secteurs=new ArrayList<Secteur>();
        HttpStatus httpStatus = null;

        try {
             secteurs = secteurService.read(request);

            httpStatus = HttpStatus.OK;
        }catch (SQLException | IllegalAccessException | DatabaseException | InvocationTargetException exception){
            String errorMessage = exception.getMessage()==null?exception.getMessage():exception.getCause().getMessage();

            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<List<Secteur>>(secteurs, httpStatus);BignonFebron amuztadji
    }
}
