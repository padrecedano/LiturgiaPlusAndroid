package org.deiverbum.app.core.model.data

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * Class summarizing user interest data
 */
data class UserData(
    val bookmarkedNewsResources: Set<String>,
    val viewedNewsResources: Set<String>,
    val followedTopics: Set<String>,
    val themeBrand: ThemeBrand,
    val shouldHideOnboarding: Boolean,
    val dynamic: UserDataDynamic
    /*val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val useVoiceReader:Boolean,
    val useMultipleInvitatory:Boolean,*/

)

data class UserDataDynamic(
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val useVoiceReader: Boolean,
    val useMultipleInvitatory: Boolean,
    val rubricColor: RubricColorConfig,
)