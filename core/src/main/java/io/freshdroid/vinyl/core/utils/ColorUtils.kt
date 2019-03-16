package io.freshdroid.vinyl.core.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette


object ColorUtils {

    const val IS_LIGHT = 0
    const val IS_DARK = 1
    const val LIGHTNESS_UNKNOWN = 2

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(@ColorInt color: Int,
                    @IntRange(from = 0, to = 255) alpha: Int): Int {
        return color and 0x00ffffff or (alpha shl 24)
    }

    /**
     * Set the alpha component of `color` to be `alpha`.
     */
    @CheckResult
    @ColorInt
    fun modifyAlpha(@ColorInt color: Int,
                    @FloatRange(from = 0.0, to = 1.0) alpha: Float): Int {
        return modifyAlpha(color, (255f * alpha).toInt())
    }

    /**
     * Checks if the most populous color in the given palette is dark
     *
     *
     * Annoyingly we have to return this Lightness 'enum' rather than a boolean as palette isn't
     * guaranteed to find the most populous color.
     */
    @Lightness
    fun isDark(palette: Palette): Int {
        val mostPopulous = getMostPopulousSwatch(palette) ?: return LIGHTNESS_UNKNOWN
        return if (isDark(mostPopulous.rgb)) IS_DARK else IS_LIGHT
    }


    fun getMostPopulousSwatch(palette: Palette?): Palette.Swatch? {
        var mostPopulous: Palette.Swatch? = null
        if (palette != null) {
            for (swatch in palette.swatches) {
                if (mostPopulous == null || swatch.population > mostPopulous.population) {
                    mostPopulous = swatch
                }
            }
        }
        return mostPopulous
    }

    /**
     * Determines if a given bitmap is dark. This extracts a palette inline so should not be called
     * with a large image!!
     *
     *
     * Note: If palette fails then check the color of the central pixel
     */
    fun isDark(bitmap: Bitmap): Boolean {
        return isDark(bitmap, bitmap.width / 2, bitmap.height / 2)
    }

    /**
     * Determines if a given bitmap is dark. This extracts a palette inline so should not be called
     * with a large image!! If palette fails then check the color of the specified pixel
     */
    @SuppressLint("WrongConstant")
    fun isDark(bitmap: Bitmap, backupPixelX: Int, backupPixelY: Int): Boolean {
        // first try palette with a small color quant size
        val palette = Palette.from(bitmap).maximumColorCount(3).generate()
        return if (palette.swatches.size > 0) {
            isDark(palette) == IS_DARK
        } else {
            // if palette failed, then check the color of the specified pixel
            isDark(bitmap.getPixel(backupPixelX, backupPixelY))
        }
    }

    /**
     * Check if a color is dark (convert to XYZ & check Y component)
     */
    fun isDark(@ColorInt color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) < 0.5
    }

    /**
     * Calculate a variant of the color to make it more suitable for overlaying information. Light
     * colors will be lightened and dark colors will be darkened
     *
     * @param color the color to adjust
     * @param isDark whether `color` is light or dark
     * @param lightnessMultiplier the amount to modify the color e.g. 0.1f will alter it by 10%
     * @return the adjusted color
     */
    @ColorInt
    fun scrimify(@ColorInt color: Int,
                 isDark: Boolean,
                 @FloatRange(from = 0.0, to = 1.0) lightnessMultiplier: Float): Int {
        var freshLightnessMultiplier = lightnessMultiplier
        val hsl = FloatArray(3)
        ColorUtils.colorToHSL(color, hsl)

        if (!isDark) {
            freshLightnessMultiplier += 1f
        } else {
            freshLightnessMultiplier = 1f - freshLightnessMultiplier
        }

        hsl[2] = MathUtils.constrain(0f, 1f, hsl[2] * freshLightnessMultiplier)
        return ColorUtils.HSLToColor(hsl)
    }

    @ColorInt
    fun scrimify(@ColorInt color: Int,
                 @FloatRange(from = 0.0, to = 1.0) lightnessMultiplier: Float): Int {
        return scrimify(color, isDark(color), lightnessMultiplier)
    }

    @Retention(AnnotationRetention.RUNTIME)
    @IntDef(IS_LIGHT, IS_DARK, LIGHTNESS_UNKNOWN)
    annotation class Lightness

}