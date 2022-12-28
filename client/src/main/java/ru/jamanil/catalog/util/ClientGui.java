package ru.jamanil.catalog.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.jamanil.catalog.controller.ItemGuiController;
import ru.jamanil.catalog.model.Item;
import ru.jamanil.catalog.service.ClientItemService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * @author Victor Datsenko
 * 23.12.2022
 */
@Component
@RequiredArgsConstructor
public class ClientGui extends JFrame {
    private final ItemGuiController controller;
    private final ClientItemService service;
    private final JLabel parseLabel = new JLabel("Catalog address:");
    private final JTextField parseField = new JTextField(5);
    private final JButton parseButton = new JButton("Parse");
    private final JLabel findLabel = new JLabel("Find Item by:");
    private final JTextField findField = new JTextField(5);
    private final JButton findButton = new JButton("Find");
    private final JLabel foundedLabel = new JLabel("Founded items:");
    private final JTextArea foundedText = new JTextArea(20, 40);


    public void showClientGui() {
        this.setBounds(100, 100, 480, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel parsePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        parsePanel.add(parseLabel);
        parsePanel.add(parseField);
        parsePanel.add(parseButton);
        parseButton.addActionListener(new ParseButtonListener());
        container.add(parsePanel);

        JPanel findPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        findPanel.add(findLabel);
        findPanel.add(findField);
        findPanel.add(findButton);
        findButton.addActionListener(new FindButtonListener());
        container.add(findPanel);

        JPanel foundedLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        foundedLabelPanel.add(foundedLabel);
        container.add(foundedLabelPanel);

        JPanel foundedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        foundedText.setLineWrap(true);
        foundedText.setWrapStyleWord(true);
        foundedPanel.add(foundedText);
        foundedPanel.add(new JScrollPane(foundedText));
        container.add(foundedPanel);

        this.setVisible(true);
    }

    class ParseButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String catalogName = parseField.getText();
            if (catalogName.isEmpty())
                JOptionPane.showMessageDialog(null, "Catalog address is empty", "Error", JOptionPane.WARNING_MESSAGE);
            else
                controller.parseCatalog(catalogName);
        }
    }

    class FindButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String findBy = findField.getText();
            if (findBy.isEmpty())
                JOptionPane.showMessageDialog(null, "FindBy string is empty", "Error", JOptionPane.WARNING_MESSAGE);
            else {
                Item[] items = service.sendFindItemRequest(findBy);
                foundedText.setText(items.length > 0 ? Arrays.stream(items).map(i -> i.toString() +
                        "\n" + "___________________________________________"
                        + "\n").toList().toString() : "Ничего не найдено");
            }
        }
    }
}
