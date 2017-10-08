/*
 *
 *  _  __               _                  __  __ ____
 * | |/ /___  _ __ ___ (_)_ __   ___      |  \/  |  _ \
 * | ' // _ \| '_ ` _ \| | '_ \ / _ \_____| |\/| | |_) |
 * | . \ (_) | | | | | | | | | |  __/_____| |  | |  __/
 * |_|\_\___/|_| |_| |_|_|_| |_|\___|     |_|  |_|_|
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * @author Komine Team
 * @link https://github.com/komine-mp/
 *
 */

package org.komine.api.text.format

import org.komine.api.text.TextBuilder
import org.komine.api.text.TextElement

/**
 * Represents an immutable text style. Can be empty or combined.
 */
data class TextStyle(
	val bold: Boolean = false,
	val italic: Boolean = false,
	val underline: Boolean = false,
	val strikethrough: Boolean = false,
	val obfuscated: Boolean = false
) : TextElement {
	companion object {
		val NONE = TextStyle()

		val BOLD = TextStyle(bold = true)
		val ITALIC = TextStyle(italic = true)
		val UNDERLINE = TextStyle(underline = true)
		val STRIKETHROUGH = TextStyle(strikethrough = true)
		val OBFUSCATED = TextStyle(obfuscated = true)

		val RESET = TextStyle()
	}

	constructor(original: TextStyle,
	            bold: Boolean = original.bold,
	            italic: Boolean = original.italic,
	            underline: Boolean = original.underline,
	            strikethrough: Boolean = original.strikethrough,
	            obfuscated: Boolean = original.obfuscated) :
		this(bold, italic, underline, strikethrough, obfuscated)

	fun isEmpty() = !obfuscated && !bold && !strikethrough && !underline && !italic  && !isReset()
	fun isReset() = this == RESET

	/**
	 * @return a new [TextStyle] with a bold property set to [new]
	 */
	fun bold(new: Boolean = true) = TextStyle(this, bold = new)

	/**
	 * @return a new [TextStyle] with an italic property set to [new]
	 */
	fun italic(new: Boolean = true) = TextStyle(this, italic = new)

	/**
	 * @return a new [TextStyle] with an underline property set to [new]
	 */
	fun underline(new: Boolean = true) = TextStyle(this, underline = new)

	/**
	 * @return a new [TextStyle] with a strikethrough property set to [new]
	 */
	fun strikethrough(new: Boolean = true) = TextStyle(this, strikethrough = new)

	/**
	 * @return a new [TextStyle] with an obfuscated property set to [new]
	 */
	fun obfuscated(new: Boolean = true) = TextStyle(this, obfuscated = new)

	/**
	 * @return whether this [TextStyle] contains [styles]
	 */
	fun contains(vararg styles: TextStyle) = styles.all { style ->
		fun contains(a: Boolean, b: Boolean) = !b || a == b

		contains(bold, style.bold) &&
			contains(italic, style.italic) &&
			contains(underline, style.underline) &&
			contains(strikethrough, style.strikethrough) &&
			contains(obfuscated, style.obfuscated)
	}

	/**
	 * @return The inverse of this text style
	 */
	fun negate() = TextStyle(!bold, !italic, !underline, !strikethrough, !obfuscated)

	/**
	 * @return Composed [this style] with [styles]
	 */
	fun and(vararg styles: TextStyle) = compose(*styles, negate = false)

	/**
	 * @return Composed [this style] with negates of [styles]
	 */
	fun andNot(vararg styles: TextStyle) = compose(*styles, negate = true)

	private fun compose(vararg styles: TextStyle, negate: Boolean = false): TextStyle {
		fun compose(a: Boolean, b: Boolean) = when {
			!a -> b
			!b -> a
			a != b -> false
			else -> a
		}

		if (styles.isEmpty()) {
			return this
		}

		var bold = bold
		var italic = italic
		var underline = underline
		var strikethrough = strikethrough
		var obfuscated = obfuscated

		if (negate) {
			for (style in styles) {
				bold = compose(bold, !style.bold)
				italic = compose(italic, !style.italic)
				underline = compose(underline, !style.underline)
				strikethrough = compose(strikethrough, !style.strikethrough)
				obfuscated = compose(obfuscated, !style.obfuscated)
			}
		} else {
			for (style in styles) {
				bold = compose(bold, style.bold)
				italic = compose(italic, style.italic)
				underline = compose(underline, style.underline)
				strikethrough = compose(strikethrough, style.strikethrough)
				obfuscated = compose(obfuscated, style.obfuscated)
			}
		}

		return TextStyle(bold, italic, underline, strikethrough, obfuscated)
	}

	override fun applyTo(builder: TextBuilder) {
		builder.style = this
	}
}
