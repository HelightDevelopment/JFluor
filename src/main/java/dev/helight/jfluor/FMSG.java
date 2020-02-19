package dev.helight.jfluor;

import com.google.common.base.Joiner;
import dev.helight.jfluor.abstraction.Loggable;
import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class FMSG implements Appendable, Flushable {

    public static FMSG builder() {
        return new FMSG();
    }

    @Getter
    private final StringBuffer buffer = new StringBuffer(64);

    private final AtomicLong timestamp = new AtomicLong(System.currentTimeMillis());

    @Getter
    private JFluor parent;

    private FColor colorActive = null;

    public FMSG parent(JFluor parent) {
        this.parent = parent;
        return this;
    }

    /**
     *
     */
    public FMSG logStack(int depth) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (depth != -1) {
            elements = Arrays.copyOfRange(elements, 2, depth + 2);
        }
        ArrayUtils.reverse(elements);


        List<String> stack = new ArrayList<>();
        for (StackTraceElement element : elements) {
            String[] classParts = element.getClassName().split(Pattern.quote(".")) ;
            stack.add(classParts[classParts.length - 1] + "." + element.getMethodName()+ ":" + element.getLineNumber());
        }

        return append("\n").append(Joiner.on(" ➨ ").join(stack)).append("\n");
    }

    public FMSG timingStart() {
        timestamp.set(System.currentTimeMillis());
        return this;
    }

    public FMSG timingStop(String format) {
        final long current = System.currentTimeMillis();
        long time = current - timestamp.get();
        append(String.format(format, time));
        return this;
    }

    public FMSG symbol(FSymbol symbol) {
        append(symbol.getCode());
        return this;
    }

    public FMSG symbol(FSymbol symbol, FColor color) {
        FColor save = colorActive;
        color(color);
        append(symbol.getCode());
        resetColor();
        if (save != null) color(save);
        return this;
    }

    public FMSG color(FColor color) {
        colorActive = color;
        append(color.code);
        return this;
    }

    public FMSG append(Loggable loggable) {
        append(loggable.log().snapshot().stringBuffer);
        return this;
    }

    public FMSG br() {
        append("\n");
        return this;
    }

    public FMSG resetColor() {
        colorActive = null;
        append("\u001b[30m");
        return this;
    }

    public FMSG r() {
        colorActive = null;
        append("\r");
        return this;
    }

    @Override
    public FMSG append(CharSequence csq) {
        if (csq == null) return this;
        buffer.append(csq);
        return this;
    }

    @Override
    public FMSG append(CharSequence csq, int start, int end) {
        buffer.append(csq, start, end);
        return this;
    }

    @Override
    public FMSG append(char c) {
        buffer.append(c);
        return this;
    }

    public FMSG append(long l) {
        buffer.append(l);
        return this;
    }

    MessageSnapshot snapshot() {
        return new MessageSnapshot(buffer);
    }

    @Override
    public void flush() {
        if (colorActive != null) resetColor();
        MessageSnapshot snapshot = snapshot();
        parent.getMessageQueue().add(snapshot);
        buffer.delete(0, buffer.length());
    }

    static class MessageSnapshot {

        @Getter
        final StringBuffer stringBuffer;

        public MessageSnapshot(StringBuffer stringBuffer) {
            this.stringBuffer = new StringBuffer(stringBuffer);
        }
    }

}
