package com.kishannareshpal.bottomlistsheet.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.checkbox.MaterialCheckBox
import com.kishannareshpal.bottomlistsheet.R
import com.kishannareshpal.bottomlistsheet.models.Item
import com.kishannareshpal.bottomlistsheet.utils.setSafeClickListener

/**
 * @param ctx the context of where the adapter is initialized
 * @param itemsList the list of items to show
 * @param isMultipleSelection whether this adapter should allow multiple selection of items or not.
 */
class BottomListSheetAdapter(private val ctx: Context, var itemsList: List<Item>, var isMultipleSelection: Boolean = false) : SelectableAdapter<BottomListSheetAdapter.ItemViewHolder>() {

    // Data
    var onItemSelectedListener: ((position: Int) -> Unit)? = null

    override fun getItemCount(): Int = itemsList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = itemsList[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(ctx)
        val view: View = inflater.inflate(R.layout.sheet_list_item, parent, false)
        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var iv_image: ImageView? = null
        var tv_text: TextView? = null
        var cb_selection: MaterialCheckBox? = null

        // Initialize the view holder, by intializing the components.
        init {
            iv_image = view.findViewById(R.id.iv_image)
            tv_text = view.findViewById(R.id.tv_text)
            cb_selection = view.findViewById(R.id.cb_selection)

            // Allow clickable rows
            val defaultClickInterval = if (isMultipleSelection) 0 else 1600 // only add interval between clicks for multiple selection items.

            // We are using safeClickListener, which is an extension set in [utils.MyUtils.kt]
            // for the purpose of preventing spam clicks on any view, by giving an iterval between accepted clicks.
            view.setSafeClickListener(defaultClickInterval) {
                onItemSelectedListener?.let {
                    if (adapterPosition != RecyclerView.NO_POSITION) { // To prevent errors when clicking a view that has no position bound to it (e.g.: a card that we may have deleted seconds ago)
                        it.invoke(adapterPosition)
                    }
                }
            }
        }

        // Bind the data to the components
        fun bind(item: Item) {
            // Configure Icon
            if (item.iconRes != null) {
                // Show the icon
                Glide.with(ctx)
                    .load(item.iconRes)
                    .into(iv_image!!)

                iv_image?.visibility = View.VISIBLE

            } else {
                // Hide the icon
                iv_image?.visibility = View.GONE
            }

            // Configure text
            tv_text?.text = item.text


            // Configure multiple selection
            if (isMultipleSelection) {
                // Allow multiple selection.
                cb_selection?.visibility = View.VISIBLE
                cb_selection?.isChecked = isPositionSelected(adapterPosition)

            } else {
                // Don't allow multiple selection.
                cb_selection?.visibility = View.GONE;
            }
        }
    }
}