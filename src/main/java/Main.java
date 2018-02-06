import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        OkHttpClient client = new OkHttpClient();
        Request request;
        Response response;

        System.out.println("Wprowadź identyfikator osoby:");
        int personId = sc.nextInt();

        request = new Request.Builder()
                //.url("http://httpbin.org/ip")
                .url("https://swapi.co/api/people/" + personId)
                .build();

        response = client.newCall(request).execute();

        ObjectMapper objectMapper = new ObjectMapper();

        People person = null;

        if (response.code() >= 200 && response.code() < 300) {
            //System.out.println(response.body().string());
            //System.out.println(response.code());
            //System.out.println(response.message());
            //Ip ip = objectMapper.readValue(response.body().string(), Ip.class);
            //System.out.println(ip.getOrigin());
            person = objectMapper.readValue(response.body().string(), People.class);
            System.out.println("size: " + person.getFilms().size());
        } else {
            System.out.println(response.message());
        }

        if (person != null) {
            System.out.println("Wprowadź identyfikator filmu od 1 do " + person.getFilms().size() + ":");
            int filmIndex = sc.nextInt();
            Request filmRequest = new Request.Builder()
                    .url(person.getFilms().get(filmIndex-1))
                    .build();

            Response filmResponse = client.newCall(filmRequest).execute();

            Film film = objectMapper.readValue(filmResponse.body().string(), Film.class);

            System.out.println(film.getTitle());
        }

    }

}
