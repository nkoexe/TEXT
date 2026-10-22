package com.nkoexe.text.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class FontFitPolicyTest {
    @Test
    fun returnsMaximumWhenItFits() {
        val result = FontFitPolicy.largestFitting(96f) { true }

        assertEquals(96f, result.fontSizeSp, 0f)
        assertTrue(result.minimumFits)
    }

    @Test
    fun findsLargestSizeWithinTheFitBoundary() {
        val result = FontFitPolicy.largestFitting(120f) { it <= 42f }

        assertTrue(result.minimumFits)
        assertTrue(result.fontSizeSp <= 42f)
        assertTrue(result.fontSizeSp > 41.9f)
    }

    @Test
    fun keepsReadableMinimumAndSignalsWhenItCannotFit() {
        val result = FontFitPolicy.largestFitting(120f) { it < 18f }

        assertEquals(FontFitPolicy.MINIMUM_FONT_SIZE_SP, result.fontSizeSp, 0f)
        assertFalse(result.minimumFits)
    }

    @Test
    fun maximumBelowMinimumStillUsesMinimum() {
        val result = FontFitPolicy.largestFitting(8f) { true }

        assertEquals(FontFitPolicy.MINIMUM_FONT_SIZE_SP, result.fontSizeSp, 0f)
        assertTrue(result.minimumFits)
    }

    @Test
    fun rejectsInvalidMaximums() {
        listOf(0f, -1f, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY).forEach { maximum ->
            assertThrows(IllegalArgumentException::class.java) {
                FontFitPolicy.largestFitting(maximum) { true }
            }
        }
    }

    @Test
    fun reportsTheMinimumAsFittingWhenOnlyTheMinimumFits() {
        val result = FontFitPolicy.largestFitting(120f) { it <= 18f }

        assertEquals(18f, result.fontSizeSp, 0.01f)
        assertTrue(result.minimumFits)
    }
}
