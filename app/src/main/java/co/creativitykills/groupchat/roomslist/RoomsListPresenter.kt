package co.creativitykills.groupchat.roomslist

import android.util.Log
import co.creativitykills.groupchat.AppController
import com.pusher.chatkit.*
import com.pusher.chatkit.rooms.Room
import com.pusher.util.Result

class RoomsListPresenter(view: RoomsListContract.View, val navigator: RoomsListContract.Navigator) : RoomsListContract.Presenter<RoomsListContract.View> {
    override fun onSelectedRoom(room: Room) {
        if (room.memberUserIds.contains(currentUser.id)) {
            // user already belongs to this room
            navigator.roomJoined(room)
        } else {
            currentUser.joinRoom(
                    roomId = room.id,
                    callback = { result ->
                        when (result) {
                            is Result.Success -> {
                                // Joined the room!
                                navigator.roomJoined(result.value)
                            }
                            is Result.Failure -> {
                                Log.d("TAG", result.error.toString())
                            }
                        }
                    }
            )
        }
    }

    private lateinit var localView: RoomsListContract.View
    private var roomList = arrayListOf<Room>()
    private lateinit var currentUser: CurrentUser

    override fun initChatManager() {
        val chatManager = createChatManager()
        chatManager.connect(listeners = ChatListeners(
                onErrorOccurred = { },
                onAddedToRoom = { },
                onRemovedFromRoom = { },
                onCurrentUserReceived = { },
                onNewReadCursor = { },
                onRoomDeleted = { },
                onRoomUpdated = { },
                onPresenceChanged = { u, n, p -> },
                onUserJoinedRoom = { u, r -> },
                onUserLeftRoom = { u, r -> },
                onUserStartedTyping = { u, r -> },
                onUserStoppedTyping = { u, r -> }
        )) {
            when (it) {
                is Result.Success -> {
                    currentUser = it.value
                    AppController.currentUser = currentUser
                    roomList.clear()
                    roomList.addAll(currentUser.rooms)
                    localView.updateList(roomList)

                    currentUser.getJoinableRooms { result ->
                        when (result) {
                            is Result.Success -> {
                                roomList.addAll(result.value)
                                localView.updateList(roomList)
                            }
                        }
                    }
                }

                is Result.Failure -> {
                    // Failure
                    Log.d("TAG", it.error.toString())
                }
            }
        }
    }

    private fun createChatManager() = ChatManager(
            instanceLocator = "",
            userId = "",
            dependencies = AndroidChatkitDependencies(
                    tokenProvider = ChatkitTokenProvider(
                            endpoint = "",
                            userId = ""
                    )
            )
    )

    init {
        attachView(view)
    }

    override fun attachView(view: RoomsListContract.View) {
        this.localView = view
    }

    override fun deattachView(view: RoomsListContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}