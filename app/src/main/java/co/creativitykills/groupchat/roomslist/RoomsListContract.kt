package co.creativitykills.groupchat.roomslist

import com.pusher.chatkit.rooms.Room
import java.util.*

interface RoomsListContract {
    interface View {
        fun updateList(roomList: ArrayList<Room>)
    }

    interface Presenter<T> {
        fun attachView(view: T)
        fun deattachView(view: T)
        fun initChatManager()
        fun onSelectedRoom(room: Room)
    }

    interface Navigator {
        fun roomJoined(room: Room)
    }

    companion object {
        fun createPresenterFromActivity(roomsListActivity: RoomsListActivity): Presenter<View> {
            return RoomsListPresenter(roomsListActivity, RoomsListNavigator(roomsListActivity))
        }
    }
}