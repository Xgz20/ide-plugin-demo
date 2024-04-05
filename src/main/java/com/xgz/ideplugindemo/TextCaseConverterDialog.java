package com.xgz.ideplugindemo;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.util.text.StringUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * @author: Xgz
 * @date: 2024/4/4
 */
public class TextCaseConverterDialog extends DialogWrapper {
    private static final Logger LOGGER = Logger.getInstance(TextCaseConverterDialog.class);
    private final String selectedText;
    private String convertedText;

    public TextCaseConverterDialog(String selectedText) {
        super(true);
        this.selectedText = selectedText;
        init();
        setTitle("文本大小写转换");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // JComboBox<String> comboBox = new JComboBox<>(new String[]{"大写", "小写"});
        ComboBox<String> comboBox = new ComboBox<>(new String[]{"大写", "小写"});

        comboBox.addActionListener(e -> {
            LOGGER.info("selectedText: " + selectedText);
            String choice = (String) comboBox.getSelectedItem();
            LOGGER.info("choice: " + choice);

            if ("大写".equals(choice)) {
                convertedText = selectedText.toUpperCase();
            } else {
                convertedText = selectedText.toLowerCase();
            }
            LOGGER.info("convertedText: " + convertedText);
        });

        LabeledComponent<JComboBox<String>> labeledComponent = LabeledComponent.create(comboBox, "转换方式");
        panel.add(labeledComponent, BorderLayout.NORTH);

        return panel;
    }

    public String getConvertedText() {
        // 如果没有手动选择，则默认转化成大写（防止传入null值导致插件报错）
        if (StringUtil.isEmpty(convertedText)) {
            return selectedText.toUpperCase();
        }
        return convertedText;
    }
}
