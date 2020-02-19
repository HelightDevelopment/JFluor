package dev.helight.jfluor.progress;

import dev.helight.jfluor.FColor;
import dev.helight.jfluor.FMSG;
import dev.helight.jfluor.JFluor;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.Setter;

import java.util.Objects;

public class FProgressBar<K extends Number> implements Observer<K> {

    @Setter
    private K min = (K)Integer.valueOf(0);

    @Setter
    private K max = (K)Integer.valueOf(100);

    @Setter
    private K current = (K)Integer.valueOf(0);

    private BarStyle style = new AsciiBar();

    private String label = "Loading";
    private long timestamp = 0;
    private K last;

    private Thread thread;

    FProgressBar(K min, K max, K current, BarStyle style, String label, long timestamp, K last, Thread thread) {
        this.min = min;
        this.max = max;
        this.current = current;
        this.style = style;
        this.label = label;
        this.timestamp = timestamp;
        this.last = last;
        this.thread = thread;
    }

    public static <K extends Number> FProgressBarBuilder<K> builder() {
        return new FProgressBarBuilder<K>();
    }

    public void start(JFluor fluor) {
        timestamp = System.currentTimeMillis();
        thread = new Thread(() -> {
            FMSG fmsg = new FMSG().parent(fluor);
            while (true) {
               log(fmsg);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void log(FMSG builder) {
        if(Objects.equals(last, current)) return;
        last = current;
        builder.r();
        style.draw(this, builder);
        builder.flush();
    }

    public long remainingTime() {
        long elapsed = System.currentTimeMillis() - timestamp;
        double percent = elapsed / ratio();
        return Double.valueOf((1-ratio()) * percent).longValue();
    }

    public void stop() {
        thread.interrupt();
    }

    public double ratio() {
        return ((current.doubleValue() - min.doubleValue()) / (max.doubleValue() - min.doubleValue()));
    }

    public void stepTo(K current) {
        this.current = current;
    }

    public static void main(String[] args) {
        PublishSubject<Integer> subject = PublishSubject.create();

        JFluor jFluor = new JFluor("test");
        Utf8Bar utf = new Utf8Bar();
        utf.spaces = false;
        FProgressBar<Integer> bar = FProgressBar.<Integer>builder()
                .style(utf)
                .max(20000)
                .build();
        bar.start(jFluor);
        subject.subscribeWith(bar);
        new Thread(() -> {
            for (int i = 0; i <= 20000; i++) {
                subject.onNext(i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            bar.start(jFluor);
        }).start();
    }
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(K k) {
        stepTo(k);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public interface BarStyle {

        void draw(FProgressBar bar, FMSG builder);

    }

    public static class Utf8Bar implements BarStyle {

        public boolean colored = true;

        public boolean spaces = true;

        @Override
        public void draw(FProgressBar bar, FMSG builder) {
            double ratio = bar.ratio();
            int lineLength = 100;
            int cursor = Double.valueOf(lineLength * ratio).intValue();
            if (colored) builder.color(FColor.RED);
            builder.append(bar.label);
            if (colored) builder.resetColor();
            builder.append(" |");
            if (colored) builder.color(FColor.GREEN);
            for (int i = 0; i < cursor; i++) {
                builder.append("█");
            }
            for (int i = 0; i < (lineLength - cursor); i++) {
                builder.append(spaces ? " " : "░");
            }
            if (colored) builder.resetColor();
            builder.append("| ");
            if (colored) builder.color(FColor.BLUE);
            builder.append((int)(ratio * 10000) / 100).append("%");
            if (colored) builder.resetColor();
            builder.append(" | ");
            if (colored) builder.color(FColor.YELLOW);
            builder.append(bar.remainingTime() / 1000).append("s");
        }
    }

    public static class AsciiBar implements BarStyle {

        public boolean colored = true;

        @Override
        public void draw(FProgressBar bar, FMSG builder) {
            double ratio = bar.ratio();
            int lineLength = 100;
            int cursor = Double.valueOf(lineLength * ratio).intValue();
            if (colored) builder.color(FColor.RED);
            builder.append(bar.label);
            if (colored) builder.resetColor();

            builder.append(" |");

            for (int i = 0; i < cursor; i++) {
                if (colored) builder.color(FColor.GREEN);
                builder.append("=");
            }

            if (cursor == lineLength) {
                builder.append("=");
            } else builder.append(">");

            builder.resetColor();

            for (int i = 0; i < (lineLength - cursor); i++) {
                builder.append(" ");
            }

            builder.append("| ");
            if (colored) builder.color(FColor.BLUE);
            builder.append((int)(ratio * 10000) / 100).append("%");
            if (colored) builder.resetColor();

            builder.append(" | ");
            if (colored) builder.color(FColor.YELLOW);
            builder.append(bar.remainingTime() / 1000).append("s");
        }
    }

    public static class FProgressBarBuilder<K extends Number> {
        private K min = (K)Integer.valueOf(0);
        private K max = (K)Integer.valueOf(100);
        private K current = (K)Integer.valueOf(0);
        private BarStyle style = new AsciiBar();
        private String label = "";
        private long timestamp = 0;
        private K last;
        private Thread thread;

        FProgressBarBuilder() { }

        public FProgressBarBuilder<K> min(K min) {
            this.min = min;
            return this;
        }

        public FProgressBarBuilder<K> max(K max) {
            this.max = max;
            return this;
        }

        public FProgressBarBuilder<K> current(K current) {
            this.current = current;
            return this;
        }

        public FProgressBarBuilder<K> style(BarStyle style) {
            this.style = style;
            return this;
        }

        public FProgressBarBuilder<K> label(String label) {
            this.label = label;
            return this;
        }

        public FProgressBar<K> build() {
            return new FProgressBar<K>(min, max, current, style, label, timestamp, last, thread);
        }

        public String toString() {
            return "FProgressBar.FProgressBarBuilder(min=" + this.min + ", max=" + this.max + ", current=" + this.current + ", style=" + this.style + ", label=" + this.label + ", timestamp=" + this.timestamp + ", last=" + this.last + ", thread=" + this.thread + ")";
        }
    }
}

