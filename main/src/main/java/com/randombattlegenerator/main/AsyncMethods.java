package com.randombattlegenerator.main;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.springframework.scheduling.annotation.Async;

public class AsyncMethods {

    @Async
    public static HttpResponse<String> pokeApi() throws URISyntaxException, IOException, InterruptedException {
        int id = ((int)Math.floor(Math.random() * 905)) + 1;
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(new URI("https://pokeapi.co/api/v2/pokemon/" +  id))
                                        .version(HttpClient.Version.HTTP_2)
                                        .timeout(Duration.ofSeconds(20))
                                        .GET()
                                        .build();
        
        return HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
    }

    @Async
    public static HttpResponse<String> monsterApi() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://app.pixelencounter.com/api/basic/monsters/random"))
        .version(HttpClient.Version.HTTP_2)
        .timeout(Duration.ofSeconds(20))
        .header("accept", "image/svg+xml; charset=utf-8")
        .GET()
        .build();

        return HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
    }

    @Async
    public static HttpResponse<String> quoteApi() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
        .uri(new URI("https://quotes15.p.rapidapi.com/quotes/random/"))
        .header("X-RapidAPI-Key", "")
        .header("X-RapidAPI-Host", "quotes15.p.rapidapi.com")
        .method("GET", HttpRequest.BodyPublishers.noBody())
        .build();

        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }
}
