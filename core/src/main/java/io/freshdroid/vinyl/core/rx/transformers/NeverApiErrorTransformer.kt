/*
 * Copyright 2017 Kickstarter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ***
 *
 * Original: https://github.com/kickstarter/android-oss/blob/master/app/src/main/java/com/kickstarter/libs/rx/transformers/NeverApiErrorTransformer.java
 * Modifications: Some modifiers and annotations have been added by Guillaume Mas.
 */
package io.freshdroid.vinyl.core.rx.transformers

import io.freshdroid.vinyl.core.network.envelopes.ErrorEnvelope
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer


class NeverApiErrorTransformer<T>(
        private val errorAction: Consumer<ErrorEnvelope>?
) : ObservableTransformer<T, T> {

    constructor() : this(null)

    override fun apply(upstream: Observable<T>): ObservableSource<T> {
        return upstream
                .doOnError { e: Throwable ->
                    val env = ErrorEnvelope.fromThrowable(e)
                    if (env != null && errorAction != null) {
                        errorAction.accept(env)
                    }
                }
                .onErrorResumeNext { e: Throwable ->
                    if (ErrorEnvelope.fromThrowable(e) == null) {
                        Observable.error(e)
                    } else {
                        Observable.empty()
                    }
                }
    }

}