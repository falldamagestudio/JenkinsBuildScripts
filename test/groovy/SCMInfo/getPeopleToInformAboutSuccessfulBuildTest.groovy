import hudson.scm.ChangeLogSet
import org.junit.Test

import static groovy.test.GroovyAssert.*
import static org.mockito.Mockito.*

class getPeopleToInformAboutSuccessfulBuildTest extends LocalSharedLibraryPipelineTest {

    void registerSeveralBuildsWithChangeSets(currentSuccess, previous1Success, previous2Success) {

        def currentBuild = mock(MockRunWrapper.class)
        def previousBuild1 = mock(MockRunWrapper.class)
        def previousBuild2 = mock(MockRunWrapper.class)
        when(currentBuild.getPreviousBuild()).thenReturn(previousBuild1)
        when(previousBuild1.getPreviousBuild()).thenReturn(previousBuild2)
        when(previousBuild2.getPreviousBuild()).thenReturn(null)

        when(currentBuild.getResult()).thenReturn(currentSuccess)
        when(previousBuild1.getResult()).thenReturn(previous1Success)
        when(previousBuild2.getResult()).thenReturn(previous2Success)

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

        when(currentBuild.getChangeSets()).thenReturn(changes)

        List<MockChangeLogSetEntry> changeSets3 = new ArrayList<MockChangeLogSetEntry>();
        changeSets3.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user5@example.com", "1239", "change 6"));
        changeSets3.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user2@example.com", "1240", "change 7"));
        changeSets3.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user7@example.com", "1241", "change 8"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes2 = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes2.add(new MockChangeLogSet(changeSets3));

        when(previousBuild1.getChangeSets()).thenReturn(changes2)

        List<MockChangeLogSetEntry> changeSets4 = new ArrayList<MockChangeLogSetEntry>();
        changeSets4.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user6@example.com", "1242", "change 9"));
        changeSets4.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user9@example.com", "1243", "change 10"));
        changeSets4.add(new MockChangeLogSetEntry(new ArrayList<String>(), "user7@example.com", "1244", "change 11"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes3 = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes3.add(new MockChangeLogSet(changeSets4));

        when(previousBuild2.getChangeSets()).thenReturn(changes3)

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void returnsAllCommittersFromPreviousFailedBuildRun() {

        registerSeveralBuildsWithChangeSets('SUCCESS', 'FAILED', 'SUCCESS')

        binding.setVariable('committers', null)
        runScript('test/jenkins/SCMInfo/getPeopleToInformAboutSuccessfulBuild.jenkins')
        def committers = binding.getVariable('committers')

        // Ensure all users in previous1 builds are listed
        assertEquals(3, committers.size())
        assertTrue(committers.contains('user2@example.com'))
        assertTrue(committers.contains('user5@example.com'))
        assertTrue(committers.contains('user7@example.com'))

        printCallStack()
        assertTrue(true)
    }
}