package com.tex.contentprovider;

import android.content.SearchRecentSuggestionsProvider;

public class SuggestionContentProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.tex.contentprovider.SuggestionContentProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SuggestionContentProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
