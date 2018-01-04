/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.services;

import com.bootcamp.commons.ws.usecases.pivotone.TypeReponseWS;

/**
 *
 * @author G3A-GROUP
 */
public class StatQuestion {
    private Long nombreReponse;
    private Long nombreReponseValeur1;
    private Long nombreReponseValeur2;
    private Long nombreReponseValeur3;
    private double pourcentageNombreReponseValeur1;
    private double pourcentageNombreReponseValeur2;
    private double pourcentageNombreReponseValeur3;
    
//    TypeReponseWS typeReponseWS;

    public Long getNombreReponse() {
        return nombreReponse;
    }

    public void setNombreReponse(Long nombreReponse) {
        this.nombreReponse = nombreReponse;
    }

    public Long getNombreReponseValeur1() {
        return nombreReponseValeur1;
    }

    public void setNombreReponseValeur1(Long nombreReponseValeur1) {
        this.nombreReponseValeur1 = nombreReponseValeur1;
    }

    public Long getNombreReponseValeur2() {
        return nombreReponseValeur2;
    }

    public void setNombreReponseValeur2(Long nombreReponseValeur2) {
        this.nombreReponseValeur2 = nombreReponseValeur2;
    }

    public Long getNombreReponseValeur3() {
        return nombreReponseValeur3;
    }

    public void setNombreReponseValeur3(Long nombreReponseValeur3) {
        this.nombreReponseValeur3 = nombreReponseValeur3;
    }

    public double getPourcentageNombreReponseValeur1() {
        return pourcentageNombreReponseValeur1;
    }

    public void setPourcentageNombreReponseValeur1(double pourcentageNombreReponseValeur1) {
        this.pourcentageNombreReponseValeur1 = pourcentageNombreReponseValeur1;
    }

    public double getPourcentageNombreReponseValeur2() {
        return pourcentageNombreReponseValeur2;
    }

    public void setPourcentageNombreReponseValeur2(double pourcentageNombreReponseValeur2) {
        this.pourcentageNombreReponseValeur2 = pourcentageNombreReponseValeur2;
    }

    public double getPourcentageNombreReponseValeur3() {
        return pourcentageNombreReponseValeur3;
    }

    public void setPourcentageNombreReponseValeur3(double pourcentageNombreReponseValeur3) {
        this.pourcentageNombreReponseValeur3 = pourcentageNombreReponseValeur3;
    }
    
    
}
