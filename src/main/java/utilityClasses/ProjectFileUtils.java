package utilityClasses;

import java.io.File;

public class ProjectFileUtils {

    /**
     * Create a directory if it does not already exist
     *
     * @param fullDirectoryPath is the full directory path to check, and create if it does not already exist
     */
    public static void createDirectoryIfNotExist(String fullDirectoryPath) {
        File directory = new File(fullDirectoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Get this java project's Maven directory
     *
     * @return the project's directory
     */
    public static String getProjectMavenDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Get this java project's Maven 'target' directory
     * <p>
     * This directory is used for the project's class, jar and other artifacts. It is also
     * used to save output files during execution
     *
     * @return the project's target directory
     */
    public static String getProjectMavenTargetDir() {
        return getProjectMavenDir() + "/target";
    }

    /**
     * Get the directory to save screenshots to
     * This was developed, for saving screenshots when a Selenium test failed
     *
     * @return the directory to save the screenshot to
     */
    public static String getProjectScreenshotDir() {
        return getProjectMavenTargetDir() + "/site/screenshots";
    }

    /**
     *
     * @return
     */
    public static String getProjectReportDir() {
        return getProjectMavenTargetDir() + "/reports";
    }

}
