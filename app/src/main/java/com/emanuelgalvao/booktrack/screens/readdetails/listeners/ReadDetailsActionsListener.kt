package com.emanuelgalvao.booktrack.screens.readdetails.listeners

interface ReadDetailsActionsListener {
    fun onDeleteClick()
    fun onCurrentPageSaveClick(currentPage: Int)
    fun onStartStopReadingClick()
}