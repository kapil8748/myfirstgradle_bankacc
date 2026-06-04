package com.kapil;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.junit.jupiter.api.extension.*;

public class ExtentReportListener
        implements BeforeAllCallback, AfterAllCallback,
                   BeforeEachCallback, AfterEachCallback {

    private static final ExtentReports extent = ExtentReportManager.getInstance();

    // Thread-safe: each test gets its own ExtentTest node
    static final ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();

    @Override
    public void beforeAll(ExtensionContext ctx) {
        // extent already initialised via singleton
    }

    @Override
    public void beforeEach(ExtensionContext ctx) {
        String name = ctx.getDisplayName();
        ExtentTest test = extent.createTest(name);
        testNode.set(test);
        test.log(Status.INFO, "Test started: " + name);
    }

    @Override
    public void afterEach(ExtensionContext ctx) {
        ExtentTest test = testNode.get();
        if (ctx.getExecutionException().isPresent()) {
            Throwable ex = ctx.getExecutionException().get();
            test.fail("Test FAILED ❌ — " + ex.getMessage());
            test.fail(ex);                        // attaches full stack trace
        } else {
            test.pass("Test PASSED ✅");
        }
    }

    @Override
    public void afterAll(ExtensionContext ctx) {
        extent.flush();                           // write HTML to disk
    }
}