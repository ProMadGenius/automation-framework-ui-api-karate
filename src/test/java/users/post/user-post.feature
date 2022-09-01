Feature: Create user and login
  Scenario: Create a user that works
    Given url "https://api.demoblaze.com/signup"
    And request {  "username": "testUser",  "password": "1234567890" }
    When method post
    Then status 200
    And match $ == "#notnull"
  Scenario: Create a user that is already created
    Given url "https://api.demoblaze.com/signup"
    And request {  "username": "horacioss2",  "password": "1234567890"  }
    When method post
    Then status 200
    And match $.errorMessage == "This user already exist."
  Scenario: Login user that is working
    Given url "https://api.demoblaze.com/login"
    And request {  "username": "horacioss1",  "password": "1234567890"  }
    When method post
    Then status 200
    And match $ == "#string"
  Scenario: Login user that is working
    Given url "https://api.demoblaze.com/login"
    And request {  "username": "horacioss2",  "password": "123456790"  }
    When method post
    Then status 200
    And match $.errorMessage == "Wrong password."