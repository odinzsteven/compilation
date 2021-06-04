package compp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main extends JFrame {
    private static final long serialVersionUID = 3577546904498233863L;
    private JFrame fenetre;
    private String exampleProgram = "$ Hello world x 10 $\n" +
            "VAR\n" +
            "    STRING LINE := \"Hello world!\";\n" +
            "    INT I := 0;\n\n" +
            "BEGIN\n" +
            "    WHILE (I < 10)  {\n" +
            "        PRINTLN(LINE);\n" +
            "        I := I + 1;\n" +
            "    }\n" +
            "END";

    private Main() {
        // fenatre
        fenetre = new JFrame("file");
        fenetre.getContentPane().setBackground(SystemColor.desktop);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.setLocationRelativeTo(null);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        /* TextTool area */
        final JTextArea Area1 = new JTextArea();
        // JScrollPane scl= new JScrollPane();

        Area1.setColumns(40);
        Area1.setRows(15);
        Area1.setBackground(new Color(230, 230, 250));
        Area1.setText(exampleProgram);
        textPanel.add(new JScrollPane(Area1));
        // scl.setViewportView(Area1);

        textPanel.add(Box.createHorizontalStrut(10));

        final JTextArea Area2 = new JTextArea();
        Area2.setColumns(40);
        Area2.setRows(15);
        Area2.setBackground(new Color(230, 230, 250));
        // scl.setViewportView(Area2);
        textPanel.add(new JScrollPane(Area2));

        fenetre.add(textPanel);

        TextTool tool = new TextTool();

        /* MENU BAR */
        MenuBar mbar = new MenuBar();
        Menu pr = new Menu("File");
        // mbar.setFont(new Font("Tempus", Font.BOLD, 20));
        fenetre.setMenuBar(mbar);
        mbar.add(pr);
        /* MENU ITEM OPEN */
        MenuItem item1 = new MenuItem("open");
        item1.addActionListener((ActionEvent e) -> {
            JFileChooser Fch = new JFileChooser(".txt");
            Fch.showOpenDialog(Fch);
            File file = Fch.getSelectedFile();
            String file_name = file.toString();
            try {
                FileReader Fre = new FileReader(file_name);
                BufferedReader BR = new BufferedReader(Fre);
                Area1.read(BR, null);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /* MENU ITEM SAVE */
        MenuItem item2 = new MenuItem("save as ");
        item2.addActionListener((ActionEvent e) -> {
            JFileChooser Sfile = new JFileChooser(".txt");
            Sfile.showSaveDialog(Sfile);
            File file = Sfile.getSelectedFile();
            try {
                FileWriter Wfile = new FileWriter(file);
                try (BufferedWriter Wbuffer = new BufferedWriter(Wfile)) {
                    Wbuffer.write(Area1.getText());
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        MenuItem item3 = new MenuItem("close");
        item3.addActionListener((ActionEvent e) -> System.exit(0));
        pr.add(item1);
        pr.add(item2);
        pr.add(item3);

        Menu help = new Menu("Help");

        MenuItem about = new MenuItem("About");
        about.addActionListener((ActionEvent e) -> System.out.print("about clicked!"));

        help.add(about);

        mbar.add(help);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        /* button ok */
        JButton ok1 = new JButton("analyser");
        ok1.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        buttonsPanel.add(ok1);
        ok1.addActionListener((ActionEvent e) -> {
            String text = Area1.getText();
            tool.reset(text);
            tool.reset(text);
            tool.saveLiteralStrings();
            tool.removeComments();
            tool.removeSpaces();
            tool.makeDyZe();

            Area2.setText(tool.getText());
        });

        /* button analyser */
        JButton analyser = new JButton("Tableau");
        analyser.setFont(new Font("Tempus Sans ITC", Font.BOLD, 20));
        buttonsPanel.add(analyser);
        analyser.addActionListener((ActionEvent e) -> {
            JFrame fenetre2 = new JFrame("Tableau");
            fenetre2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            String text = tool.getRawText();
            Analyzer analyzer = new Analyzer(tool.getSavedStrings());
            JTable table = new JTable(new LexicalAnalyseTableModel(analyzer.analyse(text)));
            fenetre2.add(new JScrollPane(table));

            fenetre2.pack();
            fenetre2.setSize(400, 600);
            fenetre2.setLocationRelativeTo(null);
            fenetre2.setVisible(true);
        });
        fenetre.add(BorderLayout.SOUTH, buttonsPanel);

    }

    // ******************************************************************************************************************************************

    // ********************************************************************************************************************************************
    private void start() {
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.pack();
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main p = new Main();
            p.start();
        });
    }

}
