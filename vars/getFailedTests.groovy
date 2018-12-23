import com.falldamagestudio.TestResults

def call() {
    echo "getting failed tests"
    def testResults = new TestResults().run()
    echo "test results: ${testResults}"
}