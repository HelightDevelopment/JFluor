package dev.helight.jfluor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FBox {

    public static void main(String[] args) {
        FBox fBox = new FBox();
        FBoxContent c1 = new FBoxContent("1234","abcdefgh");
        FBoxContent c2 = new FBoxContent("Dummy text sentence");
        FBoxContent c3 = new FBoxContent("Lorem impsum dolor","si me amet","dorime");
        fBox.insertRow(Arrays.asList(c1));
        fBox.insertRow(Arrays.asList(c2,c3));
        fBox.layout();
    }

    public static final int WIDTH = 100;
    private List<List<FBoxContent>> layout = new ArrayList<>();

    public void layout() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FSymbol.SINGLE_TOP_LEFT_CORNER.getCode());
        for (int i = 0; i < WIDTH - 2; i++) stringBuilder.append(FSymbol.SINGLE_HORIZONTAL.getCode());
        stringBuilder.append(FSymbol.SINGLE_TOP_RIGHT_CORNER.getCode());
        stringBuilder.append("\n");

        stringBuilder.append(FSymbol.SINGLE_BOTTOM_LEFT_CORNER.getCode());
        for (int i = 0; i < WIDTH - 2; i++) stringBuilder.append(FSymbol.SINGLE_HORIZONTAL.getCode());
        stringBuilder.append(FSymbol.SINGLE_BOTTOM_RIGHT_CORNER.getCode());
        System.out.println(stringBuilder.toString());
        System.out.println(calcHeight());

    }

    public int calcHeight() {
        return layout.stream()
                .map(list -> list.stream()
                        .map(FBoxContent::height)
                        .sorted(Comparator.reverseOrder())
                        .findFirst().get())
                .reduce(Integer::sum).get();
    }

    public void insertRow(List<FBoxContent> row) {
        layout.add(row);
    }

    static class FBoxContent {

        public FBoxContent(String... content) {
            this.content = content;
        }

        String[] content;

        public int width() {
            return Math.max(Arrays.asList(content)
                    .stream()
                    .map(String::length)
                    .sorted(Comparator.reverseOrder())
                    .findFirst().get(), 0);
        }

        public int height() {
            return Math.max(content.length, 0);
        }

    }

}
