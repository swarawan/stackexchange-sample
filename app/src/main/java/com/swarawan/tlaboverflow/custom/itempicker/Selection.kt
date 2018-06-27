package com.swarawan.tlaboverflow.custom.itempicker

/**
 * Created by rioswarawan on 4/13/18.
 */
data class Selection(val name: String, var isSelected: Boolean = false) {

    companion object {
        fun toSelection(list: MutableList<String>): MutableList<Selection> {
            val data = mutableListOf<Selection>()
            list.forEach {
                data.add(Selection(it, false))
            }
            return data
        }
    }
}