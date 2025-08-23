package data;

    public class DatabaseManager {
        public static void backupDatabase() {
            try {
                Runtime.getRuntime().exec("java -cp h2-2.2.224.jar org.h2.tools.Script -url jdbc:h2:file:./database/projects -user sa -script backup.zip -options compression zip");
                System.out.println("Backup created successfully");
            } catch (Exception e) {
                System.err.println("Backup failed: " + e.getMessage());
            }
        }

        public static void openDatabaseConsole() {
            try {
                Runtime.getRuntime().exec("java -cp h2-2.2.224.jar org.h2.tools.Console -url jdbc:h2:file:./database/projects -user sa");
            } catch (Exception e) {
                System.err.println("Failed to open console: " + e.getMessage());
            }
        }

        public static void performAutoBackup() {
            // Автоматическое создание бэкапа при запуске
            System.out.println("Performing automatic database backup...");
            backupDatabase();
        }
    }

