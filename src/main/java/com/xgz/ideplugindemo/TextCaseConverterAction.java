package com.xgz.ideplugindemo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

/**
 * @author: Xgz
 * @date: 2024/4/4
 */
public class TextCaseConverterAction extends AnAction {
    private static final Logger LOGGER = Logger.getInstance(TextCaseConverterAction.class);

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getRequiredData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR);

        // 获取选中的文本
        String selectedText = editor.getSelectionModel().getSelectedText();

        if (selectedText == null || selectedText.isEmpty()) {
            Messages.showErrorDialog(project, "请先选择要转换的文本。", "未选择文本");
            return;
        }

        // 显示对话框
        TextCaseConverterDialog dialog = new TextCaseConverterDialog(selectedText);
        if (dialog.showAndGet()) {
            // 获取转换后的文本并替换选中文本
            String newText = dialog.getConvertedText();
            LOGGER.info("newText: " + newText);

            Application applicationManager = ApplicationManager.getApplication();
            WriteCommandAction.runWriteCommandAction(project, new Runnable() {
                @Override
                public void run() {
                    applicationManager.runWriteAction(() ->
                            editor.getDocument().replaceString(editor.getSelectionModel().getSelectionStart(),
                                    editor.getSelectionModel().getSelectionEnd(), newText)
                    );
                }
            });
        }
    }
}
