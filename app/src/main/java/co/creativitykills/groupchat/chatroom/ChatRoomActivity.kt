package co.creativitykills.groupchat.chatroom

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import co.creativitykills.groupchat.R
import com.pusher.chatkit.messages.Message
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity(), ChatRoomContract.View {
    private lateinit var presenter: ChatRoomContract.Presenter<ChatRoomContract.View>
    private lateinit var adapter: ChatMessageAdapter

    override fun clearData() {
        edit_text.text.clear()
        hideKeyboard()
    }

    override fun updateMessage(message: Message) {
        Log.d("TAG", message.text)
        adapter.addMessage(message)
        recycler_view.layoutManager?.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val roomName = intent.getStringExtra("room_name")
        val roomId = intent.getStringExtra("room_id")

        supportActionBar!!.title = roomName
        adapter = ChatMessageAdapter()
        setUpRecyclerView()

        presenter = ChatRoomContract.createPresenterFromActivity(this)
        presenter.initialize(roomName, roomId)

        button_send.setOnClickListener {
            if (edit_text.text.isNotEmpty()) {
                val message = edit_text.text.toString()
                presenter.sendMessage(message)
            }
        }
    }

    private fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus

        if (view == null) {
            view = View(this)
        }

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setUpRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this@ChatRoomActivity)
        recycler_view.adapter = adapter
    }
}
