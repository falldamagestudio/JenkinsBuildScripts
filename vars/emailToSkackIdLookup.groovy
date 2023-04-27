import com.falldamagestudio.JsonHelpers

Map call() {
    String workspacePath = this.env.WORKSPACE;
    // we need to get object serializable in order to be able to feed it into Java library
    return JsonHelpers.readJsonSerializable(this, "$workspacePath/JenkinsBuildScripts/emailToSlack.json");
}
