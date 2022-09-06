package Lab_1;

import javax.swing.*;
import java.awt.*;
import java.util.function.Predicate;

public class Task_A {
    private static Thread thread1;
    private static Thread thread2;

    public static void main(String[] args) {
        JFrame mainFrame = createFrame();
        mainFrame.setSize(500, 600);
        Container contentPane = mainFrame.getContentPane();
        contentPane.setLayout(new GridLayout(4, 1, 1, 1));

        JSlider slider = createSlider();
        mainFrame.add(slider);

        JPanel button = createButton();
        mainFrame.add(button);

        configureNumberInputs(mainFrame);

        thread1 = new TestThread(slider, 1, val -> val <= 90, Thread.NORM_PRIORITY);
        thread2 = new TestThread(slider, -1, val -> val >= 10, Thread.NORM_PRIORITY);

        mainFrame.setVisible(true);
    }

    private static void configureNumberInputs(JFrame frame) {
        JSpinner spinner1 = createNumberInput();
        spinner1.addChangeListener(e -> thread1.setPriority((int) spinner1.getValue()));
        frame.add(createNumberInputPanel(spinner1, "Thread 1 (+1)"));

        JSpinner spinner2 = createNumberInput();
        spinner1.addChangeListener(e -> thread2.setPriority((int) spinner2.getValue()));
        frame.add(createNumberInputPanel(spinner2, "Thread 2 (-1)"));
    }

    static class TestThread extends Thread {
        private final JSlider slider;
        private final Integer changeValue; // +1 or -1
        private final Predicate<Integer> sliderPredicate;

        private final static Integer CHANGE_BOUND = 1_500_000;
        private int count;

        public TestThread(JSlider slider, Integer changeValue, Predicate<Integer> sliderPredicate, Integer initialPriority) {
            this.slider = slider;
            this.changeValue = changeValue;
            this.sliderPredicate = sliderPredicate;
            this.setPriority(initialPriority);
        }

        @Override
        public void run() {
            while (!interrupted()) {
                count++;
                int sliderValue = slider.getValue();
                if (count > CHANGE_BOUND && sliderPredicate.test(sliderValue)) {
                    changeSliderValue(sliderValue + changeValue);
                    count = 0;
                }
            }
        }

        private synchronized void changeSliderValue(int newValue) {
            this.slider.setValue(newValue);
        }
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Task A");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        return frame;
    }

    private static JSlider createSlider() {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private static JSpinner createNumberInput() {
        SpinnerModel model = new SpinnerNumberModel(Thread.NORM_PRIORITY, Thread.MIN_PRIORITY, Thread.MAX_PRIORITY, 1);
        return new JSpinner(model);
    }

    private static JPanel createNumberInputPanel(JSpinner spinner, String label) {
        JPanel panel = new JPanel();
        JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
        panel.setPreferredSize(new Dimension(300, 100));
        panel.add(spinner);

        JComponent editor = spinner.getEditor();
        Dimension prefSize = new Dimension(100, 35);
        editor.setPreferredSize(prefSize);
        return panel;
    }

    private static JPanel createButton() {
        JButton button = new JButton("Start!");
        button.addActionListener(e -> {
            thread1.start();
            thread2.start();
            button.setEnabled(false);
        });

        JPanel panel = new JPanel();
        panel.add(button);
        return panel;
    }
}
