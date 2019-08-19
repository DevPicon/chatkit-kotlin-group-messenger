package co.creativitykills.groupchat.roomslist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import co.creativitykills.groupchat.R
import co.creativitykills.groupchat.RoomsAdapter
import com.pusher.chatkit.rooms.Room
import kotlinx.android.synthetic.main.activity_rooms_list.*

class RoomsListActivity : AppCompatActivity(), RoomsListContract.View {
    private val adapter = RoomsAdapter()
    lateinit var presenter: RoomsListContract.Presenter<RoomsListContract.View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms_list)
        presenter = RoomsListContract.createPresenterFromActivity(this)
        initRecyclerView()
        presenter.initChatManager()
    }

    override fun updateList(roomList: java.util.ArrayList<Room>) {
        for (i in 0 until roomList.size) {
            adapter.addRoom(roomList[i])
        }
    }

    private fun initRecyclerView() {
        adapter.setInterface(object : RoomsAdapter.RoomClickedInterface {
            override fun roomSelected(room: Room) {
                presenter.onSelectedRoom(room)
            }
        })

        recycler_view.layoutManager = LinearLayoutManager(this@RoomsListActivity)
        recycler_view.adapter = adapter
    }
}