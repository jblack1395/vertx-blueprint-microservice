package io.vertx.blueprint.microservice.analytics;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * A product tuple represents the amount of a certain product in a shopping.
 */
@DataObject(generateConverter = true)
public class AnalyticsTuple /*extends Tuple4<String, String, Double, Integer>*/ {

  private String productId;
  private String sellerId;
  private Double price;
  private Integer amount;

  public AnalyticsTuple() {
    // empty constructor
  }

  public AnalyticsTuple(String productId, String sellerId, Double price, Integer amount) {
    this.productId = productId;
    this.sellerId = sellerId;
    this.price = price;
    this.amount = amount;
  }

  public AnalyticsTuple(Analytics product, Integer amount) {
    this.productId = product.getAnalyticsId();
    this.sellerId = product.getSellerId();
    this.price = product.getPrice();
    this.amount = amount;
  }

  public AnalyticsTuple(JsonObject json) {
    AnalyticsTupleConverter.fromJson(json, this);
  }

  public AnalyticsTuple(AnalyticsTuple other) {
    this.productId = other.productId;
    this.sellerId = other.sellerId;
    this.price = other.price;
    this.amount = other.amount;
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    AnalyticsTupleConverter.toJson(this, json);
    return json;
  }

  public String getAnalyticsId() {
    return productId;
  }

  public AnalyticsTuple setAnalyticsId(String productId) {
    this.productId = productId;
    return this;
  }

  public String getSellerId() {
    return sellerId;
  }

  public AnalyticsTuple setSellerId(String sellerId) {
    this.sellerId = sellerId;
    return this;
  }

  public Double getPrice() {
    return price;
  }

  public AnalyticsTuple setPrice(Double price) {
    this.price = price;
    return this;
  }

  public Integer getAmount() {
    return amount;
  }

  public AnalyticsTuple setAmount(Integer amount) {
    this.amount = amount;
    return this;
  }

  @Override
  public String toString() {
    return "(" + productId + "," + sellerId + "," + amount + ")";
  }
}
