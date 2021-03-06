package de.tuxed.codefellow.gui;

import de.tuxed.codefellow.MethodInfo;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.util.regex.Pattern;

class MethodCellRenderer implements ListCellRenderer {

    private final MainPanel mainPanel;

    public MethodCellRenderer(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        MethodInfo m = (MethodInfo) value;
        MethodInfoLabel label = new MethodInfoLabel(m);

        if (isSelected) {
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            if (!mainPanel.isMethodListFiltered()
                    && !mainPanel.getMethodFilter().equals("")
                    && Pattern.compile(mainPanel.getMethodFilter(), Pattern.MULTILINE).matcher(m.getMethod().getName()).find()) {
                label.setBackground(new Color(255, 255, 190));
            } else {
                label.setBackground(list.getBackground());
            }
            label.setForeground(list.getForeground());
        }
        label.setEnabled(list.isEnabled());
        label.setOpaque(true);
        return label;
    }

}
