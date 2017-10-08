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

abstract class TextBuilder(var format: TextFormat, children: List<Text> = emptyList()) : TextRepresentable {
	protected val backingChildren = children.toMutableList()
	val children get() = backingChildren.toList()

	constructor(text: Text) : this(text.format, text.children)
	constructor(text: TextBuilder) : this(text.format, text.backingChildren)

	/**
	 * Color of the text
	 */
	var color: TextColor
		get() = format.color
		set(new) {
			format = format.color(new)
		}

	/**
	 * Style of the text
	 */
	var style: TextStyle
		get() = format.style
		set(new) {
			format = format.style(new)
		}

	/**
	 * @return whether the result text will be empty
	 */
	open fun isEmpty() = format.isEmpty() && backingChildren.isEmpty()

	/**
	 * Appends [children] to the [TextBuilder]
	 */
	fun append(vararg children: Text) = also { backingChildren.addAll(children) }

	/**
	 * Insert [children] to the [TextBuilder] on position [pos]
	 */
	fun insert(pos: Int, vararg children: Text) = also { backingChildren.addAll(pos, children.toList()) }

	/**
	 * Removes [children] from the [TextBuilder]
	 */
	fun remove(vararg children: Text) = also { backingChildren.removeAll(children) }

	/**
	 * Removes all the children from the [TextBuilder]
	 */
	fun clear() = also { backingChildren.clear() }

	/**
	 * Removes all empty children from edges
	 */
	fun trim() = also {
		backingChildren.iterator().let {
			while (it.hasNext() && it.next().isEmpty()) {
				it.remove()
			}
		}
		backingChildren.listIterator(backingChildren.size).let {
			while (it.hasPrevious() && it.previous().isEmpty()) {
				it.remove()
			}
		}
	}

	/**
	 * @return built text
	 */
	abstract fun build(): Text

	override fun toText() = build()
}
