import hudson.scm.ChangeLogSet
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*

class getChangeLogsTest extends LocalSharedLibraryPipelineTest {

    void registerCurrentBuildWithChangeSets(changeSets) {

        def currentBuild = mock(MockRunWrapper.class)
        when(currentBuild.getResult()).thenReturn('SUCCESS')
        when(currentBuild.getChangeSets()).thenReturn(changeSets)

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void getChangeLogsReturnsAllChangesInASingleList() {

        List<MockChangeLogSetEntry> changeSets1 = new ArrayList<MockChangeLogSetEntry>();
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1234", "change 1"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user2@example.com", "1235", "change 2"));

        List<MockChangeLogSetEntry> changeSets2 = new ArrayList<MockChangeLogSetEntry>();
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user3@example.com", "1236", "change 3"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1237", "change 4"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user4@example.com", "1238", "change 5"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes.add(new MockChangeLogSet(changeSets1));
        changes.add(new MockChangeLogSet(changeSets2));

        registerCurrentBuildWithChangeSets(changes)
        binding.setVariable('changeLogs', null)
        runScript('test/jenkins/SCMInfo/getChangeLogs.jenkins')
        def changeLogs = binding.getVariable('changeLogs')
        assertEquals(5, changeLogs.size())
        printCallStack()
        assertTrue(true)
    }

    @Test
    void getChangeLogsRemovesDuplicateCommits() {

        List<MockChangeLogSetEntry> changeSets1 = new ArrayList<MockChangeLogSetEntry>();
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1234", "change 1"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user2@example.com", "1235", "change 2"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1234", "change 1b"));

        List<MockChangeLogSetEntry> changeSets2 = new ArrayList<MockChangeLogSetEntry>();
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user2@example.com", "1235", "change 2b"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user1@example.com", "1237", "change 4"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user4@example.com", "1238", "change 5"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes.add(new MockChangeLogSet(changeSets1));
        changes.add(new MockChangeLogSet(changeSets2));

        registerCurrentBuildWithChangeSets(changes)
        binding.setVariable('changeLogs', null)
        runScript('test/jenkins/SCMInfo/getChangeLogs.jenkins')

        def changeLogs = binding.getVariable('changeLogs')
        assertEquals(4, changeLogs.size())
        assertEquals("1234", changeLogs[0].commitId)
        assertEquals("1235", changeLogs[1].commitId)
        assertEquals("1237", changeLogs[2].commitId)
        assertEquals("1238", changeLogs[3].commitId)

        printCallStack()
        assertTrue(true)
    }
}