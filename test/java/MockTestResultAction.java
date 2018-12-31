import hudson.tasks.junit.TestResult;
import hudson.tasks.test.AbstractTestResultAction;
import java.io.Serializable;
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
}