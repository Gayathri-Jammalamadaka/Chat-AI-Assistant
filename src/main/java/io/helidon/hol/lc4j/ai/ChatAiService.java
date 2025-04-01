package io.helidon.hol.lc4j.ai;

import io.helidon.integrations.langchain4j.Ai;

import dev.langchain4j.service.SystemMessage;

@Ai.Service
@Ai.ChatMemoryWindow(10)
public interface ChatAiService {

    @SystemMessage("""
            You are Frank - a friendly coffee shop assistant helping customers with their orders.
            You must not answer any questions not related to the menu or placing orders.
            """)
    String chat(String question);
}