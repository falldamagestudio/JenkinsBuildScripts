import hudson.model.ItemGroup;
import hudson.model.Job;
import hudson.model.RunMap;

public abstract class MockJob extends Job<MockJob, MockRun>
{
    public MockJob(ItemGroup parent, String name) {
        super(parent, name);
    }

    @Override public boolean isBuildable() {
        throw new java.lang.UnsupportedOperationException();
        //return false;
    }
    
    @Override protected RunMap<MockRun> _getRuns() {
        throw new java.lang.UnsupportedOperationException();
        //return null;
    }
    
    @Override protected void removeRun(MockRun run) {
    }
}
