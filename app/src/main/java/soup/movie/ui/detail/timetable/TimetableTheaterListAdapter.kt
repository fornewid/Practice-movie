package soup.movie.ui.detail.timetable

import android.view.ViewGroup
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_timetable_theater.view.*
import soup.movie.R
import soup.movie.data.model.Theater
import soup.movie.data.model.TheaterWithTimetable
import soup.movie.ui.helper.databinding.DataBindingListAdapter
import soup.movie.ui.helper.databinding.DataBindingViewHolder
import soup.movie.util.inflate
import soup.widget.recyclerview.callback.IdBasedDiffCallback

internal class TimetableTheaterListAdapter(private val listener: Listener) :
        DataBindingListAdapter<TheaterWithTimetable>(IdBasedDiffCallback()) {

    interface Listener {

        fun onItemClick(item: TheaterWithTimetable)

        fun onItemClick(item: Theater)

        fun onItemClick(item: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<TheaterWithTimetable> =
            super.onCreateViewHolder(parent, viewType).apply {
                itemView.setOnClickListener {
                    listener.onItemClick(getItem(adapterPosition))
                }
                itemView.theaterView.setOnClickListener { _ ->
                    listener.onItemClick(getItem(adapterPosition).theater)
                }
            }

    override fun onBindViewHolder(holder: DataBindingViewHolder<TheaterWithTimetable>, position: Int) {
        super.onBindViewHolder(holder, position)
        getItem(position)?.run {
            holder.itemView.timeListView?.run {
                removeAllViews()
                timeList.map { time ->
                    inflate<Chip>(context, R.layout.chip_time).apply {
                        text = time
                        setOnClickListener {
                            listener.onItemClick(time)
                        }
                    }
                }.forEach { addView(it) }
            }
        }
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_timetable_theater
}