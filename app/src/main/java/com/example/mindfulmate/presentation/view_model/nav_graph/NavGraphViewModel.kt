package com.example.mindfulmate.presentation.view_model.nav_graph

import androidx.lifecycle.ViewModel
import com.example.mindfulmate.presentation.util.MessageModel
import com.example.mindfulmate.presentation.view_model.openai.ChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class NavGraphViewModel @Inject constructor(): ViewModel() {
    private val _showBottomSheet = MutableStateFlow(false)
    val showBottomSheet: StateFlow<Boolean> = _showBottomSheet.asStateFlow()

    private val _triggeredMessage = MutableStateFlow<String?>(null)
    val triggeredMessage: StateFlow<String?> = _triggeredMessage.asStateFlow()

    fun triggerBottomSheet(message: String?) {
        _triggeredMessage.value = message
        _showBottomSheet.value = true
    }

    fun dismissBottomSheet() {
        _triggeredMessage.value = null
        _showBottomSheet.value = false
    }

    fun setBottomSheetState(show: Boolean, message: String?) {
        _showBottomSheet.value = show
        _triggeredMessage.value = message
    }

    fun appendTriggeredMessageToChat(message: String, chatViewModel: ChatViewModel) {
        chatViewModel.addMessage(
            MessageModel(message = message, role = "assistant")
        )
        dismissBottomSheet()
    }

}
