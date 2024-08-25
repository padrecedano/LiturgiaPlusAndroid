package org.deiverbum.app.feature.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.delay
import java.util.Locale
import kotlin.random.Random

data class AudioContent(
    val imgUrl: String, val textToSpeech: String, val title: String
)


/***
 *
 *  Apps targeting Android 11 that use text-to-speech should declare INTENT_ACTION_TTS_SERVICE in the queries elements of their manifest:
 * <queries>
 * ...
 * <intent>
 * <action android:name="android.intent.action.TTS_SERVICE" />
 * </intent>
 * </queries>
 *
 */

/***
 *
 *  Use TtsAudioManager.showUi.value to Show or Hide the component in
 *  AnimatedVisibility
 *
 *
 *
 */

@Composable
fun BoxScope.TtsAudioUI(modifier: Modifier = Modifier) {

    val ttsCurrentAudioContent = TtsAudioManager.currentTTSPlaying.value
    val ttsAudioContentList = remember {
        derivedStateOf {
            TtsAudioManager.contentToSpeech.toList()
        }
    }


    var expanded by remember {
        mutableStateOf(false)
    }

    AnimatedVisibility(
        modifier = Modifier.align(Alignment.BottomCenter),
        enter = slideInVertically(initialOffsetY = { 100 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { 200 }) + fadeOut(),
        visible = TtsAudioManager.showUi.value
    ) {

        SimpleUI(
            modifier = modifier,
            ttsCurrentAudioContent = ttsCurrentAudioContent,
            ttsAudioContentList.value,
            expanded
        ) {
            expanded = !expanded
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun BoxScope.SimpleUI(
    modifier: Modifier,
    ttsCurrentAudioContent: AudioContent?,
    ttsAudioContentList: List<AudioContent>,
    expanded: Boolean,
    onClick: () -> Unit
) {
    val internalModifier = if (expanded) Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .background(MaterialTheme.colorScheme.background) else Modifier
        .fillMaxWidth()
        .wrapContentHeight()

    LazyColumn(
        modifier = internalModifier
            .then(Modifier.animateContentSize())
            .then(modifier),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        item {
            Card(
                onClick = onClick,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .then(modifier)
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {


                    Card(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(end = 8.dp)
                    ) {
                        SubcomposeAsyncImage(contentDescription = "",
                            model = ttsCurrentAudioContent?.imgUrl,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            error = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.LightGray)
                                )
                            },
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center

                                ) {
                                    CircularProgressIndicator()
                                }
                            })
                    }
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(100.dp)
                            .weight(2f)
                            .padding(end = 8.dp)
                    ) {

                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            text = ttsCurrentAudioContent?.title ?: "Title Testing",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        AudioFX(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp),
                            maxLines = 40,
                            lineWidth = 1.dp,
                            maxLineHeight = 40
                        )
                    }


                    Row(
                        modifier = Modifier
                            .width(20.dp)
                            .weight(1f)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.2f)),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        IconButton(onClick = {
                            TtsAudioManager.stop()
                        }) {
                            Icon(Icons.Default.Stop, contentDescription = "Stop")
                        }

                        IconButton(onClick = {
                            TtsAudioManager.nextTTS()
                        }) {
                            Icon(Icons.Default.SkipNext, contentDescription = "Next")
                        }

                    }

                }
            }
        }

        if (expanded) {
            items(ttsAudioContentList, key = { it.title }) { item ->

                Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 8.dp)
                Row(
                    modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    Card(
                        modifier = Modifier
                            .size(60.dp)
                            .padding(end = 8.dp)
                    ) {
                        SubcomposeAsyncImage(contentDescription = "",
                            model = item.imgUrl,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            error = {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.LightGray)
                                )
                            },
                            loading = {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center

                                ) {
                                    CircularProgressIndicator()
                                }
                            })
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        text = ttsCurrentAudioContent?.title ?: "Title Testing",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.size(20.dp))
            }

        }
    }

}


object TtsAudioManager {

    private var initialized = false

