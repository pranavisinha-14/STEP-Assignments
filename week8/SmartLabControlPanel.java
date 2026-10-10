package week8;

import java.util.*;

public class SmartLabControlPanel {
    interface Capability {
        String getName();
        boolean apply(String value, String device);
    }

    static class PowerCapability implements Capability {
        boolean on;

        public String getName() {
            return "Power";
        }

        public boolean apply(String value, String device) {
            if (!value.equalsIgnoreCase("ON")
                    && !value.equalsIgnoreCase("OFF")) {
                System.out.println("Rejected: Power must be ON or OFF.");
                return false;
            }

            on = value.equalsIgnoreCase("ON");
            System.out.println(device + ": " + (on ? "ON" : "OFF") + ".");
            return true;
        }
    }

    static class BrightnessCapability implements Capability {
        int brightness;

        public String getName() {
            return "Brightness";
        }

        public boolean apply(String value, String device) {
            try {
                int v = Integer.parseInt(value);

                if (v < 0 || v > 100) {
                    System.out.println("Rejected: Brightness must be between 0% and 100%.");
                    return false;
                }

                brightness = v;
                System.out.println(device + ": brightness set to " + v + "%.");
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Rejected: Invalid brightness value.");
                return false;
            }
        }
    }

    static class TemperatureCapability implements Capability {
        int temperature;

        public String getName() {
            return "Temperature";
        }

        public boolean apply(String value, String device) {
            try {
                int v = Integer.parseInt(value);

                if (v < 16 || v > 30) {
                    System.out.println("Rejected: " + device
                            + " temperature must be between 16°C and 30°C.");
                    return false;
                }

                temperature = v;
                System.out.println(device + ": temperature set to " + v + "°C.");
                return true;
            } catch (NumberFormatException e) {
                System.out.println("Rejected: Invalid temperature value.");
                return false;
            }
        }
    }

    static class Device {
        String name;
        Map<String, Capability> capabilities = new LinkedHashMap<>();

        Device(String name) {
            this.name = name;
        }

        void addCapability(Capability capability) {
            capabilities.put(capability.getName(), capability);
            System.out.println(name + ": " + capability.getName()
                    + " capability added.");
        }

        boolean apply(String capability, String value) {
            Capability c = capabilities.get(capability);

            if (c == null)
                return false;

            return c.apply(value, name);
        }
    }

    static class SceneStep {
        String capability, value;

        SceneStep(String capability, String value) {
            this.capability = capability;
            this.value = value;
        }
    }

    static class Scene {
        String name;
        List<SceneStep> steps = new ArrayList<>();

        Scene(String name) {
            this.name = name;
        }

        void addStep(String capability, String value) {
            steps.add(new SceneStep(capability, value));
        }

        void execute(List<Device> devices) {
            System.out.println("Scene '" + name + "' started.");
            int count = 0;

            for (SceneStep step : steps) {
                for (Device device : devices) {
                    if (device.capabilities.containsKey(step.capability)) {
                        if (device.apply(step.capability, step.value))
                            count++;
                    }
                }
            }

            System.out.println("Scene '" + name
                    + "' completed: " + count + " actions applied.");
        }
    }

    public static void main(String[] args) {
        Device ac = new Device("Lab AC");
        ac.addCapability(new PowerCapability());
        ac.addCapability(new TemperatureCapability());

        Device lights = new Device("Ceiling Lights");
        lights.addCapability(new PowerCapability());
        lights.addCapability(new BrightnessCapability());

        Device projector = new Device("Projector");
        projector.addCapability(new PowerCapability());

        List<Device> devices = Arrays.asList(ac, lights, projector);

        Scene scene = new Scene("Lecture Mode");
        scene.addStep("Power", "ON");
        scene.addStep("Brightness", "40");
        scene.addStep("Temperature", "24");

        scene.execute(devices);

        ac.apply("Temperature", "12");

        projector.addCapability(new BrightnessCapability());
        projector.apply("Brightness", "70");
    }
}