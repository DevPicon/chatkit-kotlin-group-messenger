package co.creativitykills.groupchat.manager

import com.pusher.chatkit.AndroidChatkitDependencies
import com.pusher.chatkit.ChatManager
import com.pusher.chatkit.ChatkitTokenProvider

fun createChatManager() = ChatManager(
        instanceLocator = "",
        userId = "",
        dependencies = AndroidChatkitDependencies(
                tokenProvider = ChatkitTokenProvider(
                        endpoint = "",
                        userId = ""
                )
        )
)
