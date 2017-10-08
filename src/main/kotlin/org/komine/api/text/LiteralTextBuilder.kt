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

class LiteralTextBuilder(text: Text, var content: String = "") : TextBuilder(text) {
	constructor(text: LiteralText = LiteralText.EMPTY) : this(text, text.content)

	override fun isEmpty() = super.isEmpty() && content.isEmpty()

	override fun build() =
		if (isEmpty()) LiteralText.EMPTY else LiteralText(format, backingChildren, content)
}
