package io.helidon.hol.lc4j.ai;
import java.util.function.Supplier;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import io.helidon.service.registry.Service;

@Service.Singleton
class EmbeddingModelFactory implements Supplier<EmbeddingModel> {
    @Override
    public EmbeddingModel get() {
        return new AllMiniLmL6V2EmbeddingModel();
    }
}