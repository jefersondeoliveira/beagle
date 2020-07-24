/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.zup.beagle.widget.ui

import br.com.zup.beagle.widget.Widget
import br.com.zup.beagle.widget.builder.BeagleWidgetBuilder
import br.com.zup.beagle.widget.context.Bind
import br.com.zup.beagle.widget.context.valueOf
import br.com.zup.beagle.widget.context.valueOfNullable
import br.com.zup.beagle.widget.core.TextAlignment
import kotlin.properties.Delegates

/**
 * A text widget will define a text view natively using the server driven information received through Beagle.
 *
 * @param text defines the text view content. This attribute must be declared and it cannot be null.
 * @param styleId
 *              will reference a style in your local styles file to be applied on this text view.
 *              This attribute can be set as null.
 * @param textColor defines the text color natively.
 * @param alignment defines the text content alignment inside the text view.
 *
 */
data class Text(
    val text: Bind<String>,
    val styleId: String? = null,
    val textColor: Bind<String>? = null,
    val alignment: Bind<TextAlignment>? = null
) : Widget() {
    constructor(
        text: String,
        styleId: String? = null,
        textColor: String? = null,
        alignment: TextAlignment? = null
    ) : this(
        text = valueOf(text),
        styleId = styleId,
        textColor = valueOfNullable(textColor),
        alignment = valueOfNullable(alignment)
    )

    class Builder : BeagleWidgetBuilder<Text> {
        var text: Bind<String> by Delegates.notNull()
        var styleId: String? = null
        var textColor: Bind<String>? = null
        var alignment: Bind<TextAlignment>? = null

        fun text(text: Bind<String>) = this.apply { this.text = text }
        fun styleId(styleId: String?) = this.apply { this.styleId = styleId }
        fun textColor(textColor: Bind<String>?) = this.apply { this.textColor = textColor }
        fun alignment(alignment: Bind<TextAlignment>?) = this.apply { this.alignment = alignment }

        fun text(block: () -> Bind<String>) {
            text(block.invoke())
        }

        fun styleId(block: () -> String?) {
            styleId(block.invoke())
        }

        fun textColor(block: () -> Bind<String>?) {
            textColor(block.invoke())
        }

        fun alignment(block: () -> Bind<TextAlignment>?) {
            alignment(block.invoke())
        }

        override fun build() = Text(
            text = text,
            styleId = styleId,
            textColor = textColor,
            alignment = alignment
        )
    }
}

fun text(block: Text.Builder.() -> Unit) = Text.Builder().apply(block).build()
