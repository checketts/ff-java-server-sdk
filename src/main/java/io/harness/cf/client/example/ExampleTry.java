package io.harness.cf.client.example;

import com.google.gson.JsonObject;
import io.harness.cf.client.api.Client;
import io.harness.cf.client.api.Config;
import io.harness.cf.client.api.FeatureFlagInitializeException;
import io.harness.cf.client.api.FileMapStore;
import io.harness.cf.client.dto.Target;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class ExampleTry {

  private static final String SDK_KEY = "9ecc4ced-afc1-45af-9b54-c899cbff4b62";

  public static void main(String... args) throws InterruptedException {

    try (final FileMapStore fileStore = new FileMapStore("Non-Freemium");
        final Client client = new Client(SDK_KEY, Config.builder().store(fileStore).build())) {
      client.waitForInitialization();

      final Target target =
          Target.builder()
              .identifier("target1")
              .isPrivate(false)
              .attribute("testKey", "TestValue")
              .name("target1")
              .build();

      final boolean bResult = client.boolVariation("test", target, false);
      log.info("Boolean variation: {}", bResult);
      final JsonObject jsonResult = client.jsonVariation("flag4", target, new JsonObject());
      log.info("JSON variation: {}", jsonResult);
    } catch (FeatureFlagInitializeException e) {
      log.error("Exception: {}", e.getMessage());
    }
    System.exit(0);
  }
}