    var contentToSpeech = mutableStateListOf<AudioContent>()
        private set

    var cantDataToSpeech: MutableState<Int> = mutableStateOf(0)
        private set

    /***
     *  Use this to show or not the UI     *
     */
    var showUi = TtsSystem.isPlaying
        private set

    var currentTTSPlaying = mutableStateOf<AudioContent?>(null)
        private set

    var finish = mutableStateOf(false)

    fun addTTS(newTTS: AudioContent): Boolean {
        val result = contentToSpeech.add(newTTS)
        cantDataToSpeech.value = contentToSpeech.size
        return result
    }

    fun nextTTS() {

        if (!initialized) return

        val first = contentToSpeech.firstOrNull()

        first?.let { audioToPlay ->

            currentTTSPlaying.value = audioToPlay
            contentToSpeech.remove(audioToPlay)
            cantDataToSpeech.value = contentToSpeech.size
            play(audioToPlay.textToSpeech)

            return
        }

        finish.value = true

    }

    fun initialize(context: Context, locale: Locale) {
        TtsSystem.initializeTTS(context, Locale.forLanguageTag("es"))
        initialized = true
    }

    fun start() {
        if (!initialized) return

        nextTTS()

    }

    fun play(textToSpeech: String) {

        if (!initialized) return

        TtsSystem.speech(textToSpeech)

    }

    fun stop() {
        TtsSystem.onStopTts()
        showUi.value = false
    }
}

private object TtsSystem {

    private var ttsSystem: TextToSpeech? = null

    var isError = mutableStateOf(false)
        private set

    var isPlaying = mutableStateOf(false)
        private set

    var isDone = mutableStateOf(false)
        private set


    fun initializeTTS(context: Context, currentLocale: Locale) {
        ttsSystem = TextToSpeech(context) { status ->
            when (status) {
                TextToSpeech.SUCCESS -> {
                    Log.d("MyTextToSpeech", "INNER_FIRST")
                    ttsSystem?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String) {
                            Log.d("MyTextToSpeech", "On Start")
                            isPlaying.value = true
                            isDone.value = false
                        }

                        override fun onDone(utteranceId: String) {
                            Log.d("MyTextToSpeech", "On Done")
                            isPlaying.value = false
                            isDone.value = true
                            TtsAudioManager.nextTTS()
                        }

                        @Deprecated("Deprecated in Java")
                        override fun onError(utteranceId: String) {
                            Log.d("MyTextToSpeech", "On Error")
                            isError.value = true
                        }
                    })
                }
            }
        }

    }

    fun speech(textToSpeechStr: String) {
        ttsSystem?.speak(textToSpeechStr, TextToSpeech.QUEUE_ADD, null, "tts1")
    }

    fun onPauseTts() {
        isPlaying.value = false
        ttsSystem?.stop()

    }

    fun onStopTts() {
        isPlaying.value = false
        ttsSystem?.stop()
        ttsSystem = null
    }

    fun onErrorClear() {
        isError.value = false
    }


}


@Composable
internal fun AudioFX(
    modifier: Modifier = Modifier,
    maxLines: Int = 20,
    maxLineHeight: Int = 100,
    lineWidth: Dp = 5.dp,
    timeMillis: Long = 100
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            repeat((1..maxLines).count()) {
                LineBox(maxLineHeight, MaterialTheme.colorScheme.primary, timeMillis, lineWidth)
            }
        }
    }
}

@Composable
internal fun LineBox(maxHeight: Int, color: Color, timeMillis: Long = 100, lineWidth: Dp) {
    val rand = remember {
        mutableStateOf(0.dp)
    }
    val heightDp = animateDpAsState(targetValue = rand.value)

    LaunchedEffect(Unit) {
        Log.d("STARTER", "RECOMPOSE")
        while (true) {
            rand.value = Dp(Random.nextInt(0, maxHeight).toFloat())
            delay(timeMillis)
        }
    }

    Box(
        modifier = Modifier
            .height(heightDp.value)
            .width(lineWidth)
            .clip(CircleShape)
            .background(color)
    )

}