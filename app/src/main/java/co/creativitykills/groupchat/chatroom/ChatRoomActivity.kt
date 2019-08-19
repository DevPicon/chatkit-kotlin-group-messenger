package co.creativitykills.groupchat.chatroom

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import co.creativitykills.groupchat.R
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomActivity : AppCompatActivity(), ChatRoomContract.View {
    private lateinit var presenter: ChatRoomContract.Presenter<ChatRoomContract.View>
    private lateinit var adapter: ChatMessageAdapter

    override fun clearData() {
        edit_text.text.clear()
        hideKeyboard()
    }

    override fun updateMessage(message: com.pusher.chatkit.messages.multipart.Message) {
        Log.d("TAG", message.toString())
        adapter.addMessage(message)
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
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = adapter
    }
}
