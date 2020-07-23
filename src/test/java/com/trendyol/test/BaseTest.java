package com.trendyol.test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;


public class BaseTest extends Parameters {

    RequestSpecification rS;
    public String searchMovie;
    public String searchSpecificMovie;

    @Before                                                                         // Case Test class'ı okunmadan önce bu method okunuyor
    public void setUp()
    {
        RestAssured.baseURI = "http://www.omdbapi.com/";
        searchMovie = "Harry Potter";
        searchSpecificMovie = "Harry Potter and the Sorcerer's Stone";
    }
}
