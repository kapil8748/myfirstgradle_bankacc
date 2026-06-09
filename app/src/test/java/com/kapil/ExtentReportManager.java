package com.kapil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {

            // Works locally AND in GitHub Actions CI
            String reportPath = System.getProperty("user.dir") + "/reports/ExtentReport.html";

            // Create reports/ folder if it doesn't exist
            new File(System.getProperty("user.dir") + "/reports").mkdirs();

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setDocumentTitle("BankAccount Test Report");
            spark.config().setReportName("JUnit 5 — BankAccount Test Suite");
            spark.config().setTheme(Theme.DARK);
            spark.config().setTimeStampFormat("dd MMM yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Project",   "BankAccount JUnit5 + Gradle");
            extent.setSystemInfo("Tester",    "Kapil");
            extent.setSystemInfo("Java",      System.getProperty("java.version"));
            extent.setSystemInfo("Framework", "JUnit 5.10.2");
        }
        return extent;
    }
}