package com.github.tobiasz.ideastatusbarfilename

import com.intellij.openapi.fileEditor.FileEditorManagerEvent
import com.intellij.openapi.fileEditor.FileEditorManagerListener
import com.intellij.openapi.project.DumbService
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.newvfs.VfsPresentationUtil
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.impl.status.EditorBasedWidget
import com.intellij.util.messages.MessageBusConnection

class FilenameStatusBarWidget(project: Project) : EditorBasedWidget(project), StatusBarWidget.MultipleTextValuesPresentation {

    private var filename: String? = null

    override fun ID(): String {
        return "Filename"
    }

    override fun getPresentation(): StatusBarWidget.WidgetPresentation? {
        return this
    }

    override fun getTooltipText(): String? {
        return null
    }

    override fun getSelectedValue(): String? {
        return filename
    }

    // handle the first file opened
    override fun install(statusBar: StatusBar) {
        super.install(statusBar)
        DumbService.getInstance(project).runWhenSmart { updateFilename(getSelectedFile()) }
    }

    // handle when a user changes to a new file
    override fun registerCustomListeners(connection: MessageBusConnection) {
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, object : FileEditorManagerListener {
            override fun selectionChanged(event: FileEditorManagerEvent) {
                updateFilename(event.newFile)
            }
        })
    }

    private fun updateFilename(file: VirtualFile?) {
        if (file == null) {
            return
        }
        filename = VfsPresentationUtil.getUniquePresentableNameForUI(project, file)
        myStatusBar?.updateWidget(ID())
    }

}