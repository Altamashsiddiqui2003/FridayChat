package util;

import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.*;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.StringEntity;

public class HttpUtil {

    // 🔥 Directly store your Gemini API key here or set via System.getenv()
    private static final String API_KEY = "AIzaSyAUaGzxuYKv7hNBgpcy6qwO36jZAPJRTCY"; // 👈 Replace with your real key

    // ✅ Updated version: Gemini API expects key in URL, not Authorization header
    public static String postJson(String url, String jsonBody) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // Ensure the URL has ?key=API_KEY
            String finalUrl = url.contains("?key=") ? url : url + "?key=" + API_KEY;

            HttpPost post = new HttpPost(finalUrl);
            post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            post.setEntity(new StringEntity(jsonBody));

            try (CloseableHttpResponse resp = client.execute(post)) {
                int status = resp.getCode();
                String respBody = new String(resp.getEntity().getContent().readAllBytes());

                if (status >= 200 && status < 300) {
                    return respBody;
                } else {
                    // Throw a detailed exception to help with debugging
                    throw new RuntimeException("API call failed: " + status + " body: " + respBody);
                }
            }
        }
    }
}
