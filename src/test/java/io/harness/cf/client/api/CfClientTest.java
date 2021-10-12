package io.harness.cf.client.api;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.harness.cf.ApiException;
import io.harness.cf.client.dto.Target;
import java.io.IOException;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.junit.Ignore;
import org.junit.Test;

@Slf4j
public class CfClientTest {

  private final CfClient cfClient;
  private Target target =
      Target.builder()
          .name("target1")
          .identifier("target1-identifier")
          .attributes(
              new ImmutableMap.Builder<String, Object>()
                  .put("accountId", "kmpySmUISimoRrJL6NL73w")
                  .put("name", "ObjectName5")
                  .put("licenseType", "TRIAL")
                  .build())
          .build();

  public CfClientTest()
      throws CfClientException, ApiException, IOException, XmlPullParserException {

    // local settings
    String apiKey = "dee0f227-7e8f-4f9a-866d-943a1517a47e";
    String baseUrl = "http://localhost/api/1.0";
    String eventsUrl = "http://localhost/api/1.0";

    cfClient =
        new CfClient(
            apiKey,
            Config.builder()
                .configUrl(baseUrl)
                .eventUrl(eventsUrl)
                .streamEnabled(true)
                .analyticsEnabled(true)
                .build());

    // Wait for the client to be initialized
    int retries = 5;
    while (!cfClient.isInitialized() && retries > 0) {
      try {
        Thread.sleep(2000);
        --retries;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    if (cfClient.isInitialized() != true) {
      throw new CfClientException("Unable to initialize client with feature flag server");
    }
  }

  @Test
  @Ignore
  public void boolVariation() {
    // String FEATURE_FLAG_KEY = "Test";
    // String FEATURE_FLAG_KEY = "show_animation";
    String FEATURE_FLAG_KEY = "enable_anomaly_detection_batch_job";

    IntStream.range(0, 1)
        .forEach(
            i -> {
              boolean result = cfClient.boolVariation(FEATURE_FLAG_KEY, target, false);
              log.info("Boolean variation: {}", result);
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }

  @Test
  @Ignore
  public void jsonVariation() {
    String FEATURE_FLAG_KEY = "Test_JSON";
    JsonObject defaultValue = new Gson().fromJson("{'default' : 'true'}", JsonObject.class);

    IntStream.range(0, 1)
        .forEach(
            i -> {
              JsonObject result = cfClient.jsonVariation(FEATURE_FLAG_KEY, target, defaultValue);
              log.info("JSON variation: {}", result.toString());
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }

  @Test
  @Ignore
  public void stringVariation() {
    String COLOR_FEATURE_KEY = "color";

    IntStream.range(0, 50)
        .forEach(
            i -> {
              String color = cfClient.stringVariation(COLOR_FEATURE_KEY, target, "black");
              log.info("String variation: {}", color);
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }

  @Test
  @Ignore
  public void numberVariation() {
    String COUNT_FEATURE_KEY = "count";

    IntStream.range(0, 50)
        .forEach(
            i -> {
              double color = cfClient.numberVariation(COUNT_FEATURE_KEY, target, 0);
              log.info("String variation: {}", color);
              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
  }
}
