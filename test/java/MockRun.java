import hudson.model.Run;
import java.io.IOException;
import java.io.File;

public class MockRun extends hudson.model.Run<MockJob, MockRun>
{
    public MockRun() throws IOException {
        super(null);
    }

    public MockRun(MockJob job) throws IOException {
        super(job);
    }

    public MockRun(MockJob job, File dir) throws IOException {
        super(job, dir);
    }

    public String getExternalizableId() {
        return "externalizable_id_12345678";
    }
}
