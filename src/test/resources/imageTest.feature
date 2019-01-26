# new feature
# Tags: optional

Feature: Verify that test.jpg; image (take screenshot of your actual Google logo) exists on Google webpage when it is opened by Webdriver.

  Scenario: A scenario
    When I navigate to google homepage
    Then I verify that main image matches test image