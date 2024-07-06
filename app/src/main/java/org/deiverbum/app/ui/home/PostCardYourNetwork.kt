package org.deiverbum.app.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.deiverbum.app.R
import org.deiverbum.app.model.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCardPopular(
    post: Post,
    navigateToArticle: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { navigateToArticle(post.id) },
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .width(280.dp)
    ) {
        Column {
            Image(
                painter = painterResource(post.imageId),
                contentDescription = null, // decorative
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = post.metadata.author.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(
                        id = R.string.lbl_nona,
                        formatArgs = arrayOf(
                            post.metadata.date,
                            post.metadata.readTimeMinutes
                        )
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
/*
@Preview("Regular colors")
@Preview("Dark colors", uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewPostCardPopular(
    @PreviewParameter(PostPreviewParameterProvider::class, limit = 1) post: Post
) {
    JetnewsTheme {
        Surface {
            PostCardPopular(post, {})
        }
    }
}

@Preview("Regular colors, long text")
@Composable
fun PreviewPostCardPopularLongText(
    @PreviewParameter(PostPreviewParameterProvider::class, limit = 1) post: Post
) {
    val loremIpsum =
        """
        Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ullamcorper pharetra massa,
        sed suscipit nunc mollis in. Sed tincidunt orci lacus, vel ullamcorper nibh congue quis.
        Etiam imperdiet facilisis ligula id facilisis. Suspendisse potenti. Cras vehicula neque sed
        nulla auctor scelerisque. Vestibulum at congue risus, vel aliquet eros. In arcu mauris,
        facilisis eget magna quis, rhoncus volutpat mi. Phasellus vel sollicitudin quam, eu
        consectetur dolor. Proin lobortis venenatis sem, in vestibulum est. Duis ac nibh interdum,
        """.trimIndent()
    JetnewsTheme {
        Surface {
            PostCardPopular(
                post.copy(
                    title = "Title$loremIpsum",
                    metadata = post.metadata.copy(
                        author = PostAuthor("Author: $loremIpsum"),
                        readTimeMinutes = Int.MAX_VALUE
                    )
                ),
                {}
            )
        }
    }
}

/**
 * Provides sample [Post] instances for Composable Previews.
 *
 * When creating a Composable Preview using @Preview, you can pass sample data
 * by annotating a parameter with @PreviewParameter:
 *
 * ```
 * @Preview
 * @Composable
 * fun MyPreview(@PreviewParameter(PostPreviewParameterProvider::class, limit = 2) post: Post) {
 *   MyComposable(post)
 * }
 * ```
 *
 * In this simple app we just return the hard-coded posts. When the app
 * would be more complex - e.g. retrieving the posts from a server - this would
 * be the right place to instantiate dummy instances.
 */
class PostPreviewParameterProvider : PreviewParameterProvider<Post> {
    override val values = sequenceOf(
        post1, post2, post3, post4, post5
    )
}*/