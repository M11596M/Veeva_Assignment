//package com.veeva.Listeners;
//
//
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import org.testng.ITestContext;
//import org.testng.ITestListener;
//import org.testng.ITestResult;
//
//public class ExtentReportListener implements ITestListener {
//
//    private ExtentReports extent;
//    private ExtentTest test;
//
//    @Override
//    public void onStart(ITestContext context) {
//        extent = new ExtentReports("path/to/extent-report.html", true);
//    }
//
//    @Override
//    public void onTestStart(ITestResult result) {
//        test = extent.startTest(result.getName());
//    }
//
//    @Override
//    public void onTestSuccess(ITestResult result) {
//        test.log(LogStatus.PASS, "Test Passed");
//    }
//
//    @Override
//    public void onTestFailure(ITestResult result) {
//        test.log(LogStatus.FAIL, "Test Failed");
//    }
//
//    @Override
//    public void onTestSkipped(ITestResult result) {
//        test.log(LogStatus.SKIP, "Test Skipped");
//    }
//
//    @Override
//    public void onFinish(ITestContext context) {
//        extent.endTest(test);
//        extent.flush();
//    }
//}
