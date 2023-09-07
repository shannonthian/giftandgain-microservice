package com.giftandgain.listing.controller;


import com.giftandgain.listing.model.Listing;
import com.giftandgain.listing.repository.ListingRepository;
import com.giftandgain.listing.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path="/api/listing")
public class ListingController {

    private final ListingRepository listingRepository;
    private final ListingService listingService;

    @Autowired
    public ListingController(ListingRepository listingRepository, ListingService listingService){
        this.listingRepository=listingRepository;
        this.listingService=listingService;
    }

    @GetMapping(path="all")
    public @ResponseBody List<Listing> getListings(){
        List<Listing> listings = listingService.getListings();
        return listings;
    }


}
