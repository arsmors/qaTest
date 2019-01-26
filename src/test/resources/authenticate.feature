# new feature
# Tags: optional

Feature: Authentication test

  Scenario Outline: : Authentication test
    Given I authenticate at homepage using
    | username | <username> |
    | password | <password> |

    Then I get response
    | responseBody | <responseBody> |
    | responseCode | <responseCode>  |

     Examples:
         | username | password | responseCode | responseBody              |
         | postman  | password | 200          | {"authenticated":true}    |
         | postman  | password | 200          | authenticated:true        |
         | postman  | wrong    | 401          | Unauthorized              |




