import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;

    private static int readInt(int min, int max) {
        while (true) {
            try {
                int menuPosition = Integer.parseInt(scanner.nextLine());
                if (menuPosition >= min && menuPosition <= max) {
                    return menuPosition;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            System.out.println("Musisz podać liczbę od " + min + " do " + max + ".");
        }
    }

    public static void main(String[] args) throws IOException {
        scanner = new Scanner(System.in);

        OkHttpClient client = new OkHttpClient();
        Request request;
        Response response;

        System.out.println("Wprowadź identyfikator osoby:");
        int personId = readInt(1, 80);

        request = new Request.Builder()
                .url("https://swapi.co/api/people/" + personId)
                .build();

        response = client.newCall(request).execute();

        ObjectMapper objectMapper = new ObjectMapper();


        if (response.code() >= 200 && response.code() < 300) {
            People person = objectMapper.readValue(response.body().string(), People.class);
            System.out.println("size: " + person.getFilms().size());

            System.out.println("Wprowadź identyfikator filmu od 1 do " + person.getFilms().size() + ":");
            int filmIndex = readInt(1, person.getFilms().size());
            Request filmRequest = new Request.Builder()
                    .url(person.getFilms().get(filmIndex - 1))
                    .build();

            response = client.newCall(filmRequest).execute();

            Film film = objectMapper.readValue(response.body().string(), Film.class);

            System.out.println(film.getTitle());

        } else {
            System.out.println(response.message());
        }

    }

}
