package ui.cui.gui.Panels;

import javax.swing.*;
import entities.Artikel;
import domain.EShop;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddArtikelPanel extends JPanel {
    public interface AddBookListener {
        void onArtikelAdded(Artikel artikel);
    }

    private EShop eshop = null;
    private AddBookListener addListener = null;

    private JButton addButton;
    private JTextField numberTextField = null;
    private JTextField titleTextField = null;
    private JTextField artikelNummerTextField = null;
    private JTextField artikelpreisTextField = null;
    private JTextField artikelMengeTextField = null;
    private JTextField benutzerEmailTextField = null;

    public AddArtikelPanel(EShop eshop, AddBookListener addListener) {
        this.eshop = eshop;
        this.addListener = addListener;

        setupUI();

        setupEvents();
    }

    private void setupUI() {
        // BoxLayout (vertikal)
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Abstandhalter ("Filler") zwischen Rand und erstem Element
        Dimension borderMinSize = new Dimension(5, 10);
        Dimension borderPrefSize = new Dimension(5, 10);
        Dimension borderMaxSize = new Dimension(5, 10);
        this.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Eingabefelder
        this.add(new JLabel("Nummer: "));
        numberTextField = new JTextField();
        this.add(numberTextField);
        this.add(new JLabel("Titel: "));
        titleTextField = new JTextField();
        this.add(titleTextField);
        this.add(new JLabel("Artikelnummer: "));
        artikelNummerTextField = new JTextField();
        this.add(artikelNummerTextField);
        this.add(new JLabel("Artikelpreis: "));
        artikelpreisTextField = new JTextField();
        this.add(artikelpreisTextField);
        this.add(new JLabel("Menge: "));
        artikelMengeTextField = new JTextField();
        this.add(artikelMengeTextField);
        this.add(new JLabel("Email: "));
        benutzerEmailTextField = new JTextField();
        this.add(benutzerEmailTextField);




        // Abstandhalter ("Filler") zwischen Eingabefeldern und Button
        Dimension minSize = new Dimension(1, 20);
        Dimension prefSize = new Dimension(1, Short.MAX_VALUE);
        Dimension maxSize = new Dimension(1, Short.MAX_VALUE);
        Box.Filler filler = new Box.Filler(minSize, prefSize, maxSize);
        this.add(filler);

        // Button
        addButton = new JButton("Hinzufügen");
        this.add(addButton);

        // Abstandhalter ("Filler") zwischen letztem Element und Rand
        this.add(new Box.Filler(borderMinSize, borderPrefSize, borderMaxSize));

        // Umrandung
        this.setBorder(BorderFactory.createTitledBorder("Einfügen"));
    }

    private void setupEvents() {
        addButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        System.out.println("Event: " + ae.getActionCommand());
                        onAddButtonClick();
                    }
                });
    }

    private void onAddButtonClick() {
        String nummer = numberTextField.getText();
        String titel = titleTextField.getText();
        String artikelNummer = numberTextField.getText();
        String artikelpreis = artikelpreisTextField.getText();
        String artikelMenge = artikelMengeTextField.getText();
        String benutzerEmail = benutzerEmailTextField.getText();


        if (!nummer.isEmpty() && !titel.isEmpty()) {
            try {
                int nummerAlsInt = Integer.parseInt(nummer);
                int preisAlsInt = Integer.parseInt(artikelpreis);
                int artikelNummerAlsInt = Integer.parseInt(artikelNummer);

                Artikel artikel = new Artikel(nummerAlsInt, artikelNummerAlsInt, titel, preisAlsInt);
                eshop.artikelEinfuegen(artikel, Integer.parseInt(artikelMenge), benutzerEmail);
                numberTextField.setText("");
                titleTextField.setText("");
                artikelNummerTextField.setText("");
                artikelpreisTextField.setText("");

                // Am Ende Listener, d.h. unseren Frame benachrichtigen:
                addListener.onArtikelAdded(artikel);
            } catch (NumberFormatException nfe) {
                System.err.println("Bitte eine Zahl eingeben.");
            }
        }
    }
}
