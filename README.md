# Trendyol Case Gökhan Akduman

###### Running the application:
> docker-compose up

###### Swagger:
> http://localhost:8080/swagger-ui.html


# Overall Structure and Components of the Applicatiom

## Database Migrations
I used flyway for database migrations. Migration scripts are in [src/main/resources/db/migration](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/tree/main/src/main/resources/db/migration)

## Flow and algorithm

###### LinkConverterService
Rest controller has 2 endpoints which can also be seen in swagger. When called, this 2 endpoints call [LinkConverterService](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/service/LinkConverterService.java).*getConvertedUriString* method with source link type parameter (WEB or DEEPLINK)
This service then checks the validity of the URI and calls [LinkConverterStrategyFactory](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/strategy/LinkConverterStrategyFactory.java).*getLinkConverterStrategyFromUri* to get appropriate strategy for the gicen URI.

###### LinkConverterStrategyFactory
Since there are 3 categories of links (product, search, other) and these categories requires different type of processing (algorithm), I applied **Strategy design pattern**.
This factory is responsible for creating related strategy object for the given URI. To create the related Strategy object, I used a mechanism similar to (but not exactly same as) **Chain of Responsibility**. 

The order of chain is:
1. [ProductLinkConverterStrategy](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/strategy/ProductLinkConverterStrategy.java)
2. [SearchLinkConverterStrategy](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/strategy/SearchLinkConverterStrategy.java)
3. [DefaultLinkConverterStrategy](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/strategy/DefaultLinkConverterStrategy.java)

After finding appropriate strategy, that strategy is applied and related Model object is generated.
The benefit of StrategyFactory and Strategy is, if we want to add a new Link category to the application, we will write the Strategy of it and add it to chain. We don't have to change LinkConverterService and LinkConverterStrategyFactory 

###### Visitor Pattern
There are 3 categories of links (product, search and other) and each of them may have different storing patterns. That is why I implemented Visitor pattern.
Visitors are called inside LinkConverterService line 30.
Visitor implementation is under [/src/main/java/com/trendyol/linkConverter/visitor](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/tree/main/src/main/java/com/trendyol/linkConverter/visitor)

The benefit of this pattern is, when we want to change our storage behaviour, we can add those dynamically without changing other classes.

[SqlStorageVisitor](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/visitor/storage/SqlStorageVisitor.java) is responsible for converting Product, Search and Default links to corresponding entities and save them to database.

###### Controller Advisor
[ControllerAdvisor](https://github.com/DevelopmentHiring/TrendyolCase-GokhanAkduman/blob/main/src/main/java/com/trendyol/linkConverter/controller/exception/ControllerAdvisor.java) is added for Response Entity Exception Handling

Error logging also made here with slf4j
