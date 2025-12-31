package com.allpath.textcertifier

object Sanitizer {

    private val dictionary = setOf(
        "the","and","of","to","in","for","on","with",
        "we","are","build","building","grant","support",
        "launch","pilot","five","school","districts",
        "web2","web3","allpath","foundation","terminal",
        "kiosk","modular","apps","tools","mental","health",
        "economic","empowerment","community","sovereignty"
    )

    private val canon = setOf(
        "Allpath",
        "FLOW",
        "Allpath OS",
        "Cosmic Elephants",
        "Space Donkeys",
        "Ethereum Foundation",
        "Web2",
        "Web3",
        "AI",
        "Codex"
    )

    fun sanitize(text: String): String {
        return text
            .split(" ")
            .joinToString(" ") { token ->
                sanitizeToken(token)
            }
            .replace(Regex("\\s+"), " ")
            .trim()
    }

    private fun sanitizeToken(token: String): String {
        if (canon.contains(token)) return token

        val lower = token.lowercase()

        fixRepeatedLetter(lower)?.let {
            return matchCase(token, it)
        }

        if (dictionary.contains(lower)) return token

        return token
    }

    private fun fixRepeatedLetter(word: String): String? {
        val match = Regex("^([a-z]+)([a-z])\\2$").find(word) ?: return null
        val fixed = match.groupValues[1] + match.groupValues[2]
        return if (dictionary.contains(fixed)) fixed else null
    }

    private fun matchCase(original: String, replacement: String): String {
        return when {
            original.all { it.isUpperCase() } -> replacement.uppercase()
            original.firstOrNull()?.isUpperCase() == true ->
                replacement.replaceFirstChar { it.uppercase() }
            else -> replacement
        }
    }
}