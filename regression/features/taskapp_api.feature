Feature: TaskApp API tests

  Background: setup environment
    Given properties are loaded and environment is healthy

  Scenario: create, get, update, patch and delete a Task
    When a task is created with description as test description and resolved as False
    Then the response code should be 201
    And  the location header should be properly set
    When the created task is retrieved
    Then the response code should be 200
    Then the response 'description' field should be test description
    Then the response 'resolved' field should be False
    Then the response 'createdAt' and 'updatedAt' fields should be set and be the same
    When the created task description is patched to changed 123
    And  the created task is retrieved
    Then the response code should be 200
    Then the response 'description' field should be changed 123
    Then the response 'updatedAt' should be different than 'createdAt'
    When the created task description is put with description as CHANGED and resolved as True
    And  the created task is retrieved
    Then the response 'description' field should be CHANGED
    Then the response 'resolved' field should be True
    Then the response code should be 200
    When the created task is deleted
    Then the response code should be 200
    When the created task is retrieved
    Then the response code should be 404
