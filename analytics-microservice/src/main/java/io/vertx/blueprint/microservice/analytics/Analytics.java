package io.vertx.blueprint.microservice.analytics;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

/**
 * Analytics data object.
 *
 * @author Eric Zhao
 */
@DataObject(generateConverter = true)
public class Analytics {

  private String productId;
  private String sellerId;
  private String name;
  private double price = 0.0d;
  private String illustration;
  private String type;

  public Analytics() {
    // Empty constructor
  }

  public Analytics(Analytics other) {
    this.productId = other.productId;
    this.sellerId = other.sellerId;
    this.name = other.name;
    this.price = other.price;
    this.illustration = other.illustration;
    this.type = other.type;
  }

  public Analytics(JsonObject json) {
    AnalyticsConverter.fromJson(json, this);
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    AnalyticsConverter.toJson(this, json);
    return json;
  }

  public String getAnalyticsId() {
    return productId;
  }

  public Analytics setAnalyticsId(String productId) {
    this.productId = productId;
    return this;
  }

  public String getSellerId() {
    return sellerId;
  }

  public Analytics setSellerId(String sellerId) {
    this.sellerId = sellerId;
    return this;
  }

  public String getName() {
    return name;
  }

  public Analytics setName(String name) {
    this.name = name;
    return this;
  }

  public double getPrice() {
    return price;
  }

  public Analytics setPrice(double price) {
    this.price = price;
    return this;
  }

  public String getIllustration() {
    return illustration;
  }

  public Analytics setIllustration(String illustration) {
    this.illustration = illustration;
    return this;
  }

  public String getType() {
    return type;
  }

  public Analytics setType(String type) {
    this.type = type;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Analytics product = (Analytics) o;

    return productId.equals(product.productId) && sellerId.equals(product.sellerId);
  }

  @Override
  public int hashCode() {
    int result = productId.hashCode();
    result = 31 * result + sellerId.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return this.toJson().encodePrettily();
  }
}
