package com.yelp.monbanquet;

import com.montealegreluis.yelpv3.Yelp;
import com.montealegreluis.yelpv3.client.AccessToken;
import com.montealegreluis.yelpv3.client.Credentials;
import com.montealegreluis.yelpv3.jsonparser.SearchCategoryParser;
import com.montealegreluis.yelpv3.search.SearchCategories;
import com.montealegreluis.yelpv3.search.SearchCriteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

import static java.lang.Double.parseDouble;

/**
 * Created by Samy on 15/12/2017 .
 */
@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class MainController {

    Yelp yelp = new Yelp(new Credentials("YVc4BitOuHlC9PRFDwRCwg", "cgBWbFTVBpMpktDBjhrhlMsWThtbWbqqnUOcpJaqLZJWQxHoWFnASoLRy9DSdiZc"));

    @RequestMapping(value="/categories" ,headers="Accept=application/json")
    @ResponseBody
    public SearchCategories getCategories() {
        SearchCriteria criteria = SearchCriteria.byLocation("Paris");

        String originalJsonResponse = yelp.search(criteria).originalResponse();
        SearchCategories categories = SearchCategoryParser.all().parentCategories().availableAt(Locale.FRANCE);
        return  categories ;
    }

    @RequestMapping(value="/search" ,headers="Accept=application/json")
    @ResponseBody
    public String search(String location,String categorie, String term,String longitude,String latitude) {


        SearchCriteria criteria;

        if (location != null) criteria = SearchCriteria.byLocation(location);
        else criteria = SearchCriteria.byCoordinates(parseDouble(latitude) , parseDouble(longitude));
        criteria.inCategories(categorie);
        criteria.withTerm(term);
        String originalJsonResponse = yelp.search(criteria).originalResponse();
        return  originalJsonResponse ;
    }
}
