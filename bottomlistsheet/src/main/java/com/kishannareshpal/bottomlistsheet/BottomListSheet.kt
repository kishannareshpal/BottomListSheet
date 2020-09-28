package com.kishannareshpal.bottomlistsheet

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.kishannareshpal.bottomlistsheet.adapters.BottomListSheetAdapter
import com.kishannareshpal.bottomlistsheet.models.Item

class BottomListSheet: BottomSheetDialogFragment() {

    // View model
    private var viewModel: BottomListSheetViewModel? = null
    private lateinit var ctx: Context
    private var adapter: BottomListSheetAdapter? = null // reycler view adapter for the items.

    // Private Data
    private var title: String? = null
    private var subtitle: String? = null
    private var itemsList: List<Item>? = null
    private var itemSelectedListener: ((Int) -> Unit)? = null
    private var confirmClickListener: ((View, List<Int>) -> Unit)? = null
    private var closeClickListener: ((View, List<Int>) -> Unit)? = null
    private var dismissOnConfirm: Boolean = true // whether or not the confirm button will dismiss the dialog on click.
    private var preselectedItemsPositions: List<Int> = listOf() // preselect items at these positions on show.

    /**
     * Set if the multiple selection should be enabled or not.
     */
    var isMultipleSelection: Boolean = true
        set(value) {
            viewModel?.setMultipleSelection(value)
        }


