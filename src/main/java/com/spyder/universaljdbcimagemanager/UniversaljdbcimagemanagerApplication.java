package com.spyder.universaljdbcimagemanager;

import javax.swing.SwingUtilities;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class UniversaljdbcimagemanagerApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(
                UniversaljdbcimagemanagerApplication.class
        )
        .headless(false)
        .run(args);

        SwingUtilities.invokeLater(
                MainFrame::new
        );
    }
}