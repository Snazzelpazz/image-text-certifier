package com.allpath.textcertifier

class Certifier {

    /**
     * High-level certification pipeline.
     * This enforces the rule:
     *  - Fix what can be fixed
     *  - Never guess
     *  - Never introduce new typos
     */
    fun certifyText(rawText: String): String {
        // Step 1: sanitize deterministically
        val cleaned = Sanitizer.sanitize(rawText)

        // Step 2: final guardrail
        // If sanitizer made things worse, refuse change
        return if (confidence(cleaned) >= confidence(rawText)) {
            cleaned
        } else {
            rawText
        }
    }

    /**
     * Very simple confidence heuristic for now.
     * This will get stronger later.
     */
    private fun confidence(text: String): Int {
        return text.length
    }
}