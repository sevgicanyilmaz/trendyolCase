package com.apitest;

import org.junit.Test;

import io.restassured.response.ValidatableResponse;

public class ApiTest extends BaseTest
{
	
    @Test 
    public void searchCheck4HP(){
        
    	String id = apiTestHelper.searchFilm("harry potter", "Harry Potter and the Sorcerer's Stone");
        
        apiTestHelper.searchWithID(id);
        
        
    }


}

