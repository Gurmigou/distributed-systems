package Exam.socket;

import Exam.model.Abiturient;

import java.util.ArrayList;
import java.util.List;

public class DataObject {
    private static final List<Abiturient> abiturients = new ArrayList<>();

    private static final int MIN_GRADE = 5;
    private static final int HIGH_GRADE = 10;
    private static final int NORM_GRADE = 7;

    static {
        abiturients.add(Abiturient.of(1L, "Name1", "Surname1",
                "Ukraine", "123456781", new int[]{1, 2, 3}));

        abiturients.add(Abiturient.of(2L, "Name2", "Surname2",
                "Ukraine", "123456782", new int[]{1, 3, 5}));

        abiturients.add(Abiturient.of(3L, "Name3", "Surname3",
                "Ukraine", "123456783", new int[]{5, 4, 5}));

        abiturients.add(Abiturient.of(4L, "Name4", "Surname4",
                "Ukraine", "123456784", new int[]{1, 1, 1}));

        abiturients.add(Abiturient.of(5L, "Name5", "Surname5",
                "Ukraine", "123456785", new int[]{3, 5, 5}));
    }

    public static List<Abiturient> getAbiturientsWithGradeLessMin() {
        List<Abiturient> result = new ArrayList<>();
        for (Abiturient abiturient : abiturients) {
            int sum = abiturient.calculateSum();
            if (sum <= MIN_GRADE) {
                result.add(abiturient);
            }
        }
        return result;
    }

    public static List<Abiturient> getAbiturientsWithGradeMoreNorm() {
        List<Abiturient> result = new ArrayList<>();
        for (Abiturient abiturient : abiturients) {
            int sum = abiturient.calculateSum();
            if (sum >= NORM_GRADE && sum < HIGH_GRADE) {
                result.add(abiturient);
            }
        }
        return result;
    }

    public static List<Abiturient> getAbiturientsWithGradeMoreHigh() {
        List<Abiturient> result = new ArrayList<>();
        for (Abiturient abiturient : abiturients) {
            int sum = abiturient.calculateSum();
            if (sum >= HIGH_GRADE) {
                result.add(abiturient);
            }
        }
        return result;
    }

    public static List<Abiturient> getAbiturientsWithGradeHigherThan(int n) {
        List<Abiturient> result = new ArrayList<>();
        for (Abiturient abiturient : abiturients) {
            int sum = abiturient.calculateSum();
            if (sum > n) {
                result.add(abiturient);
            }
        }
        return result;
    }
}
