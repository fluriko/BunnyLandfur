package mate.academy.service;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.util.UUID;

public class SendFormUtil {
    public static void whenPostRequestWithAuthorizationUsingHttpClientThenCorrect(String login, String password)
            throws ClientProtocolException, IOException, AuthenticationException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://localhost:8080/login");

        httpPost.setEntity(new StringEntity("test post"));
        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(login, password);
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

        CloseableHttpResponse response = client.execute(httpPost);
//        assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
//        client.close();
    }

    public static String getRandomPassword(int end) {
        return UUID.randomUUID().toString().substring(1, end);
    }

    public static void main(String[] args) throws IOException, AuthenticationException {
        String randomPassword = getRandomPassword(5);
        System.out.println("pass: " + randomPassword);
        whenPostRequestWithAuthorizationUsingHttpClientThenCorrect("fluriko", "123123");
    }
}
