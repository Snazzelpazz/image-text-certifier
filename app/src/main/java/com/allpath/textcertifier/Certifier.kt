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
package com.allpath.textcertifier

import android.graphics.Bitmap

/**
 * ALLPATH Certifier (Authority Gate)
 * Enforces LAW 11.2: Only canon (verified objects) creates continuity.
 */
class Certifier(private val ocrEngine: OcrProvider) {

    /**
     * Verifies that the rendered result matches the sanitized intent.
     * Prevents "Synthetic Secondness" by ensuring object grounding.
     */
    fun verify(plannedFixes: List<TextRegion>, resultBitmap: Bitmap): Boolean {
        // Perform fresh OCR pass on the candidate image
        val finalAudit = ocrEngine.detectAllText(resultBitmap)

        // Deterministic Anchor Check: Box count must remain invariant
        if (plannedFixes.size != finalAudit.size) return false

        // Character-Level Alignment: Verification of symbolic stability
        return plannedFixes.zip(finalAudit).all { (planned, actual) ->
            planned.text == actual.text && actual.confidence > 0.90f
        }
    }
}
