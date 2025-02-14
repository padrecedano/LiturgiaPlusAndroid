package org.deiverbum.app.feature.bugreport

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.deiverbum.app.R
import org.deiverbum.app.core.data.repository.UserDataRepository
import org.deiverbum.app.core.model.data.BugItem
import org.deiverbum.app.util.Configuration
import org.deiverbum.app.util.Constants
import javax.inject.Inject

/**
 * ViewModel para el contenido proveniente de archivos locales.
 *
 * @author A. Cedano
 * @version 2.0
 * @since 2022.1
 */
@HiltViewModel
class BugViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    val userDataRepository: UserDataRepository,

    ) : ViewModel() {

    private val _bugItems = MutableStateFlow<List<BugItem>>(emptyList())
    val bugItems: StateFlow<List<BugItem>> get() = _bugItems

    init {
        val bugsArray = context.resources.getStringArray(R.array.bug_report)
        val initialBugItems = bugsArray.map { BugItem(title = it) }
        _bugItems.value = initialBugItems
    }

    fun updateBugItem(updatedItem: BugItem, newCompleted: Boolean) {
        val updatedList = _bugItems.value.map { item ->
            if (item.title == updatedItem.title) item.copy(checked = newCompleted) else item
        }
        _bugItems.value = updatedList
    }

    fun send() {
        //val intent = Intent(Intent.ACTION_SENDTO)
        val intent = Intent(Intent.ACTION_MAIN).apply {
            //addCategory(Intent.CATEGORY_APP_EMAIL)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
            putExtra(Intent.EXTRA_SUBJECT, Constants.ERR_SUBJECT)
            putExtra(Intent.EXTRA_TEXT, "msg")

        }
        /*intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(Configuration.MY_EMAIL))
        intent.putExtra(Intent.EXTRA_SUBJECT, Constants.ERR_SUBJECT)
        intent.putExtra(Intent.EXTRA_TEXT, "msg")*/
        //if (intent.resolveActivity(requireActivity().packageManager) != null) {
        context.applicationContext.startActivity(intent)
        //}
    }
}