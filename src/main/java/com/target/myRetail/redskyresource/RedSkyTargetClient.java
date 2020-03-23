package com.target.myRetail.redskyresource;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "redsky-target",
        url = "https://redsky.target.com/v2/pdp/tcin")
public interface RedSkyTargetClient {
    @RequestMapping(method = RequestMethod.GET,
            value = "{productId}?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics")
    ResponseEntity<String> getProductInfoById(@PathVariable("productId") String productId);

}
