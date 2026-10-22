package com.nkoexe.text.ui

internal data class FontFitResult(
    val fontSizeSp: Float,
    val minimumFits: Boolean
)

/** Pure binary-search policy: tested separately from Compose text measurement. */
internal object FontFitPolicy {
    const val MINIMUM_FONT_SIZE_SP = 18f
    private const val SEARCH_STEPS = 14

    fun largestFitting(
        maximumFontSizeSp: Float,
        fits: (fontSizeSp: Float) -> Boolean
    ): FontFitResult {
        require(maximumFontSizeSp.isFinite() && maximumFontSizeSp > 0f) {
            "Maximum font size must be finite and positive."
        }

        val maximum = maxOf(MINIMUM_FONT_SIZE_SP, maximumFontSizeSp)
        if (!fits(MINIMUM_FONT_SIZE_SP)) {
            return FontFitResult(MINIMUM_FONT_SIZE_SP, minimumFits = false)
        }
        if (fits(maximum)) {
            return FontFitResult(maximum, minimumFits = true)
        }

        var low = MINIMUM_FONT_SIZE_SP
        var high = maximum
        repeat(SEARCH_STEPS) {
            val candidate = (low + high) / 2f
            if (fits(candidate)) low = candidate else high = candidate
        }
        return FontFitResult(low, minimumFits = true)
    }
}
