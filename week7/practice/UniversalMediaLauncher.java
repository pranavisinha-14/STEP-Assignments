package week7.practice;

public class UniversalMediaLauncher {

    // Interface
    interface Playable {

        String play();

        String play(int fromSecond);

        String pause();
    }

    // Abstract media class
    static abstract class MediaFile {

        private static int fileCounter = 1000;

        private final String fileId;

        public MediaFile() {

            fileCounter++;

            fileId =
                    "MF-" + fileCounter;
        }

        public abstract String getFormatInfo();

        public String getFileId() {

            return fileId;
        }
    }

    /*
     * AudioFile IS-A MediaFile
     * and CAN-DO Playable.
     */
    static class AudioFile
            extends MediaFile
            implements Playable {

        private String title;

        public AudioFile(
                String title) {

            if (title == null
                    || title.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Title cannot be blank");
            }

            this.title =
                    title.trim();
        }

        @Override
        public String getFormatInfo() {

            return "Audio file, ID: "
                    + getFileId();
        }

        @Override
        public String play() {

            return "Playing audio: "
                    + title;
        }

        /*
         * Overloaded play().
         */
        @Override
        public String play(
                int fromSecond) {

            int minutes =
                    fromSecond / 60;

            int seconds =
                    fromSecond % 60;

            return String.format(
                    "Playing audio: %s from %d:%02d",
                    title,
                    minutes,
                    seconds);
        }

        @Override
        public String pause() {

            return "Paused audio: "
                    + title;
        }
    }

    /*
     * Podcast has NO relationship with
     * MediaFile.
     *
     * It only implements Playable.
     */
    static class Podcast
            implements Playable {

        private String showName;
        private int episodeNumber;

        public Podcast(
                String showName,
                int episodeNumber) {

            if (showName == null
                    || showName.trim().isEmpty()) {

                throw new IllegalArgumentException(
                        "Show name cannot be blank");
            }

            if (episodeNumber <= 0) {

                throw new IllegalArgumentException(
                        "Episode number must be positive");
            }

            this.showName =
                    showName.trim();

            this.episodeNumber =
                    episodeNumber;
        }

        @Override
        public String play() {

            return "Streaming episode "
                    + episodeNumber
                    + " of "
                    + showName;
        }

        @Override
        public String play(
                int fromSecond) {

            return "Streaming episode "
                    + episodeNumber
                    + " of "
                    + showName
                    + " from "
                    + fromSecond
                    + " seconds";
        }

        @Override
        public String pause() {

            return "Paused podcast: "
                    + showName
                    + " episode "
                    + episodeNumber;
        }
    }

    /*
     * Polymorphic method.
     *
     * Works with AudioFile and Podcast
     * without checking their actual types.
     */
    static void launchAll(
            Playable[] items) {

        for (Playable item : items) {

            System.out.println(
                    item.play());
        }
    }

    public static void main(String[] args) {

        AudioFile a =
                new AudioFile(
                        "Morning Jazz");

        System.out.println(
                a.play());

        System.out.println(
                a.play(30));

        System.out.println(
                a.getFormatInfo());

        System.out.println();

        Podcast p =
                new Podcast(
                        "Tech Talk",
                        12);

        System.out.println(
                p.play());

        System.out.println();

        /*
         * Upcasting:
         * AudioFile → Playable
         */
        Playable ref = a;

        System.out.println(
                ref.play());

        System.out.println();

        /*
         * Mixed array.
         */
        launchAll(
                new Playable[] {
                    ref,
                    p
                });
    }
}