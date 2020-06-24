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

package br.com.zup.beagle.sample.builder

import br.com.zup.beagle.sample.compose.quality.ComposeNavigateTypeQuality
import br.com.zup.beagle.sample.compose.sample.ComposeSampleNavigateType
import br.com.zup.beagle.widget.action.Alert
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.NavigationBarItem
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.ImagePath

class NavigateTypeBuilder(val qaFlag: Boolean) : ScreenBuilder{
    override fun build(): Screen {
        return Screen(
            navigationBar = NavigationBar(
                title = "Step 1",
                showBackButton = true,
                navigationBarItems = listOf(
                    NavigationBarItem(
                        text = "",
                        image = ImagePath.Local.justMobile("informationImage"),
                        action = Alert(
                            title = "Navigation Type",
                            message = "Decide the type of navigation.",
                            labelOk = "OK"
                        )
                    )
                )
            ),
            child = if (qaFlag) ComposeNavigateTypeQuality else ComposeSampleNavigateType
        )
    }

}