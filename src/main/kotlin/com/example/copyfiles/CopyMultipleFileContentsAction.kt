package com.example.copyfiles


import com.intellij.openapi.actionSystem.*
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection
import java.nio.charset.StandardCharsets

class CopyMultipleFileContentsAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY) ?: return
        val result = buildString {
            for (file in files) {
                if (!file.isDirectory && file.isValid) {
                    appendLine("==== Start of the File: ${file.name} ====")
                    appendLine(
                        String(file.contentsToByteArray(), StandardCharsets.UTF_8)
                    )
                    appendLine()
                    appendLine("==== End of the File: ${file.name} ====")
                }
            }
        }
        CopyPasteManager.getInstance().setContents(StringSelection(result.trim()))
    }

    override fun update(e: AnActionEvent) {
        val files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY)
        e.presentation.isEnabledAndVisible = !files.isNullOrEmpty()
    }
}