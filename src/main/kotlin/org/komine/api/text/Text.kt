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

package org.komine.api.text

import org.komine.api.text.format.TextColor
import org.komine.api.text.format.TextFormat
import org.komine.api.text.format.TextStyle

abstract class Text(
	val format: TextFormat = TextFormat.NONE,
	children: List<Text> = emptyList()) : TextRepresentable, Comparable<Text> {
	/**
	 * @return the children of this [Text]
	 */
	val children = children.toList()

	/**
	 * @return the color of this [Text]
	 */
	val color: TextColor get() = format.color

	/**
	 * @return the style of this [Text]
	 */
	val style: TextStyle get() = format.style

	/**
	 * @return whether this [Text] is empty
	 */
	open fun isEmpty() = format.isEmpty() && children.isEmpty()

	/**
	 * @return a new [TextBuilder] with the content and the properties of this [Text]
	 */
	abstract fun rebuild(): TextBuilder

	/**
	 * @return concatenated text of this [Text] and [other]
	 */
	operator fun plus(other: Text) = rebuild().append(other)

	/**
	 * @see TextBuilder.trim
	 * @return trimmed this text
	 */
	fun trim() = rebuild().trim().build()

	// TODO: Maybe compare something other
	override fun compareTo(other: Text): Int = hashCode() - other.hashCode()

	override fun toText() = this

	companion object {
		fun of() = LiteralText.EMPTY
		fun of(content: String = "", format: TextFormat = TextFormat.NONE, children: List<Text> = emptyList()) =
			LiteralTextBuilder(content, format, children).build()
	}
}
