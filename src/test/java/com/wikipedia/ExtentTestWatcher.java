package com.wikipedia;

import com.aventstack.extentreports.Status;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

public class ExtentTestWatcher implements TestWatcher {

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (BaseTest.extentTest.get() != null)
            BaseTest.extentTest.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (BaseTest.extentTest.get() != null)
            BaseTest.extentTest.get().log(Status.FAIL, cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (BaseTest.extentTest.get() != null)
            BaseTest.extentTest.get().log(Status.SKIP, "Test aborted: " + cause.getMessage());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        if (BaseTest.extentTest.get() != null)
            BaseTest.extentTest.get().log(Status.SKIP,
                    "Test disabled: " + reason.orElse("no reason given"));
    }
}