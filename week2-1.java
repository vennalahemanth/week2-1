import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

class GoalReminder {
    private static class Goal {
        String task;
        LocalTime reminderTime;

        Goal(String task, LocalTime reminderTime) {
            this.task = task;
            this.reminderTime = reminderTime;
        }
    }

    private static List<Goal> goals = new ArrayList<>();
    private static Timer timer = new Timer();
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Daily Goal Reminder!");
        while (true) {
            System.out.print("Enter your goal (or 'exit' to stop): ");
            String task = scanner.nextLine();
            if (task.equalsIgnoreCase("exit")) break;

            System.out.print("Enter reminder time (HH:MM format): ");
            String timeInput = scanner.nextLine();

            try {
                LocalTime reminderTime = LocalTime.parse(timeInput, timeFormatter);
                goals.add(new Goal(task, reminderTime));
                scheduleReminder(task, reminderTime);
                System.out.println("Goal added successfully!");
            } catch (Exception e) {
                System.out.println("Invalid time format. Please enter in HH:MM format.");
            }
        }

        scanner.close();
        System.out.println("You have exited the application. Stay productive!");
    }

    private static void scheduleReminder(String task, LocalTime reminderTime) {
        long delay = calculateDelay(reminderTime);
        if (delay > 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Reminder: " + task + " at " + reminderTime.format(timeFormatter));
                }
            }, delay);
        } else {
            System.out.println("Reminder time has already passed for today.");
        }
    }

    private static long calculateDelay(LocalTime reminderTime) {
        LocalTime now = LocalTime.now();
        return java.time.Duration.between(now, reminderTime).toMillis();
    }
}