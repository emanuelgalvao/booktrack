package com.emanuelgalvao.booktrack.screens.addbook.listeners

interface AddBookActionsListener {
    fun onSearchClick(title: String)
    fun onBookSelect(bookId: String)
    fun onAddBookClick()
}