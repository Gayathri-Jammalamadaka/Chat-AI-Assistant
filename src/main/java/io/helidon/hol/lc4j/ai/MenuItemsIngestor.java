package io.helidon.hol.lc4j.ai;

import io.helidon.hol.lc4j.data.MenuItemsService;
import io.helidon.service.registry.Service;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.data.segment.TextSegment;
import io.helidon.common.config.Config;
import io.helidon.hol.lc4j.data.MenuItem;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;

@Service.Singleton
public class MenuItemsIngestor {
    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final MenuItemsService menuItemsService;

    @Service.Inject
    public MenuItemsIngestor(Config config,
                             EmbeddingStore<TextSegment> embeddingStore,
                             EmbeddingModel embeddingModel,
                             MenuItemsService menuItemsService) {
        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;
        this.menuItemsService = menuItemsService;
    }

    private Document generateDocument(MenuItem item) {
        var str = String.format(
                "%s: %s. Category: %s. Price: $%.2f. Tags: %s. Add-ons: %s.",
                item.getName(),
                item.getDescription(),
                item.getCategory(),
                item.getPrice(),
                String.join(", ", item.getTags()),
                String.join(", ", item.getAddOns())
        );

        return Document.from(str);
    }

    public void ingest() {
        // Create an ingestor with the given embedding model and embedding store
        var ingestor = EmbeddingStoreIngestor.builder()
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();

        // Read menu items from JSON
        var menuItems = menuItemsService.getMenuItems();

        // Convert menu items into text documents
        var documents = menuItems.stream()
                .map(this::generateDocument)
                .toList();

        // Process and store embeddings
        ingestor.ingest(documents);
    }
}