    /**
     * Get the positions of all selected items as a list.
     * @return List<Int> list of all selected items positions.
     */
    val selectedItemsPositions: List<Int>
        get() = adapter?.getSelectedItems() ?: listOf()



    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.ctx = context
        context.setTheme(R.style.Theme_BottomListSheet); // todo implement night mode (or dynamic)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.list_sheet_layout, container, false)
    }

    override fun getTheme(): Int = R.style.Theme_BottomListSheet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize our ViewModel
        viewModel = ViewModelProvider(this).get(BottomListSheetViewModel::class.java)

        // Initialize the components
        val tv_title: TextView = view.findViewById(R.id.tv_title)
        val tv_subtitle: TextView = view.findViewById(R.id.tv_subtitle)
        val rv_items: RecyclerView = view.findViewById(R.id.rv_items)
        val btn_confirm: MaterialButton = view.findViewById(R.id.btn_confirm)
        val btn_close: ImageButton = view.findViewById(R.id.btn_close)

        // Setup the components
        btn_confirm.setOnClickListener {
            // Custom click listener on confirm button.
            this.confirmClickListener?.invoke(it, selectedItemsPositions)
            if (dismissOnConfirm) this.dismiss() // whether or not we should dismiss this sheet on click.
        }

        btn_close.setOnClickListener {
            // Custom click listener on close button.
            this.closeClickListener?.invoke(it, selectedItemsPositions)
            this.dismiss() // Always dismiss the sheet.
        }


        // Observe data updates
        viewModel!!.title.observe(viewLifecycleOwner) {
            // Change the title.
            this.title = it
            tv_title.text = it
            if (it != null) tv_title.visibility = View.VISIBLE else tv_title.visibility = View.GONE
        }

        viewModel!!.subtitle.observe(viewLifecycleOwner) {
            // Change the subtitle
            this.subtitle = it
            tv_subtitle.text = it
            if (it != null) tv_subtitle.visibility = View.VISIBLE else tv_subtitle.visibility = View.GONE
        }

        viewModel!!.itemsList.observe(viewLifecycleOwner) {
            // Change the items list
            this.itemsList = it
            it?.let { listOfItems ->

                adapter = BottomListSheetAdapter(ctx, listOfItems, isMultipleSelection)
                // Set a listener for each item click.
                adapter!!.onItemSelectedListener = {
                    if (isMultipleSelection) {
                        // Select/Deselect the currently tapped item.
                        adapter!!.toggleSelection(it)
                        // Enable the confirm button when there's a change on the selected items, according to the previouly selected items.
                        val shouldEnableConfirmBtn = preselectedItemsPositions != selectedItemsPositions
                        btn_confirm.isEnabled = shouldEnableConfirmBtn

                    } else {
                        // For single selection only, we must automatically dismiss the sheet on tap.
                        this.dismiss()
                    }
                    // Execute the custom click block if available.
                    itemSelectedListener?.invoke(it)
                }

                // Set the layout manager (Vertical) and adapter of the recycler view.
                 rv_items.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
                rv_items.adapter = adapter
            }
        }

        viewModel!!.preselectedItemsPositions.observe(viewLifecycleOwner) {
            // Change preselected items positions.
            this.preselectedItemsPositions = it.orEmpty()
            it?.forEach { preselectedPosition ->
                // Toggle selection of each item.
                adapter?.toggleSelection(preselectedPosition)
            }
        }

        viewModel!!.onItemSelectedListener.observe(viewLifecycleOwner) {
            // Custom click listener for each tapped item on recycler view.
            this.itemSelectedListener = it
        }

        viewModel!!.isMultipleSelection.observe(viewLifecycleOwner) { enabled ->
            // Change the sheet to allow multiple selection, or not.
            btn_confirm.visibility = if (enabled) View.VISIBLE else View.GONE
            btn_confirm.isEnabled = false // disable by default. will only be enabled on selection change (if it's multiple selection)
        }

        viewModel!!.dismissOnConfirm.observe(viewLifecycleOwner) {
            // Change whether or not the sheet should automatically dismiss on confirm button click.
            this.dismissOnConfirm = it
        }

        viewModel!!.onConfirmClickListener.observe(viewLifecycleOwner) {
            // Custom click listener for the confirm button. By default this button will dismissed the sheet.
            this.confirmClickListener = it
        }

        viewModel!!.onCloseClickListener.observe(viewLifecycleOwner) {
            // Custom click listener for the close button. By default this button will dismiss the sheet.
            this.closeClickListener = it
        }

        // Manually update these values on first creation
        viewModel!!.let {
            it.setTitle(title)
            it.setSubtitle(subtitle)
            it.setItemsList(itemsList)
            it.setPreselectedItemsPositions(preselectedItemsPositions)
            it.setOnItemSelectedListener(itemSelectedListener)
            it.setMultipleSelection(isMultipleSelection)
            it.setDismissOnConfirm(dismissOnConfirm)
            it.setOnConfirmClickListener(confirmClickListener)
            it.setOnCloseClickListener(closeClickListener)
        }


    }



    // Public methods
    /**
     * Change the title of the sheet
     * @param titleText The new title to change to.
     *                  Otherwise pass null to remove the title. And if this is the case, please
     *                  use {@link BottomSheetList#subtitle}.
     */
    fun title(titleText: String?): BottomListSheet {
        if (viewModel == null) {
            this.title = titleText

        } else {
            viewModel!!.setTitle(titleText)
        }
        return this
    }

    /**
     * Change the subtitle of the sheet
     * @param subtitleText The new subtitle to change to.
     *                     Otherwise pass null to remove the subtitle. And if this is the case, please
     *                     use {@link BottomSheetList#title}.
     */
    fun subtitle(subtitleText: String?): BottomListSheet {
        if (viewModel == null) {
            this.subtitle = subtitleText
        } else {
            viewModel!!.setSubtitle(subtitleText)
        }
        return this
    }


    /**
     * Change the list of items to display as selectables.
     * @param itemsList the list of items. If you pass in null, it will automatically default to an empty list.
     */
    fun items(itemsList: List<Item>?): BottomListSheet {
        if (viewModel == null) {
            this.itemsList = itemsList
        } else {
            viewModel?.setItemsList(itemsList)
        }
        return this;
    }

    /**
     * Change the preselected items on the list on show.
     * @param itemsPositions the position of items (zero-indexed) to be preselected
     */
    fun preselectItems(itemsPositions: List<Int>): BottomListSheet {
        if (viewModel == null) {
            this.preselectedItemsPositions = itemsPositions
        } else {
            viewModel?.setPreselectedItemsPositions(itemsPositions)
        }
        return this;
    }

    /**
     * Change the default item selected click listener.
     * By default, on multiple selection -> toggle selection
     *             and on single selectino -> dismiss the sheet
     * @param listener the lambda of the executable block.
     *                 (position: Item) is the curently tapped item position.
     */
    fun onItemSelected(listener: ((position: Int) -> Unit)?): BottomListSheet {
        if (viewModel == null) {
            this.itemSelectedListener = listener
        } else {
            viewModel?.setOnItemSelectedListener(listener)
        }
        return this;
    }

    /**
     * Change the default click listener of the close button.
     * By default it will dismiss the sheet.
     * @param clickListener the lambda of the executable block.
     *                      (button: View) is the tapped button.
     *                      (selectedItems: List<Item>) is a list of selected items positions.
      */
    fun onClose(clickListener: ((button: View, selectedItems: List<Int>) -> Unit)?): BottomListSheet {
        if (viewModel == null) {
            this.closeClickListener = clickListener
        } else {
            viewModel?.setOnCloseClickListener(clickListener)
        }
        return this;
    }

    /**
     * Change the default click listener of the confirm button.
     * By default it will dismiss the sheet.
     * @param clickListener the lambda of the executable block.
     *                      (button: View) is the tapped button.
     *                      (selectedItems: List<Item>) is a list of selected items positions.
     */
    fun onConfirm(dismissOnConfirm: Boolean = true, clickListener: ((button: View, selectedItems: List<Int>) -> Unit)?): BottomListSheet {
        if (viewModel == null) {
            this.confirmClickListener = clickListener
            this.dismissOnConfirm = dismissOnConfirm
        } else {
            viewModel?.setOnConfirmClickListener(clickListener)
            viewModel?.setDismissOnConfirm(dismissOnConfirm)
        }
        return this;
    }



    // Public Utility methods
    /**
     * Clear all selected items, if the sheet is set to be multiple selection.
     */
    fun clearAllSelection() {
        adapter?.clearSelection()
    }


    /**
     * Show the sheet.
     * @see dismiss to hide the sheet.
     */
    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, "com.kishan.bottomlistsheet")
    }
}