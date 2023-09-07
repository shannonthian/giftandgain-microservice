package com.giftandgain.listing;

import com.giftandgain.listing.model.Listing;
import com.giftandgain.listing.repository.ListingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ListingConfig {

    @Bean
    CommandLineRunner commandLineRunner(ListingRepository listingRepository){
        return args -> {
            Listing listing1 = new Listing("rice (kg)", 23);
            Listing listing2 = new Listing("cereal(grams)", 300);
            listingRepository.saveAll(List.of(listing1,listing2));
        };
    }
}
