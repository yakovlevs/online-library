package com.example.onlinelibrary.gbapi;

import com.example.onlinelibrary.domain.Query;
import com.example.onlinelibrary.gbapi.gbook.GoogleBook;
import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
@Log4j
@Component
public class GoogleBooksApiClient {

    @Value("${application.googleBooksApi.UrlTemplate}")
    private String URL;
    @Value("${application.googleBooksApi.AppKey}")
    private String API_KEY;

    @Cacheable("booksList")
    public List<GoogleBook> executeQuery(Query query) {
        log.info("API request:" + query.toString());
        GoogleBooksApiResponse result = restTemplate().getForObject(prepareUrl(query), GoogleBooksApiResponse.class);
        return result.getItems();
    }

    private URI prepareUrl(@NonNull Query request) {
        return generateUri(URL + "" + request.getFullQuery() + "&key=" + API_KEY);
    }

    private URI generateUri(@NonNull String stringUrl) {
        try {
            return new URI(stringUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
    }
}
