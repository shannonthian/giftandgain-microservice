package com.giftandgain.listing.service;

import com.giftandgain.listing.model.Listing;
import com.giftandgain.listing.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingService {
    private final ListingRepository listingRepository;

    @Autowired
    public ListingService(ListingRepository listingRepository){
        this.listingRepository=listingRepository;
    }

    public List<Listing> getListings(){
        List<Listing> listings=listingRepository.findAll();
        return listings;
    }

}
