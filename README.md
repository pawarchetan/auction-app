# auction-app

## Solution 

#### Technology used :
* Java8
* MongoDB
* GraphQL
* Spring Boot
* Spring Data JPA
* Docker, Docker-Compose
* Heroku
* JUnit
* Lombok
* Gradle
* Development methodology used: TDD
* IDE used --> IntelliJ

#### How to run :

#### Code coverage :
* Approximately more than 90% code has been covered.
* Test has been written using JUnit, Mockito.
* Code coverage is measured using Intellij IDEA code coverage plugin.

#### Feature details :
* To create or attach a bid, you must have created product (Real estate entity), auction with target price and reserve price.
* Bid response will be **BID_PLACED (if bid placed successfully)** or **FINAL_BID (if bid meets target price).**
* Auction will have **CREATED, ACTIVE, FINISHED** statuses.
* Newly created auction will have status CREATED, once any bid is attached to auction, status will change to ACTIVE.
* If any bid is satisfying the target price of the auction, auction status will change to FINISHED.
* If any higher amount bid exist, current bid will not go through. In that case service will respond with message **"Higher amount bid exist, please increase your bid amount".**
* If any bid is matching target price of auction and no other bid has met the reserve price of the auction, then winiing bid will pay the bid amount.
* If any bid is matching target price of auction and there are bids who has already met reserve price of the auction, then **higher price bid will win at second price auction.**

#### Implementation details :

* **Schema design/decision made :**
  * 
  
* **GraphQL:** 
  * Please find all GraphQL queries/types/inputs/mutations in resources folder.
  * Resolvers can be found in graphql package.

* **Unit Tests/ Integration Tests: **
  * Unit tests has been written using Junit and Mockito
  * Repository Tests has been written using SpringMongoTest to actually test data inserting and deletion.

* **Challenges faced : **
  * First time tried and looked into GraphQL, so faced some challenges in understanding the best practices from the development side. As on the internet I saw multiple ways of implementing the QraphQL API, so was not sure which one is preferred way as per industry standard.
  * Faced some chalaneges in writing actual integration tests for GraphQL API.

#### Limitations/Future scope :
* To achieve high consistency, consider use of Causal Consistency and Read and Write Concerns available in MongoDB.
* Use of distributed cache (memcache or redis)
* if required use of Mongo-MapReduce for heavy compuation over the period of time.
* Make address as different document and embed it in Product document as they are co-related / dependent.


