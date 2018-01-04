package io.vertx.blueprint.microservice.analytics.impl;

import io.vertx.blueprint.microservice.common.service.JdbcRepositoryWrapper;
import io.vertx.blueprint.microservice.analytics.Analytics;
import io.vertx.blueprint.microservice.analytics.AnalyticsService;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JDBC implementation of {@link io.vertx.blueprint.microservice.analytics.AnalyticsService}.
 *
 * @author Eric Zhao
 */
public class AnalyticsServiceImpl extends JdbcRepositoryWrapper implements AnalyticsService {

  private static final int PAGE_LIMIT = 10;

  public AnalyticsServiceImpl(Vertx vertx, JsonObject config) {
    super(vertx, config);
  }

  @Override
  public AnalyticsService initializePersistence(Handler<AsyncResult<Void>> resultHandler) {
    client.getConnection(connHandler(resultHandler, connection -> {
      connection.execute(CREATE_STATEMENT, r -> {
        resultHandler.handle(r);
        connection.close();
      });
    }));
    return this;
  }

  @Override
  public AnalyticsService addAnalytics(Analytics product, Handler<AsyncResult<Void>> resultHandler) {
    JsonArray params = new JsonArray()
      .add(product.getAnalyticsId())
      .add(product.getSellerId())
      .add(product.getName())
      .add(product.getPrice())
      .add(product.getIllustration())
      .add(product.getType());
    executeNoResult(params, INSERT_STATEMENT, resultHandler);
    return this;
  }

  @Override
  public AnalyticsService retrieveAnalytics(String productId, Handler<AsyncResult<Analytics>> resultHandler) {
    this.retrieveOne(productId, FETCH_STATEMENT)
      .map(option -> option.map(Analytics::new).orElse(null))
      .setHandler(resultHandler);
    return this;
  }

  @Override
  public AnalyticsService retrieveAnalyticsPrice(String productId, Handler<AsyncResult<JsonObject>> resultHandler) {
    this.retrieveOne(productId, "SELECT price FROM product WHERE productId = ?")
      .map(option -> option.orElse(null))
      .setHandler(resultHandler);
    return this;
  }

  @Override
  public AnalyticsService retrieveAnalyticssByPage(int page, Handler<AsyncResult<List<Analytics>>> resultHandler) {
    this.retrieveByPage(page, PAGE_LIMIT, FETCH_WITH_PAGE_STATEMENT)
      .map(rawList -> rawList.stream()
        .map(Analytics::new)
        .collect(Collectors.toList())
      )
      .setHandler(resultHandler);
    return this;
  }

  @Override
  public AnalyticsService retrieveAllAnalyticss(Handler<AsyncResult<List<Analytics>>> resultHandler) {
    this.retrieveAll(FETCH_ALL_STATEMENT)
      .map(rawList -> rawList.stream()
        .map(Analytics::new)
        .collect(Collectors.toList())
      )
      .setHandler(resultHandler);
    return this;
  }

  @Override
  public AnalyticsService deleteAnalytics(String productId, Handler<AsyncResult<Void>> resultHandler) {
    this.removeOne(productId, DELETE_STATEMENT, resultHandler);
    return this;
  }

  @Override
  public AnalyticsService deleteAllAnalyticss(Handler<AsyncResult<Void>> resultHandler) {
    this.removeAll(DELETE_ALL_STATEMENT, resultHandler);
    return this;
  }

  // SQL statements

  private static final String CREATE_STATEMENT = "CREATE TABLE IF NOT EXISTS `product` (\n" +
    "  `productId` VARCHAR(60) NOT NULL,\n" +
    "  `sellerId` varchar(30) NOT NULL,\n" +
    "  `name` varchar(255) NOT NULL,\n" +
    "  `price` double NOT NULL,\n" +
    "  `illustration` MEDIUMTEXT NOT NULL,\n" +
    "  `type` varchar(45) NOT NULL,\n" +
    "  PRIMARY KEY (`productId`),\n" +
    "  KEY `index_seller` (`sellerId`) )";
  private static final String INSERT_STATEMENT = "INSERT INTO product (`productId`, `sellerId`, `name`, `price`, `illustration`, `type`) VALUES (?, ?, ?, ?, ?, ?)";
  private static final String FETCH_STATEMENT = "SELECT * FROM product WHERE productId = ?";
  private static final String FETCH_ALL_STATEMENT = "SELECT * FROM product";
  private static final String FETCH_WITH_PAGE_STATEMENT = "SELECT * FROM product LIMIT ?, ?";
  private static final String DELETE_STATEMENT = "DELETE FROM product WHERE productId = ?";
  private static final String DELETE_ALL_STATEMENT = "DELETE FROM product";
}
