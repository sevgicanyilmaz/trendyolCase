package com.apitest;

import org.junit.Test;

import io.restassured.response.ValidatableResponse;

public class ApiTest 
{
	String apiKey = "54d621dd";
    HelperMethods apiTestHelper = new HelperMethods(apiKey);
    
    String id;
    
    @Test 
    public void searchCheck4HP(){
        
        id = apiTestHelper.searchFilm("harry potter", "Harry Potter and the Sorcerer's Stone");
        
        apiTestHelper.searchWithID(id);
        
        
    }


}

