package co.creativitykills.groupchat.chatroom

import android.util.Log
import co.creativitykills.groupchat.AppController
import com.pusher.chatkit.CurrentUser
import com.pusher.chatkit.messages.multipart.NewPart
import com.pusher.chatkit.rooms.RoomListeners
import com.pusher.util.Result
import elements.Error

class ChatRoomPresenter(view: ChatRoomContract.View) : ChatRoomContract.Presenter<ChatRoomContract.View> {

    private lateinit var roomId: String
    private lateinit var currentUser: CurrentUser

    private var localView: ChatRoomContract.View = view

    override fun sendMessage(message: String) {
        val elements = listOf<NewPart>(NewPart.Inline(message))

        currentUser.sendMultipartMessage(
                roomId,
                elements,
                callback = { result: Result<Int, Error> ->
                    //Result<Int, Error>
                    when (result) {

                        is Result.Success<Int, Error> -> {
                            Log.d("TAG", "error: ${result.value}")
                        }
                        is Result.Failure<*, *> -> {
                            Log.d("TAG", "error: ${result.error}")
                        }
                    }
                }
        )

    }

    override fun initialize(roomName: String?, roomId: String?) {
        this.roomId = roomId!!
        currentUser = AppController.currentUser
        currentUser.subscribeToRoomMultipart(
                roomId = roomId,
                listeners = RoomListeners(
                        onMessage = { message ->
                            localView.updateMessage(message)
                        },
                        onErrorOccurred = { error ->
                            Log.d("TAG", error.toString())
                        }
                ),
                messageLimit = 100, // Optional
                callback = { subscription ->
                    // Called when the subscription has started.
                    // You should terminate the subscription with subscription.unsubscribe()
                    // when it is no longer needed
                    Log.d("TAG", subscription.toString())
                }
        )

    }
}