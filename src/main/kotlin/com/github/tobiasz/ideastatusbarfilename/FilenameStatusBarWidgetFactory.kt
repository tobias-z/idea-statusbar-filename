package com.github.tobiasz.ideastatusbarfilename

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.widget.StatusBarEditorBasedWidgetFactory


class FilenameStatusBarWidgetFactory : StatusBarEditorBasedWidgetFactory() {

    override fun getId(): String {
        return "Filename"
    }

    override fun getDisplayName(): String {
        return "Filename"
    }

    override fun createWidget(project: Project): StatusBarWidget {
        return FilenameStatusBarWidget(project)
    }

    override fun disposeWidget(widget: StatusBarWidget) {
        Disposer.dispose(widget)
    }

}
