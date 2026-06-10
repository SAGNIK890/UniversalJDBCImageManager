package com.spyder.universaljdbcimagemanager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

    JTextField tfDriver;
    JTextField tfUrl;
    JTextField tfUser;
    JPasswordField tfPassword;

    JTextField tfTable;
    JTextField tfImageColumn;
    JTextField tfPkColumn;

    JButton connectBtn;
    JButton uploadBtn;
    JButton refreshBtn;
    JButton deleteBtn;
    JButton downloadBtn;
    JButton searchBtn;

    JComboBox<String> imageList;

    JLabel imageLabel;

    DatabaseManager db;

    public MainFrame() {

        db = new DatabaseManager();

        setTitle("Universal Database Image Viewer");

        setSize(1000, 700);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLayout(new BorderLayout());

        JPanel top =
                new JPanel(
                        new GridLayout(8, 2, 5, 5)
                );

        tfDriver =
                new JTextField(
                        "com.mysql.cj.jdbc.Driver"
                );

        tfUrl =
                new JTextField(
                        "jdbc:mysql://localhost:3306/Sagnik"
                );

        tfUser =
                new JTextField();

        tfPassword =
                new JPasswordField();

        tfTable =
                new JTextField();

        tfImageColumn =
                new JTextField();

        tfPkColumn =
                new JTextField();

        top.add(new JLabel("Driver"));
        top.add(tfDriver);

        top.add(new JLabel("URL"));
        top.add(tfUrl);

        top.add(new JLabel("Username"));
        top.add(tfUser);

        top.add(new JLabel("Password"));
        top.add(tfPassword);

        top.add(new JLabel("Table Name"));
        top.add(tfTable);

        top.add(new JLabel("Image Column"));
        top.add(tfImageColumn);

        top.add(new JLabel("Primary Key Column"));
        top.add(tfPkColumn);

        connectBtn =
                new JButton("Connect");

        top.add(connectBtn);

        add(top, BorderLayout.NORTH);

        imageLabel =
                new JLabel();

        imageLabel.setHorizontalAlignment(
                JLabel.CENTER
        );

        add(
                new JScrollPane(imageLabel),
                BorderLayout.CENTER
        );

        JPanel bottom =
                new JPanel();

        imageList =
                new JComboBox<>();

        uploadBtn =
                new JButton("Upload");

        refreshBtn =
                new JButton("Refresh");

        downloadBtn =
                new JButton("Download");

        deleteBtn =
                new JButton("Delete");

        searchBtn =
                new JButton("Search");

        bottom.add(imageList);
        bottom.add(uploadBtn);
        bottom.add(refreshBtn);
        bottom.add(downloadBtn);
        bottom.add(deleteBtn);
        bottom.add(searchBtn);

        add(bottom, BorderLayout.SOUTH);

        connectBtn.addActionListener(
                e -> connect()
        );

        refreshBtn.addActionListener(
                e -> loadImages()
        );

        uploadBtn.addActionListener(
                e -> upload()
        );

        deleteBtn.addActionListener(
                e -> deleteImage()
        );

        downloadBtn.addActionListener(
                e -> downloadImage()
        );

        searchBtn.addActionListener(
                e -> searchImage()
        );

        imageList.addActionListener(
                e -> displaySelectedImage()
        );

        setVisible(true);
    }

    private void connect() {

        try {

            db.connect(
                    tfDriver.getText(),
                    tfUrl.getText(),
                    tfUser.getText(),
                    String.valueOf(
                            tfPassword.getPassword()
                    ),
                    tfTable.getText(),
                    tfImageColumn.getText(),
                    tfPkColumn.getText()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Connected Successfully"
            );

            loadImages();

        } catch (Exception ex) {

            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage()
            );
        }
    }

    private void loadImages() {

        try {

            imageList.removeAllItems();

            ResultSet rs =
                    db.getImages();

            while (rs.next()) {

                imageList.addItem(
                        rs.getString(1)
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    private String getSelectedId() {

        return (String)
                imageList.getSelectedItem();
    }

    private void displaySelectedImage() {

        try {

            String id =
                    getSelectedId();

            if (id == null)
                return;

            ResultSet rs =
                    db.getImageById(id);

            if (rs.next()) {

                byte[] imageBytes =
                        rs.getBytes(1);

                ImageIcon icon =
                        new ImageIcon(imageBytes);

                Image image =
                        icon.getImage()
                                .getScaledInstance(
                                        700,
                                        500,
                                        Image.SCALE_SMOOTH
                                );

                imageLabel.setIcon(
                        new ImageIcon(image)
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    private void upload() {

        try {

            JFileChooser fc =
                    new JFileChooser();

            if (fc.showOpenDialog(this)
                    ==
                    JFileChooser.APPROVE_OPTION) {

                File file =
                        fc.getSelectedFile();

                db.uploadImage(file);

                loadImages();

                JOptionPane.showMessageDialog(
                        this,
                        "Image Uploaded"
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    private void deleteImage() {

        try {

            String id =
                    getSelectedId();

            if (id == null)
                return;

            db.deleteImage(id);

            imageLabel.setIcon(null);

            loadImages();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    private void searchImage() {

        try {

            String id =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Primary Key Value"
                    );

            if (id == null)
                return;

            ResultSet rs =
                    db.getImageById(id);

            if (rs.next()) {

                byte[] imageBytes =
                        rs.getBytes(1);

                ImageIcon icon =
                        new ImageIcon(imageBytes);

                imageLabel.setIcon(icon);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Record Not Found"
                );
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }

    private void downloadImage() {

        try {

            String id =
                    getSelectedId();

            if (id == null)
                return;

            ResultSet rs =
                    db.getImageById(id);

            if (rs.next()) {

                byte[] imageBytes =
                        rs.getBytes(1);

                JFileChooser fc =
                        new JFileChooser();

                if (fc.showSaveDialog(this)
                        ==
                        JFileChooser.APPROVE_OPTION) {

                    FileOutputStream fos =
                            new FileOutputStream(
                                    fc.getSelectedFile()
                            );

                    fos.write(imageBytes);

                    fos.close();

                    JOptionPane.showMessageDialog(
                            this,
                            "Downloaded Successfully"
                    );
                }
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}