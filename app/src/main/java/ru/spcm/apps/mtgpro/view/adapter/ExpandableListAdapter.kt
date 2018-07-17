package ru.spcm.apps.mtgpro.view.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.CheckBox
import android.widget.TextView
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.FilterItem
import ru.spcm.apps.mtgpro.model.dto.FilterOption
import java.util.*
import kotlin.collections.HashMap

class ExpandableListAdapter(private val activity: Activity,
                            private val items: List<FilterItem>) : BaseExpandableListAdapter() {

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val group = getGroup(groupPosition)
        val view = convertView
                ?: activity.layoutInflater.inflate(R.layout.list_item_filter_group, parent, false)

        val groupTitle = view.findViewById<TextView>(R.id.filterGroupTitle)
        groupTitle.text = group.title
        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        val option = getChild(groupPosition, childPosition)

        val view = convertView
                ?: activity.layoutInflater.inflate(R.layout.list_item_filter_option, parent, false)
        val optionBox = view.findViewById<CheckBox>(R.id.filterCheckbox)
        if (convertView == null) {
            optionBox.setOnClickListener { v ->
                val c = v.getTag(R.string.tag_id) as Int
                val p = v.getTag(R.string.tag_parent) as Int
                items[p].options[c].selected = (v as CheckBox).isChecked
            }
        }
        optionBox.text = option.title
        optionBox.setTag(R.string.tag_id, childPosition)
        optionBox.setTag(R.string.tag_parent, groupPosition)
        optionBox.isChecked = items[groupPosition].options[childPosition].selected
        return view
    }

    fun getSelectedItems(): List<FilterItem> {
        return items
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return items[groupPosition].options.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): FilterOption {
        return items[groupPosition].options[childPosition]
    }

    override fun getGroup(groupPosition: Int): FilterItem {
        return items[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return items.size
    }

}