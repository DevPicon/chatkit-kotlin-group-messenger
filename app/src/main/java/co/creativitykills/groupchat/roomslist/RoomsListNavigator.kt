package co.creativitykills.groupchat.roomslist

import android.content.Intent
import co.creativitykills.groupchat.ChatRoomActivity
import com.pusher.chatkit.rooms.Room

class RoomsListNavigator(val activity: RoomsListActivity) : RoomsListContract.Navigator {
    override fun roomJoined(room: Room) {
        val intent = Intent(activity, ChatRoomActivity::class.java)
        intent.putExtra("room_id", room.id)
        intent.putExtra("room_name", room.name)
        activity.startActivity(intent)
    }
}