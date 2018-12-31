import hudson.tasks.junit.CaseResult;
import hudson.tasks.junit.TestResult;
import hudson.tasks.test.AbstractTestResultAction;
import java.io.Serializable;
import java.util.List;
import javax.annotation.CheckForNull;

public class MockTestResultAction extends AbstractTestResultAction<MockTestResultAction> {

    private TestResult testResult;

    public MockTestResultAction(TestResult testResult) {
        this.testResult = testResult;
    }

    @Override public TestResult getResult() {
        return testResult;
    }

    @Override public int getTotalCount() {
        return testResult.getTotalCount();
    }

    @Override public int getFailCount() {
        return testResult.getFailCount();
    }

    @Override public List<CaseResult> getFailedTests() {
        return testResult.getFailedTests();
    }

    @Override public List<CaseResult> getSkippedTests() {
        return testResult.getSkippedTests();
    }

    @Override public List<CaseResult> getPassedTests() {
        return testResult.getPassedTests();
    }
}