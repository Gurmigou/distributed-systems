package Lab_1;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Task_B {
    // 1 - works
    // 0 - stopped
    private static final AtomicInteger SEMAPHORE = new AtomicInteger(0);

    private static Thread thread1;
    private static Thread thread2;

    public static void main(String[] args) {
        JFrame mainFrame = createFrame();
        mainFrame.setSize(500, 600);
        Container contentPane = mainFrame.getContentPane();
        contentPane.setLayout(new GridLayout(2, 1, 0, 0));

        JSlider slider = createSlider();
        mainFrame.add(slider);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 0, 0));

        JButton start1Button = new JButton("ПУСК 1");
        buttonPanel.add(buttonAsPanel(start1Button));

        JButton stop1Button = new JButton("СТОП 1");
        buttonPanel.add(buttonAsPanel(stop1Button));

        JButton start2Button = new JButton("ПУСК 2");
        buttonPanel.add(buttonAsPanel(start2Button));

        JButton stop2Button = new JButton("СТОП 2");
        buttonPanel.add(buttonAsPanel(stop2Button));

        // Start
        start1Button.addActionListener(e -> {
            thread1 = new TestThread(slider, -1, val -> val >= 10, Thread.MIN_PRIORITY);
            thread1.setName("Thread 1");
            start1Button.setEnabled(false);
            stop1Button.setEnabled(true);
            thread1.start();
        });
        start2Button.addActionListener(e -> {
            thread2 = new TestThread(slider, 1, val -> val <= 90, Thread.MAX_PRIORITY);
            thread2.setName("Thread 2");
            start2Button.setEnabled(false);
            stop2Button.setEnabled(true);
            thread2.start();
        });

        // Stop
        stop1Button.addActionListener(e -> {
            thread1.interrupt();
            stop1Button.setEnabled(false);
            start1Button.setEnabled(true);
        });
        stop2Button.addActionListener(e -> {
            thread2.interrupt();
            stop2Button.setEnabled(false);
            start2Button.setEnabled(true);
        });
        stop1Button.setEnabled(false);
        stop2Button.setEnabled(false);

        mainFrame.add(buttonPanel);
        mainFrame.setVisible(true);
    }

    static class TestThread extends Thread {
        private final JSlider slider;
        private final Integer changeValue; // +1 or -1
        private final Predicate<Integer> sliderPredicate;

        public TestThread(JSlider slider, Integer changeValue, Predicate<Integer> sliderPredicate, Integer initialPriority) {
            this.slider = slider;
            this.changeValue = changeValue;
            this.sliderPredicate = sliderPredicate;
            this.setPriority(initialPriority);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public void run() {
            while (SEMAPHORE.get() != 0) ;
            SEMAPHORE.getAndSet(1);

            while (!interrupted()) {
                int sliderValue = slider.getValue();
                if (sliderPredicate.test(sliderValue)) {
                    slider.setValue(sliderValue + changeValue);
                }
            }
            SEMAPHORE.getAndSet(0);
        }
    }

    private static JFrame createFrame() {
        JFrame frame = new JFrame("Task B");
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

    private static JPanel buttonAsPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.add(button);
        return panel;
    }
}
