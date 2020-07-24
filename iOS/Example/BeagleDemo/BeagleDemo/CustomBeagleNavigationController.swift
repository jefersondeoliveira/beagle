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

import Beagle

class CustomBeagleNavigationController: BeagleNavigationController {
    
    override func serverDrivenStateDidChange(
        to state: ServerDrivenState,
        at screenController: BeagleController
    ) {
        super.serverDrivenStateDidChange(to: state, at: screenController)
        if case let .error(serverDrivenError, retry) = state,
           case .remoteScreen(let requestError) = serverDrivenError {
            let message: String
            switch requestError {
            case .networkError(let error), .decoding(let error):
                message = error.localizedDescription
            case .loadFromTextError, .urlBuilderError:
                message = requestError.localizedDescription
            }
            ErrorView(message: message, retry: retry).present(in: view)
        }
    }
    
}
