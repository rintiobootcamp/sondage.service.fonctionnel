/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.services;

import java.util.HashMap;

/**
 *
 * @author G3A-GROUP
 */
public class StatQuestion {
    private Long nombreReponse;
    private HashMap<String,Long> valeurReponse;
    private HashMap<String,Double> pourcentageValeurReponse;

    public Long getNombreReponse() {
        return nombreReponse;
    }

    public void setNombreReponse(Long nombreReponse) {
        this.nombreReponse = nombreReponse;
    }

    public HashMap<String, Long> getValeurReponse() {
        return valeurReponse;
    }

    public void setValeurReponse(HashMap<String, Long> valeurReponse) {
        this.valeurReponse = valeurReponse;
    }

    public HashMap<String, Double> getPourcentageValeurReponse() {
        return pourcentageValeurReponse;
    }

    public void setPourcentageValeurReponse(HashMap<String, Double> pourcentageValeurReponse) {
        this.pourcentageValeurReponse = pourcentageValeurReponse;
    }
    
    
    
}
