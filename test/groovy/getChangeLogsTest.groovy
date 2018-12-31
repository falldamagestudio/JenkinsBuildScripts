import com.lesfurets.jenkins.unit.*
import com.lesfurets.jenkins.unit.cps.BasePipelineTestCPS
import hudson.scm.ChangeLogSet
import org.junit.*
import org.junit.rules.TemporaryFolder

import static groovy.test.GroovyAssert.*
import static com.lesfurets.jenkins.unit.global.lib.LibraryConfiguration.library
import static com.lesfurets.jenkins.unit.global.lib.LocalSource.localSource
import static org.mockito.Mockito.*


class getChangeLogsTest extends BasePipelineTest {

    @ClassRule
    public static TemporaryFolder folder = new TemporaryFolder()

    static File temp

    @BeforeClass
    static void init() {
        temp = folder.newFolder('libs')
    }

    @Before
    void setup() {
        // TODO: Find a better way of getting project root dir
        String dirPath = new File( System.getProperty("user.dir") )
                .getAbsoluteFile()
                .getAbsolutePath()
                
        def library = library()
                .name('JenkinsBuildScripts')
                .defaultVersion('latest')
                .allowOverride(true)
                .implicit(false)
                .targetPath(dirPath)
                .retriever(new FixedFolderLocalSource(dirPath))
                .build()
        helper.registerSharedLibrary(library)

        super.setUp()
        helper.registerAllowedMethod("echo", [String.class], { String s -> println s})
    }

    void registerCurrentBuildWithChangeSets(changeSets) {

        def currentBuild = mock(MockRunWrapper.class)
        when(currentBuild.getResult()).thenReturn('SUCCESS')
        when(currentBuild.getChangeSets()).thenReturn(changeSets)

        binding.setVariable('currentBuild', currentBuild)
    }

    @Test
    void getChangeLogsReturnsAllChangesInASingleList() {

        List<MockChangeLogSetEntry> changeSets1 = new ArrayList<MockChangeLogSetEntry>();
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1234", "change 1"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1235", "change 2"));

        List<MockChangeLogSetEntry> changeSets2 = new ArrayList<MockChangeLogSetEntry>();
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1236", "change 3"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1237", "change 4"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1238", "change 5"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes.add(new MockChangeLogSet(changeSets1));
        changes.add(new MockChangeLogSet(changeSets2));

        registerCurrentBuildWithChangeSets(changes)
        binding.setVariable('changeLogs', null)
        runScript('test/jenkins/getChangeLogs.jenkins')
        def changeLogs = binding.getVariable('changeLogs')
        assertEquals(5, changeLogs.size())
        printCallStack()
        assertTrue(true)
    }

    @Test
    void getChangeLogsRemovesDuplicateCommits() {

        List<MockChangeLogSetEntry> changeSets1 = new ArrayList<MockChangeLogSetEntry>();
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1234", "change 1"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1235", "change 2"));
        changeSets1.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1234", "change 1b"));

        List<MockChangeLogSetEntry> changeSets2 = new ArrayList<MockChangeLogSetEntry>();
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1235", "change 2b"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1237", "change 4"));
        changeSets2.add(new MockChangeLogSetEntry(new ArrayList<String>(), "1238", "change 5"));

        List<ChangeLogSet<MockChangeLogSetEntry>> changes = new ArrayList<ChangeLogSet<MockChangeLogSetEntry>>();
        changes.add(new MockChangeLogSet(changeSets1));
        changes.add(new MockChangeLogSet(changeSets2));

        registerCurrentBuildWithChangeSets(changes)
        binding.setVariable('changeLogs', null)
        runScript('test/jenkins/getChangeLogs.jenkins')

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