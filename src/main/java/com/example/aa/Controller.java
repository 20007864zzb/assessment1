package com.example.aa;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;

public class Controller {

    @FXML
    TextField filenameid;
    @FXML
    TextField searchwhat;
    @FXML
    TextArea text;


    public void clik(ActionEvent actionEvent) throws Exception {
        Main m = new Main();
        Stage primaryStage = new Stage();
        m.start(primaryStage);
    }

    public void readfilename(ActionEvent actionEvent) throws IOException {
        JFileChooser fd = new JFileChooser();
        fd.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
        fd.showOpenDialog(null);
        File f = fd.getSelectedFile();
        filenameid.setText(f.getName());
        if (f != null) {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String str;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
                text.appendText(str);
                text.appendText("\n");
            }

        }

    }

    public void adddt(ActionEvent actionEvent) {
        String a = new String();
        a = Main.NowString.get();
        text.insertText(0, a);
    }


    public void savefuction(ActionEvent actionEvent) {
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG | JFileChooser.DIRECTORIES_ONLY);
        jf.showDialog(null, null);
        File fi = jf.getSelectedFile();
        String f = fi.getAbsolutePath() + "\\" + filenameid.getText();
        System.out.println("save: " + f);
        try {
            FileWriter out = new FileWriter(f);
            String A;
            A = text.getText();
            out.write(A);
            out.close();
        } catch (Exception e) {
        }
    }

    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void copy(ActionEvent actionEvent) {
        String a;
        a = text.getSelectedText();
        setSysClipboardText(a);
    }

    public void pasteud(ActionEvent actionEvent) {
        int a;
        a = text.getCaretPosition();
        text.insertText(a, getSysClipboardText());
    }

    public void cut(ActionEvent actionEvent) {
        int a;
        int b;
        String c;
        c = text.getSelectedText();
        a = text.getSelection().getEnd();
        b = text.getSelection().getStart();
        setSysClipboardText(c);
        text.replaceText(b, a, "");
    }


    public void setSysClipboardText(String writeMe) {
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable tText = new StringSelection(writeMe);
        clip.setContents(tText, null);


    }

    public static String getSysClipboardText() {
        String ret = "";
        Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        // ???????????????????????????
        Transferable clipTf = sysClip.getContents(null);

        if (clipTf != null) {
            // ?????????????????????????????????
            if (clipTf.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                try {
                    ret = (String) clipTf
                            .getTransferData(DataFlavor.stringFlavor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ret;
    }

    public void search(ActionEvent actionEvent) {
        String a;
        String b;
        String d = new String();
        int c;
        a = searchwhat.getText();
        b = text.getText();
        c = b.indexOf(a);
        for (int i = 0; i < a.length(); i++) {
            d = d + "???";
        }
        while (c != -1) {
            text.replaceText(c, c + a.length(), d);
            b = text.getText();
            c = b.indexOf(a);
        }
        System.out.print(c);


    }

    public void recoversearch(ActionEvent actionEvent) {
        String a;
        String b;
        String d = new String();
        String e;
        int c;
        a = searchwhat.getText();
        b = text.getText();
        e = text.getText();
        for (int i = 0; i < a.length(); i++) {
            d = d + "???";
        }
        c = b.indexOf(d);
        while (c != -1) {
            text.replaceText(c, c + a.length(), a);
            b = text.getText();
            c = b.indexOf(d);
        }
        System.out.print(c);
    }

    public void print(ActionEvent actionEvent) {
        JFileChooser fd = new JFileChooser();
        fd.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
        fd.showOpenDialog(null);
        File f = fd.getSelectedFile();
        System.out.print(f.getAbsolutePath());
        if (f != null) {
            File file = new File(f.getAbsolutePath()); // ?????????????????????

            // ???????????????????????????

            HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

            // ?????????????????????????????????????????????????????????autosense

            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

            // ????????????????????????????????????

            PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
            System.out.println("-------------------All services-------------------");
            for (int i = 0; i < printService.length; i++) {
                System.out.println(printService[i]);
            }

            // ???????????????????????????

            //PrintService service1 = PrintServiceLookup.lookupDefaultPrintService();
            if (printService.length > 0) {

                System.out.println("-------------------Choose Printer-------------------");
                System.out.println(printService[0]);
                //???????????? Microsoft XPS Document Writer
                PrintService service = printService[0];

                //?????????????????????
                AttributeSet attributes = service.getAttributes();
                for (Attribute a : attributes.toArray()) {
                    String name = a.getName();
                    String value = attributes.get(a.getClass()).toString();
                    System.out.println(name + " : " + value);
                }
                // ?????????????????????

                //PrintService service = ServiceUI.printDialog(null, 200, 200, printService,

                //    service1, flavor, pras);

                if (service != null) {

                    try {
                        System.out.println("Begin Print PDF: " + file.getName());
                        DocPrintJob job = service.createPrintJob(); // ??????????????????

                        FileInputStream fis = new FileInputStream(file); // ???????????????????????????

                        DocAttributeSet das = new HashDocAttributeSet();
                        //???????????????????????????
                        pras.add(Chromaticity.COLOR);

                        //??????????????????
                        pras.add(OrientationRequested.LANDSCAPE);

                        //??????Letter?????????????????????
                        pras.add(MediaSizeName.NA_LETTER);

                        // European A4 paper
                        pras.add(MediaSizeName.ISO_A4);

                        //????????????
                        pras.add(Finishings.STAPLE);

                        //??????????????????
                        pras.add(SheetCollate.COLLATED);

                        //????????????
                        pras.add(Sides.DUPLEX);

                        // 2?????????????????????
                        pras.add(new NumberUp(2));

                        //???????????????
                        pras.add(new Copies(2));

                        Doc doc = new SimpleDoc(fis, flavor, das);

                        job.print(doc, pras);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void pdfopen(ActionEvent actionEvent) {
        JFileChooser fd = new JFileChooser();
        fd.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
        fd.showOpenDialog(null);
        File f = fd.getSelectedFile();
        System.out.println(f);
        PdfReader reader;
        try {

            reader = new PdfReader(f.getAbsolutePath());
            // pageNumber = 1
            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1);
            text.setText(textFromPage);
            filenameid.setText(f.getName());
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pdfsave(ActionEvent actionEvent) {}





}

