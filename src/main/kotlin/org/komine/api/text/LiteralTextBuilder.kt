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

import org.komine.api.text.format.TextFormat

class LiteralTextBuilder(var content: String = "", format: TextFormat, children: List<Text> = emptyList()) : TextBuilder(format, children) {
	constructor(text: Text, content: String = "") : this(content, text.format, text.children)
	constructor(text: LiteralText = LiteralText.EMPTY) : this(text, text.content)

	override fun isEmpty() = super.isEmpty() && content.isEmpty()

	override fun build() = when {
		isEmpty() -> LiteralText.EMPTY
		format.isEmpty() && children.isEmpty() && content == LiteralText.NEW_LINE.content ->
			LiteralText.NEW_LINE
		else -> LiteralText(content, format, backingChildren)
	}
}
