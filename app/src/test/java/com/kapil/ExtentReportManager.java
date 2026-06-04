package com.kapil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
            spark.config().setDocumentTitle("BankAccount Test Report");
            spark.config().setReportName("JUnit 5 — BankAccount Test Suite");
            spark.config().setTheme(Theme.DARK);
            spark.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project",    "BankAccount JUnit5 + Gradle");
            extent.setSystemInfo("Tester",     "Kapil");
            extent.setSystemInfo("Java",       System.getProperty("java.version"));
            extent.setSystemInfo("Framework",  "JUnit 5.10.2");
        }
        return extent;
    }
}