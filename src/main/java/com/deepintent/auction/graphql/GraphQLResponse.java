package com.deepintent.auction.graphql;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.springframework.http.ResponseEntity;

public class GraphQLResponse {

    private ReadContext context;

    public GraphQLResponse(ResponseEntity<String> responseEntity) {
        context = JsonPath.parse(responseEntity.getBody());
    }

    public String get(String path) {
        return context.read(path);
    }

    public ReadContext context() {
        return context;
    }

}
