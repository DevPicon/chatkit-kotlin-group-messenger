package co.creativitykills.groupchat.chatroom

import com.pusher.chatkit.messages.Message

interface ChatRoomContract {
    interface View {
        fun updateMessage(message: Message)
        fun clearData()
    }

    interface Presenter<T> {
        fun initialize(roomName: String?, roomId: String?)
        fun sendMessage(message: String)
    }

    companion object {
        fun createPresenterFromActivity(activity: ChatRoomActivity): Presenter<View> = ChatRoomPresenter(activity)
    }
}