package dev.helight.jfluor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FColorTest {


    private JFluor jFluor;

    @BeforeEach
    void setUp() {
        jFluor = new JFluor("DebugLogger");
    }

    @Test
    void testString() {
        FMSG fmsg = FMSG
                .builder()
                .parent(jFluor)
                .logStack(1);

        for (FColor value : FColor.values()) {
            fmsg.append(value.testString()).append(" ").reset();
        }

        fmsg
                .br()
                .color(FColor.GREEN)
                .append("Lorem impsum dolor")
                .symbol(FSymbol.APPLE, FColor.RED)
                .append("si me amet")
                .br();

        fmsg.flush();
    }
}