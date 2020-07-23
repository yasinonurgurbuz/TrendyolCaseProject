package com.trendyol.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.http.ContentType;
import org.junit.Test;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class CaseTest extends BaseTest {

    // Rest Assured hakkinda daha fazla bilgi icin: https://github.com/rest-assured/rest-assured/wiki/Usage_Legacy

    @Test
    // Harry Potter and the Sorcerer's Stone filminin indexini girerek id "By ID or Title" motodunda ki parametreleri kullanarak arama yapilmasi
    public void trendyolCase() { byID(findImdbIdSpecificMovie(1)); }

    // Harry Potter and the Sorcerer's Stone filminin imdb ID sini bulma bölümü
    public String findImdbIdSpecificMovie(int movieIndex)                                                                       //http://www.omdbapi.com/?s=Harry%20Potter&apikey=8db2290f "Harry Potter and the Sorcerer's Stone" filminin index ini bulma
    {
    Response response = bySearchHarryPotter(searchMovie).when().get(baseURI).then().extract().response();                        // Harry Potter isminin http://www.omdbapi.com/'fa ki parametrelerle aranması
        String findMovieImdbId = new StringBuilder().append("Search[").append(movieIndex).append("].imdbID").toString();         // "Harry Potter and the Sorcerer's Stone" filmi 1. index te olduğu için Search[1].imdbID kullanıldı ve string olarak alındı, StringBuilder bize .append() kullanarak istediğimiz datayı bulmamızı sağlar
        return response.jsonPath().getString(findMovieImdbId);                                                                   // JsonPath() to get data from the response body
    }


    // "Bulunan imdbID yi "By ID or Title" Parametrini kullanarak http://www.omdbapi.com/ 'da aratılıp, response değerlerini kontrol etme bölümü
    private void byID(String imdbID)
    {
        byIDorTitleSearchParameters(imdbID).when().get(baseURI).then()
                .statusCode(200).and()                                                                                           // Statü kontrolü
                .body("Title", equalTo(searchSpecificMovie)).and()                                                            // İsim Kontrolü
                .body("Year",equalTo("2001")).and()                                                                   // Yıl kontrolü
                .body("Released",equalTo("16 Nov 2001"));                                                             // Yayınlanan yıl kontrolü
    }


    // "By Search Parametresi altindaki parametrelerinin girdisi / Aynı zamanda burda "Harry Potter" ismini parametreler arasına koyarak daha sonra aramaya hazırlanması bölümü
    private RequestSpecification bySearchHarryPotter(String searchMovie)
    {
        rS = given()
                .accept(ContentType.JSON)
                .param(apiKey, "8db2290f")
                .param(movieTitle, searchMovie)
                .param(movieType, "movie")
                .param(movieYear, "")
                .param(dataType, "json")
                .param(pageNumber, "1")
                .param(callbackName, "")
                .param(apiVersion, "1");
        return rS;
    }

    // Harry Potter arasmasından gelen ID'yi "By ID or Title" parametreleriyle film araması yapılıp, test etme bölümü
    public RequestSpecification byIDorTitleSearchParameters(String id)
    {
        rS = given()
                .accept(ContentType.JSON)
                .param(apiKey, "8db2290f")
                .param(imdbID, id)
                .param(movieTitle, "")
                .param(movieType, "movie")
                .param(movieYear, "")
                .param(plot, "short")
                .param(dataType, "json")
                .param(callbackName, "")
                .param(apiVersion, "1");
        return rS;
    }
}