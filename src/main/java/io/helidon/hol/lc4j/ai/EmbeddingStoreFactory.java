package io.helidon.hol.lc4j.ai;
import java.util.function.Supplier;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import io.helidon.service.registry.Service;

@Service.Singleton
class EmbeddingStoreFactory implements Supplier<EmbeddingStore<TextSegment>> {
    @Override
    public EmbeddingStore<TextSegment> get() {
        return new InMemoryEmbeddingStore<>();
    }
}