package io.vertx.blueprint.microservice.analytics;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

/**
 * A service interface managing products.
 * <p>
 * This service is an event bus service (aka. service proxy)
 * </p>
 *
 * @author Eric Zhao
 */
@VertxGen
@ProxyGen
public interface AnalyticsService {

  /**
   * The name of the event bus service.
   */
  String SERVICE_NAME = "product-eb-service";

  /**
   * The address on which the service is published.
   */
  String SERVICE_ADDRESS = "service.product";

  /**
   * A static method that creates a product service.
   *
   * @param config a json object for configuration
   * @return initialized product service
   */
  // static AnalyticsService createService(Vertx vertx, JsonObject config)

  /**
   * Initialize the persistence.
   *
   * @param resultHandler the result handler will be called as soon as the initialization has been accomplished. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService initializePersistence(Handler<AsyncResult<Void>> resultHandler);

  /**
   * Add a product to the persistence.
   *
   * @param product       a product entity that we want to add
   * @param resultHandler the result handler will be called as soon as the product has been added. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService addAnalytics(Analytics product, Handler<AsyncResult<Void>> resultHandler);

  /**
   * Retrieve the product with certain `productId`.
   *
   * @param productId     product id
   * @param resultHandler the result handler will be called as soon as the product has been retrieved. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService retrieveAnalytics(String productId, Handler<AsyncResult<Analytics>> resultHandler);

  /**
   * Retrieve the product price with certain `productId`.
   *
   * @param productId     product id
   * @param resultHandler the result handler will be called as soon as the product has been retrieved. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService retrieveAnalyticsPrice(String productId, Handler<AsyncResult<JsonObject>> resultHandler);

  /**
   * Retrieve all products.
   *
   * @param resultHandler the result handler will be called as soon as the products have been retrieved. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService retrieveAllAnalyticss(Handler<AsyncResult<List<Analytics>>> resultHandler);

  /**
   * Retrieve products by page.
   *
   * @param resultHandler the result handler will be called as soon as the products have been retrieved. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService retrieveAnalyticssByPage(int page, Handler<AsyncResult<List<Analytics>>> resultHandler);

  /**
   * Delete a product from the persistence
   *
   * @param productId     product id
   * @param resultHandler the result handler will be called as soon as the product has been removed. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService deleteAnalytics(String productId, Handler<AsyncResult<Void>> resultHandler);

  /**
   * Delete all products from the persistence
   *
   * @param resultHandler the result handler will be called as soon as the products have been removed. The async result indicates
   *                      whether the operation was successful or not.
   */
  @Fluent
  AnalyticsService deleteAllAnalyticss(Handler<AsyncResult<Void>> resultHandler);

}
