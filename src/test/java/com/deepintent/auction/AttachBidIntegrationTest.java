package com.deepintent.auction;

import com.deepintent.auction.config.GraphQLRestTemplate;
import com.deepintent.auction.graphql.GraphQLResponse;
import com.deepintent.auction.repository.AuctionRepository;
import com.deepintent.auction.repository.BidRepository;
import com.deepintent.auction.repository.BidderRepository;
import com.deepintent.auction.repository.ProductRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AttachBidIntegrationTest {

    private String productId;
    private String bidderId;
    private String bidId;
    private String auctionId;

    @Autowired
    private GraphQLRestTemplate graphQLRestTemplate;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private BidderRepository bidderRepository;

    @Autowired
    private ProductRepository productRepository;

    @After
    public void deleteAll() {
        bidRepository.deleteById(bidId);
        bidderRepository.deleteById(bidderId);
        auctionRepository.deleteById(auctionId);
        productRepository.deleteById(productId);
    }

    @Test
    public void createProduct() {
        String request = getCreateProductRequest();
        ResponseEntity<String> response = graphQLRestTemplate.callApi(request);
        GraphQLResponse graphQLResponse = new GraphQLResponse(response);
        productId = graphQLResponse.get("$.data.createProduct.id");

        assertNotNull(productId);

        String bidderRequest = getCreateBidderRequest();
        ResponseEntity<String> bidderResponse = graphQLRestTemplate.callApi(bidderRequest);
        GraphQLResponse graphQLBidderResponse = new GraphQLResponse(bidderResponse);
        bidderId = graphQLBidderResponse.get("$.data.createBidder.id");

        assertNotNull(bidderId);

        String auctionRequest = getCreateAuctionRequest();
        ResponseEntity<String> auctionResponse = graphQLRestTemplate.callApi(auctionRequest);
        GraphQLResponse graphQLAuctionResponse = new GraphQLResponse(auctionResponse);
        auctionId = graphQLAuctionResponse.get("$.data.createAuction.id");

        assertNotNull(auctionId);

        String attachedBidRequest = getCreateBidRequest();
        ResponseEntity<String> bidResponse = graphQLRestTemplate.callApi(attachedBidRequest);
        GraphQLResponse graphQLBidResponse = new GraphQLResponse(bidResponse);
        bidId = graphQLBidResponse.get("$.data.createBid.id");
        String bidStatus = graphQLBidResponse.get("$.data.createBid.bidStatus");

        assertNotNull(bidId);
        assertNotNull(bidStatus);
    }

    private String getCreateBidRequest() {
        return "{\"query\":\"mutation createBid($input: BidDto!) {\\n  createBid(bidDto: $input) {\\n    id\\n   " +
                "\\tauctionId\\n    bidderId\\n    amount\\n    bidStatus\\n  }\\n}\",\"variables\":{\"input\":{\"id\":\"\"," +
                "\"auctionId\":\"" + this.auctionId + "\",\"bidderId\":\"" + this.bidderId + "\",\"amount\":5600}}," +
                "\"operationName\":\"createBid\"}";
    }

    private String getCreateBidderRequest() {
        return "{\"query\":\"mutation createBidder($input: BidderDto!) {\\n  createBidder(bidderDto: $input) {\\n    id\\n  " +
                "}\\n}\",\"variables\":{\"input\":{\"id\":\"\",\"firstName\":\"chetan\",\"lastName\":\"test\"}}," +
                "\"operationName\":\"createBidder\"}";
    }

    private String getCreateAuctionRequest() {
        return "{\"query\":\"mutation createAuction($input: AuctionDto!) {\\n  createAuction(auctionDto: $input) {\\n   " +
                " id\\n    productId\\n    status\\n    targetPrice\\n    reservePrice\\n  }\\n}\",\"variables\":{\"input\":{\"id\":\"\"," +
                "\"productId\":\"" + this.productId + "\",\"status\":\"CREATED\",\"targetPrice\":7000,\"reservePrice\":5600}}," +
                "\"operationName\":\"createAuction\"}";
    }

    private String getCreateProductRequest() {
        return "{\"query\":\"mutation createProduct($input: ProductDto!) {\\n  createProduct(productDto: $input) {\\n    id\\n " +
                "   name\\n    description\\n    address\\n    price\\n  }\\n}\\n\",\"variables\":{\"input\":{\"id\":\"\"," +
                "\"name\":\"house\",\"description\":\"Test\",\"address\":\"test\",\"price\":50000}},\"operationName\":\"createProduct\"}";
    }

}
