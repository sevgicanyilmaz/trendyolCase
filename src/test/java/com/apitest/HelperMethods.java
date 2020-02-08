package com.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.testng.Assert.assertEquals;

public class HelperMethods {

    String baseURI = "http://www.omdbapi.com/";
    String apiKey;
    String id=null;
    
    HelperMethods(String apiKey){
    	this.apiKey = apiKey;
    	RestAssured.baseURI = baseURI;
    }
    
    public String searchFilm(String searchWord, String filmTitle) {
    	
    	ValidatableResponse response = getRequest(searchWord);

        JsonPath jsonPath = getJsonPath(response);
        
        List<Film> data = jsonPath.getList("Search", Film.class);

        for(int i=0;i<data.size();i++) {
        	if(data.get(i).getTitle().equals(filmTitle)) {
        		id=data.get(i).getImdbID(); 
        		break;
        	}
        }
    
        return id;
    }

    public void searchWithID(String id) {
        
    	   given()
                .param("apikey", apiKey)
                .param("i",id)
                .when()
                .get()
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("Title", not(emptyOrNullString()))
                .body("Year", not(emptyOrNullString()))
                .body("Released", not(emptyOrNullString()));
    	   
    	   //testng kullancaksak year/title/released ayrı ayrı tanımlayıp getten sonra --- .path("Year")   ----
     	   //assertEquals(true,!year.isEmpty());
    	   //assertEquals(true,!title.isEmpty());
    	   //assertEquals(true,!released.isEmpty());
    	   //assertEquals(200,response.extract().statusCode())
        
    }
    
	public ValidatableResponse getRequest(String searchWord) {
		return   given()
                .param("s", searchWord)
                .param("apikey", apiKey)
                .when()
                .get("http://www.omdbapi.com/")
                .then();
	}

	public JsonPath getJsonPath(ValidatableResponse response) {
		JsonPath jsonPath = response.log().all()
        		.contentType(ContentType.JSON)
        		.statusCode(200)
        		.and()              
        		.body("Search.Title",not(emptyOrNullString()))
            	.body("Search.Year",not(emptyOrNullString()))
            	.extract()
            	.response().jsonPath();
		return jsonPath;
	}
   
    
}
