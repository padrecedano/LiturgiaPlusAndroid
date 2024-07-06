package org.deiverbum.app.ui.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _isSwitchOn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isSwitchOn = _isSwitchOn.asStateFlow()

    private val _textPreference: MutableStateFlow<String> = MutableStateFlow("")
    var textPreference = _textPreference.asStateFlow()

    private val _intPreference: MutableStateFlow<Int> = MutableStateFlow(0)
    var intPreference = _intPreference.asStateFlow()


    fun toggleSwitch() {
        _isSwitchOn.value = _isSwitchOn.value.not()
        // here is place for permanent storage handling - switch
    }

    fun saveText(finalText: String) {
        _textPreference.value = finalText
        // place to store text
    }

    // just checking, if it is not empty - but you can check anything
    fun checkTextInput(text: String) = text.isNotEmpty()

    // to get separator for the locale
    private val separatorChar = DecimalFormatSymbols.getInstance(Locale.ENGLISH).decimalSeparator

    // filtering only numbers and decimal separator
    fun filterNumbers(text: String): String = text.filter { it.isDigit() || it == separatorChar }

    // someone can still put more decimal points into the textfield
    // we should always try to convert text to number
    fun checkNumber(text: String): Boolean {
        val value = text.toDoubleOrNull() ?: return false
        return value < 0
    }

    // saving the number / show error if something goes wrong
    fun saveNumber(text: String) {
        val value = text.toDoubleOrNull()
            ?: 0 // default value / handle the error in some way - show toast or something

    }


    companion object {
        const val TAG = "SettingsViewModel"
    }

}