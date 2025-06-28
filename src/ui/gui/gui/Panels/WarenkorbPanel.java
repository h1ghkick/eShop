package ui.gui.gui.Panels;

import domain.EShop;
import entities.Artikel;
import ui.gui.models.WarenkorbTableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class WarenkorbPanel extends JPanel {
    private final EShop eshop;
    private final JTable table;
    private final WarenkorbTableModel model;
    private final JLabel totalLabel;
    private final JButton removeButton;
    private final JButton buyButton;

    public WarenkorbPanel(EShop eshop) {
        this.eshop = eshop;

        // TableModel erzeugen
        Map<Artikel, Integer> waren = eshop.getWarenkorb().listeAusgeben();
        model = new WarenkorbTableModel(waren);
        table = new JTable(model);

        // Gesamtpreis berechnen
        double total = waren.entrySet().stream()
                .mapToDouble(e -> e.getKey().getPreis() * e.getValue())
                .sum();
        totalLabel = new JLabel("Gesamt: " + total + " €");

        removeButton = new JButton("Entfernen");
        buyButton = new JButton("Kaufen");

        initLayout();
        initEvents();
    }

    private void initLayout() {
        setLayout(new BorderLayout(10, 10));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(5, 5));
        south.add(totalLabel, BorderLayout.WEST);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        btnPanel.add(removeButton);
        btnPanel.add(buyButton);
        south.add(btnPanel, BorderLayout.EAST);

        add(south, BorderLayout.SOUTH);
    }

    private void initEvents() {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    // Artikel aus Domain entfernen
                    Artikel art = (Artikel) model.getValueAt(row, 1); // Titel-Spalte zum Vergleich
                    int menge = (int) model.getValueAt(row, 2);
                    eshop.getWarenkorb().artikelEntfernen(art, menge);

                    // Model aktualisieren
                    model.removeRow(row);
                    updateTotal();
                }
            }
        });

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Kauf auslösen
                    String email = eshop.getAktuellerUser().getMail();
                    eshop.Kaufen(eshop.getWarenkorb(), email);

                    JOptionPane.showMessageDialog(WarenkorbPanel.this,
                            "Danke für Ihren Einkauf!",
                            "Erfolg", JOptionPane.INFORMATION_MESSAGE);

                    // Leeren
                    model.removeAllRows();
                    updateTotal();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(WarenkorbPanel.this,
                            "Fehler beim Kaufen: " + ex.getMessage(),
                            "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void updateTotal() {
        double newTotal = eshop.getWarenkorb().listeAusgeben().entrySet().stream()
                .mapToDouble(e -> e.getKey().getPreis() * e.getValue())
                .sum();
        totalLabel.setText("Gesamt: " + newTotal + " €");
    }
}
