package ui.gui.gui.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowClosingListener extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        Window window = e.getWindow();
        int result = JOptionPane.showConfirmDialog(window, "Wollen Sie die Anwendung wirklich schließen?");
        if (result == 0) {
            window.dispose();
            System.exit(0);
        }
    }
}