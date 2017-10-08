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

data class TextFormat(val color: TextColor = TextColor.NONE,
                      val style: TextStyle = TextStyle.NONE) : TextElement {
	companion object {
		val NONE = TextFormat()
	}

	fun color(new: TextColor) = TextFormat(new, style)
	fun style(new: TextStyle) = TextFormat(color, new)
	fun merge(format: TextFormat) = TextFormat(
		when {
			format.color == TextColor.NONE -> this.color
			format.color == TextColor.RESET -> TextColor.NONE
			else -> format.color
		}, style.and(format.style)
	)

	fun isEmpty() = color == TextColor.NONE && style.isEmpty() && !style.isReset()

	override fun applyTo(builder: TextBuilder) {
		builder.format = this
	}
}
