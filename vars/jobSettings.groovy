import com.falldamagestudio.JsonHelpers

Object call() {
    String workspacePath = this.env.WORKSPACE;
    String jobName = this.env.JOB_NAME;
    String settingsPath = "$workspacePath/JenkinsBuildScripts/${jobName}.json";
    if (fileExists(settingsPath)) {
        echo("Found settings file $settingsPath");
        return JsonHelpers.readJsonSerializable(this, settingsPath);
    }

    return [:];
}
