package util;

import java.io.File;
import java.io.IOException;

public class FileMerger {
    private static boolean hasDone;
    public static File mergeFiles(File sources, File output) {
        String command = "ffmpeg -f concat -safe 0 -i " + "\"" + sources.getPath() +  "\"" + " -c copy " + "\"" + output.getPath() + "\"";

        System.out.println("______________________________________________________");
        System.out.println("Starting to merge all files!");
        System.out.println("______________________________________________________");

        setHasDone(false);

        ProcessBuilder pb = new ProcessBuilder();
        pb.command(command.split(" "));

        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process;
        try {
            process = pb.start();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        while (process.isAlive()){
            setHasDone(false);
        }

        setHasDone(true);
        return output;
    }


    public static boolean hasDone() {
        return hasDone;
    }

    private static void setHasDone(boolean hasDone) {
        FileMerger.hasDone = hasDone;
    }
}
