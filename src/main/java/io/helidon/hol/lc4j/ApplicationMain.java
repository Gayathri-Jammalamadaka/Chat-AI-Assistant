package io.helidon.hol.lc4j;//Add import for MenuItemService class
import io.helidon.common.config.Config;
import io.helidon.hol.lc4j.ai.MenuItemsIngestor;
import io.helidon.hol.lc4j.data.MenuItemsService;
import io.helidon.hol.lc4j.rest.ChatBotService;
import io.helidon.logging.common.LogConfig;
import io.helidon.service.registry.Services;
import io.helidon.webserver.WebServer;

public class ApplicationMain {
    public static void main(String[] args) {
        // Initialize logging
        LogConfig.configureRuntime();

        var config = Services.get(Config.class);

         Services.get(MenuItemsIngestor.class).ingest();

        // Start the Helidon Web Server
        WebServer.builder()
                .config(config.get("server"))
                .routing(routing -> routing.register("/", Services.get(ChatBotService.class)))
                .build()
                .start();
    }
}