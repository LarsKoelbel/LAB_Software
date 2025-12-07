package com.example.task_4.library_project.Elements;

import com.example.task_4.WikibooksController;

/**
 * Base class for a ui element
 */
abstract public class UIElement {
    WikibooksController context;

    public UIElement(WikibooksController context)
    {
        this.context = context;
    }

    public WikibooksController getContext() {
        return context;
    }
}
