package com.emanuelgalvao.booktrack.screens.home.listeners

interface HomeActionsListeners {
    fun onAddBookClick()
    fun onTryAgainClick()
    fun onBookClick(bookId: String)
}