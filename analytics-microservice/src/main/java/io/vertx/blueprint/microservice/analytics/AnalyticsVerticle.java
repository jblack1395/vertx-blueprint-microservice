package io.vertx.blueprint.microservice.analytics;

import io.vertx.blueprint.microservice.common.BaseMicroserviceVerticle;
import io.vertx.blueprint.microservice.common.service.ExampleHelper;
import io.vertx.blueprint.microservice.analytics.api.RestAnalyticsAPIVerticle;
import io.vertx.blueprint.microservice.analytics.impl.AnalyticsServiceImpl;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.serviceproxy.ProxyHelper;

import static io.vertx.blueprint.microservice.analytics.AnalyticsService.SERVICE_ADDRESS;


/**
 * A verticle publishing the product service.
 *
 * @author Eric Zhao
 */
public class AnalyticsVerticle extends BaseMicroserviceVerticle {

  @Override
  public void start(Future<Void> future) throws Exception {
    super.start();

    // create the service instance
    AnalyticsService productService = new AnalyticsServiceImpl(vertx, config());
    // register the service proxy on event bus
    ProxyHelper.registerService(AnalyticsService.class, vertx, productService, SERVICE_ADDRESS);
    // publish the service in the discovery infrastructure
    initAnalyticsDatabase(productService)
      .compose(databaseOkay -> publishEventBusService(AnalyticsService.SERVICE_NAME, SERVICE_ADDRESS, AnalyticsService.class))
      .compose(servicePublished -> deployRestService(productService))
      .setHandler(future.completer());
  }

  private Future<Void> initAnalyticsDatabase(AnalyticsService service) {
    Future<Void> initFuture = Future.future();
    service.initializePersistence(initFuture.completer());
    return initFuture.map(v -> {
      ExampleHelper.initData(vertx, config());
      return null;
    });
  }

  private Future<Void> deployRestService(AnalyticsService service) {
    Future<String> future = Future.future();
    vertx.deployVerticle(new RestAnalyticsAPIVerticle(service),
      new DeploymentOptions().setConfig(config()),
      future.completer());
    return future.map(r -> null);
  }

}
