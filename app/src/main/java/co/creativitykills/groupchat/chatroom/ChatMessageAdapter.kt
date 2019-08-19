package co.creativitykills.groupchat.chatroom

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.creativitykills.groupchat.R
import com.pusher.chatkit.messages.multipart.Message
import com.pusher.chatkit.messages.multipart.Payload

class ChatMessageAdapter : RecyclerView.Adapter<ChatMessageAdapter.ViewHolder>() {

    private var list = ArrayList<Message>()

    fun addMessage(e: Message) {
        list.add(e)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.custom_chat_row, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = list[position].sender.name

        list[position].parts.forEach { part ->
            when (val payload = part.payload) {
                is Payload.Inline -> {
                    holder.message.text = payload.content
                }
                is Payload.Url -> {
                    holder.message.text = payload.url.toString()
                }
                is Payload.Attachment -> {
                }
                else -> {
                }
            }
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById(R.id.text_user_name)
        var message: TextView = itemView.findViewById(R.id.chat_message)
    }
}