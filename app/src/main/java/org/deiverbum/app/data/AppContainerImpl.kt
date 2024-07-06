package org.deiverbum.app.data

import android.content.Context
import org.deiverbum.app.data.interests.InterestsRepository
import org.deiverbum.app.data.interests.impl.FakeInterestsRepository
//import org.deiverbum.app.data.interests.impl.FakeInterestsRepository
import org.deiverbum.app.data.posts.PostsRepository
import org.deiverbum.app.data.posts.impl.FakePostsRepository

//import org.deiverbum.app.data.posts.impl.FakePostsRepository

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val postsRepository: PostsRepository
    val interestsRepository: InterestsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class AppContainerImpl(private val applicationContext: Context) : AppContainer {

    override val postsRepository: PostsRepository by lazy {
        FakePostsRepository()
    }

    override val interestsRepository: InterestsRepository by lazy {
        FakeInterestsRepository()
    }
}