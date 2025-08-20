package com.gl.sample.controller;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.gl.sample.dto.FilteredProducts;
import com.gl.sample.dto.SortedProducts;

@RestController
public class SampleController {

    private final String uri = "https://jsonmock.hackerrank.com/api/inventory";
    private final RestTemplate restTemplate = new RestTemplate();

    private JSONArray getProductData() {
        String result = restTemplate.getForObject(uri, String.class);
        JSONObject root = new JSONObject(result);
        return root.getJSONArray("data");
    }

    @CrossOrigin
    @GetMapping("/filter/price/{initial_price}/{final_price}")
    private ResponseEntity<ArrayList<FilteredProducts>> filtered_books(
            @PathVariable("initial_price") int init_price,
            @PathVariable("final_price") int final_price) {
        ArrayList<FilteredProducts> books = new ArrayList<>();
        try {
            JSONArray data = getProductData();
            for (int i = 0; i < data.length(); i++) {
                JSONObject product = data.getJSONObject(i);
                int price = product.getInt("price");
                if (price >= init_price && price <= final_price) {
                    books.add(new FilteredProducts(product.getString("barcode")));
                }
            }
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println("Error encountered : " + E.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin
    @GetMapping("/sort/price")
    private ResponseEntity<SortedProducts[]> sorted_books() {
        try {
            JSONArray data = getProductData();
            List<JSONObject> productList = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                productList.add(data.getJSONObject(i));
            }
            // Sort products by price ascending
            productList.sort((p1, p2) -> Integer.compare(p1.getInt("price"), p2.getInt("price")));

            SortedProducts[] ans = new SortedProducts[productList.size()];
            for (int i = 0; i < productList.size(); i++) {
                ans[i] = new SortedProducts(productList.get(i).getString("barcode"));
            }
            return new ResponseEntity<>(ans, HttpStatus.OK);
        } catch (Exception E) {
            System.out.println("Error encountered : " + E.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
