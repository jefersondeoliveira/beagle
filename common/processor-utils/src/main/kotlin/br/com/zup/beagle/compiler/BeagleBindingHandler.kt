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

package br.com.zup.beagle.compiler

import br.com.zup.beagle.core.BindAttribute
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asTypeName
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

open class BeagleBindingHandler(private val processingEnvironment: ProcessingEnvironment,
                                private val bindClass: KClass<out BindAttribute<*>>) {
    companion object {
        const val BINDING_SUFFIX = "Binding"
    }

    private val typeUtils = processingEnvironment.typeUtils
    fun createBindingClass(element: TypeElement) =
        element.visibleGetters.map { this.createBindParameter(it) }.let { parameters ->
            TypeSpec.classBuilder("${element.simpleName}${BINDING_SUFFIX}")
                .superclass(this.typeUtils.getKotlinName(element.superclass))
                .addSuperinterfaces(element.interfaces.map(TypeMirror::asTypeName))
                .primaryConstructor(FunSpec.constructorFrom(parameters))
                .addProperties(parameters.map { PropertySpec.from(it, it.tag(Boolean::class) == true) })
        }

    fun createBindParameter(element: ExecutableElement) =
        ParameterSpec.builder(
            element.fieldName,
            this.typeUtils.getKotlinName(element.returnType).let {
                if (element.isOverride) it else bindClass.asTypeName().parameterizedBy(it)
            }
        ).tag(Boolean::class, element.isOverride).build()
}