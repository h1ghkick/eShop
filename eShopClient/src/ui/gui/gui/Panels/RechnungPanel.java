package ui.gui.gui.Panels;

import entities.Artikel;
import entities.Kunde;
import entities.Rechnung;
import ui.gui.models.RechnungTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class RechnungPanel extends JPanel {
    public RechnungPanel(Rechnung rechnung) {
        setLayout(new BorderLayout(10, 10));

        // Header: Kundendaten + Datum
        Kunde kunde = rechnung.getKunde();
        JLabel nameLabel = new JLabel("Kunde: " + kunde.getFirstName() + " " + kunde.getLastName());
        JLabel datumLabel = new JLabel("Datum: " + rechnung.getKaufDatum());

        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        datumLabel.setFont(datumLabel.getFont().deriveFont(Font.BOLD));

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.add(nameLabel);
        headerPanel.add(datumLabel);

        add(headerPanel, BorderLayout.NORTH);

        // Tabelle mit Artikeln
        JTable table = new JTable(new RechnungTableModel(rechnung.getArtikelListe()));
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Gesamtpreis unten
        JLabel totalLabel = new JLabel("Gesamt: " + rechnung.getGesamtpreis() + " €");
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(totalLabel);
        add(footer, BorderLayout.SOUTH);
    }
}

