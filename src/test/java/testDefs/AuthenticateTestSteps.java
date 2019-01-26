package testDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import gherkin.deps.net.iharder.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AuthenticateTestSteps {
    HttpURLConnection connection;

    @Given("^I authenticate at homepage using$")
    public void iAuthenticateAtHomepageAndUsing(Map<String, String> data) throws Throwable {
        URL url = new URL("https://postman-echo.com/basic-auth");
        String authStr = data.get("username") + ":" + data.get("password");
        String authEncoded = Base64.encodeBytes(authStr.getBytes());

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", "Basic " + authEncoded);
    }

    @Then("^I get response$")
    public void iGetResponse(Map<String, String> data) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        int responseCode = connection.getResponseCode();
        assertEquals(data.get("responseCode"), String.valueOf(responseCode));

        InputStream in;
        if (responseCode == 200) {
            in = connection.getInputStream();
        } else {
            in = connection.getErrorStream();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            result.append(line);
        }

        reader.close();
        in.close();

        assertEquals(data.get("responseBody"), result.toString());
    }
}
