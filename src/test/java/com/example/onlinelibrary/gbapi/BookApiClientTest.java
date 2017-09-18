package com.example.onlinelibrary.gbapi;

import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookApiClientTest {
    @Autowired
    private BookApiClient bookApiClient;

    @Autowired
    private BookDao bookDao;

    @Test
    public void init() throws Exception {

        Volumes volumes = bookApiClient.findByTitle("harry potter", 0L);
        if (volumes != null) {
            for (Volume volume : volumes.getItems()) {
                Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
                Volume.SaleInfo saleInfo = volume.getSaleInfo();
                System.out.println("==========");
                // Title.
                System.out.println("Title: " + volumeInfo.getTitle());
            }
        }

        bookDao.findByTitle("harry potter",0L).forEach(book -> System.out.println(book.getTitle()));
    }

}