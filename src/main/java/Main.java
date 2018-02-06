import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request;
        Response response;

        request = new Request.Builder()
                .url("http://httpbin.org/ip")
                //.url("https://swapi.co/api/people/1/")
                .build();

        response = client.newCall(request).execute();

        ObjectMapper objectMapper = new ObjectMapper();

        if (response.code() >= 200 && response.code() < 300) {
            //System.out.println(response.body().string());
            System.out.println(response.code());
            System.out.println(response.message());
            Ip ip = objectMapper.readValue(response.body().string(), Ip.class);
            System.out.println(ip.getOrigin());
        } else {
            System.out.println(response.message());
        }
    }

}
