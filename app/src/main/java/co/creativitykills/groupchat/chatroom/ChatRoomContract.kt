package co.creativitykills.groupchat.chatroom

interface ChatRoomContract {
    interface View {
        fun updateMessage(message: com.pusher.chatkit.messages.multipart.Message)
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