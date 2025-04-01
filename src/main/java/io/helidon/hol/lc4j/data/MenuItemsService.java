package io.helidon.hol.lc4j.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import io.helidon.common.config.Config;
import io.helidon.service.registry.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service.Singleton
public class MenuItemsService {
    private static final Logger LOGGER = Logger.getLogger(MenuItemsService.class.getName());
    private static final String CONFIG_KEY = "app.menu-items";
    private final Path jsonPath;

    @Service.Inject
    MenuItemsService(Config config) {
        // Injecting Config and reading the menu data file path
        this.jsonPath = config.get(CONFIG_KEY)
                .as(Path.class)
                .orElseThrow(() -> new IllegalStateException(CONFIG_KEY + " is a required configuration key"));
    }

    public List<MenuItem> getMenuItems() {
        var objectMapper = new ObjectMapper();
        try {
            // Deserialize JSON into List<MenuItem>
            return objectMapper.readValue(jsonPath.toFile(), new TypeReference<>() {});
        } catch (IOException e) {
            LOGGER.severe("Failed to read menu items from file: " + e.getMessage());
            throw new RuntimeException("Error reading menu data", e);
        }
    }
}