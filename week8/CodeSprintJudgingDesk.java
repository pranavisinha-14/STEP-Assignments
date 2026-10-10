package week8;

import java.util.*;

public class CodeSprintJudgingDesk {
    interface ScoringRule {
        double calculate(Score s);
    }

    static class InnovationRule implements ScoringRule {
        public double calculate(Score s) {
            return s.idea * 0.5 + s.execution * 0.3
                    + s.presentation * 0.2;
        }
    }

    static class OpenRule implements ScoringRule {
        public double calculate(Score s) {
            return (s.idea + s.execution + s.presentation) / 3.0;
        }
    }

    static class Student {
        String name;

        Student(String name) {
            this.name = name;
        }
    }

    static class Score {
        int idea, execution, presentation;

        Score(int idea, int execution, int presentation) {
            if (idea < 0 || idea > 10 || execution < 0 || execution > 10
                    || presentation < 0 || presentation > 10) {
                throw new IllegalArgumentException(
                        "Ratings must be between 0 and 10.");
            }

            this.idea = idea;
            this.execution = execution;
            this.presentation = presentation;
        }
    }

    static class Project {
        String name;
        Score score;

        Project(String name) {
            this.name = name;
        }
    }

    static class Team {
        String name, track;
        List<Student> members;
        Project project;

        Team(String name, String track, List<Student> members) {
            this.name = name;
            this.track = track;
            this.members = new ArrayList<>(members);
        }
    }

    static class Hackathon {
        enum State {
            OPEN, JUDGING, PUBLISHED
        }

        State state = State.OPEN;
        List<Team> teams = new ArrayList<>();
        Set<String> registeredStudents = new HashSet<>();
        Map<String, ScoringRule> rules = new HashMap<>();

        Hackathon() {
            rules.put("Innovation", new InnovationRule());
            rules.put("Open", new OpenRule());
        }

        boolean registerTeam(Team team) {
            if (state != State.OPEN) {
                System.out.println(
                        "Registration failed: registration is closed.");
                return false;
            }

            if (team.members.size() < 2 || team.members.size() > 4) {
                System.out.println(
                        "Registration failed: A team must have 2 to 4 members.");
                return false;
            }

            if (!rules.containsKey(team.track)) {
                System.out.println("Registration failed: Unknown track.");
                return false;
            }

            for (Student student : team.members) {
                if (registeredStudents.contains(student.name)) {
                    System.out.println("Registration failed: "
                            + student.name + " already belongs to a team.");
                    return false;
                }
            }

            for (Student student : team.members)
                registeredStudents.add(student.name);

            teams.add(team);

            System.out.println("Team " + team.name + " registered ("
                    + team.members.size() + " members, "
                    + team.track + " track).");

            return true;
        }

        boolean submitProject(Team team, Project project) {
            if (state == State.PUBLISHED || !teams.contains(team)
                    || team.project != null) {
                System.out.println("Submission failed: team cannot submit a project.");
                return false;
            }

            team.project = project;
            state = State.JUDGING;

            System.out.println("Project '" + project.name
                    + "' submitted by " + team.name + ".");

            return true;
        }

        void recordScore(Project project, int idea,
                         int execution, int presentation) {
            if (state == State.PUBLISHED) {
                System.out.println(
                        "Rescore rejected: Results have already been published.");
                return;
            }

            Team owner = null;

            for (Team team : teams) {
                if (team.project == project) {
                    owner = team;
                    break;
                }
            }

            if (owner == null) {
                System.out.println(
                        "Score rejected: project was not submitted.");
                return;
            }

            project.score = new Score(idea, execution, presentation);

            double result = rules.get(owner.track).calculate(project.score);

            System.out.println("Score recorded for '" + project.name + "'.");
            System.out.printf(Locale.US, "Final score: %.2f.%n", result);
        }

        void publishResults() {
            state = State.PUBLISHED;
            System.out.println("Results published.");
        }
    }

    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon();

        Team byteBusters = new Team("ByteBusters", "Innovation",
                Arrays.asList(new Student("Asha"),
                        new Student("Ravi"), new Student("Neha")));

        Team soloCoder = new Team("SoloCoder", "Open",
                Arrays.asList(new Student("Kiran")));

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        Project project = new Project("SmartAttend");

        if (hackathon.submitProject(byteBusters, project))
            hackathon.recordScore(project, 8, 7, 9);

        hackathon.publishResults();
        hackathon.recordScore(project, 10, 7, 9);
    }
}