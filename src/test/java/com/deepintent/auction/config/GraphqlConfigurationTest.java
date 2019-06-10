package com.deepintent.auction.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class GraphqlConfigurationTest {

    @Test
    public void shouldInstantiateGraphQLErrorHandler() {
        assertNotNull(new GraphqlConfiguration().errorHandler());
    }

}
