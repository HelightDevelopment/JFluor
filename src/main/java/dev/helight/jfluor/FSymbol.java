package dev.helight.jfluor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FSymbol {

    //Extended Ascii
    DOUBLE_BOTTOM_LEFT_CORNER("╚"),
    DOUBLE_BOTTOM_RIGHT_CORNER("╝"),
    DOUBLE_TOP_LEFT_CORNER("╔"),
    DOUBLE_TOP_RIGHT_CORNER("╗"),
    DOUBLE_VERTICAL("║"),
    DOUBLE_VERTICAL_TAP_LEFT("╣"),
    DOUBLE_VERTICAL_TAP_RIGHT("╠"),
    DOUBLE_HORIZONTAL("═"),
    DOUBLE_HORIZONTAL_TAP_TOP("╩"),
    DOUBLE_HORIZONTAL_TAP_BOTTOM("╦"),
    DOUBLE_CROSSING("╬"),

    SINGLE_BOTTOM_LEFT_CORNER("└"),
    SINGLE_BOTTOM_RIGHT_CORNER("┘"),
    SINGLE_TOP_LEFT_CORNER("┌"),
    SINGLE_TOP_RIGHT_CORNER("┐"),
    SINGLE_VERTICAL("│"),
    SINGLE_VERTICAL_TAP_LEFT("┤"),
    SINGLE_VERTICAL_TAP_RIGHT("├"),
    SINGLE_HORIZONTAL("─"),
    SINGLE_HORIZONTAL_TAP_TOP("┴"),
    SINGLE_HORIZONTAL_TAP_BOTTOM("┬"),

    BLOCK("█"),
    BLOCK_HALF_BOTTOM("▄"),
    BLOCK_HALF_TOP("▀"),
    SHADE_DENSE("▓"),
    SHADE("▒"),

    //Older symbols which should be supported by modern consoles
    CHECK_MARK("✔"),
    CHECK_MARK_HEAVY("✅"),
    CANCEL("❌"),
    STARS("✨"),
    COFFEE("☕"),
    MAIL("✉"),
    HEARTH("♥"),
    CLOCK("⏰"),

    //Newer symbols
    GLOBE("\uD83C\uDF0D"),
    INTERNET("\uD83C\uDF10"),
    APPLE("\uD83C\uDF4E"),
    BEER("\uD83C\uDF7A"),
    PIANO("\uD83C\uDFB9"),
    POINT_RIGHT("\uD83D\uDC49"),
    POINT_LEFT("\uD83D\uDC48"),
    POINT_UP("\uD83D\uDC46"),
    POINT_DOWN("\uD83D\uDC47"),
    THUMBS_UP("\uD83D\uDC4D"),
    THUMBS_DOWN("\uD83D\uDC4E"),
    FLOPPY("\uD83D\uDCBE"),
    DISK("\uD83D\uDCBF"),
    FOLDER("\uD83D\uDCC1"),
    DOCUMENT("\uD83D\uDCC4"),
    CALENDAR("\uD83D\uDCC5"),
    GRAPH_INCREMENT("\uD83D\uDCC8"),
    GRAPH_DECREMENT("\uD83D\uDCC9"),
    NOTES("\uD83D\uDCCB"),
    PIN("\uD83D\uDCCD"),
    PAPERCLIP("\uD83D\uDCCE"),
    BOOK("\uD83D\uDCD5"),
    BOOK_STACK("\uD83D\uDCDA"),
    CONNECTION_ANTENNA("\uD83D\uDCF6"),
    CAMERA("\uD83D\uDCF7"),
    /* Hope you get the joke */ @Deprecated TV("\uD83D\uDCFA"),
    LOCK_CLOSED("\uD83D\uDD12"),
    LOCK_OPEN("\uD83D\uDD13"),
    KEY("\uD83D\uDD11"),
    LINK("\uD83D\uDD17"),
    FLAME("\uD83D\uDD25"),
    WATER_DROP("\uD83D\uDCA7"),
    FLASHLIGHT("\uD83D\uDD26"),
    WRENCH("\uD83D\uDD27"),
    HAMMER("\uD83D\uDD28"),
    SCREW("\uD83D\uDD29"),
    KNIFE("\uD83D\uDD2A"),
    MICROSCOPE("\uD83D\uDD2C"),
    TELESCOPE("\uD83D\uDD2D"),
    ROCKET("\uD83D\uDE80"),
    TRAFFIC_LIGHT("\uD83D\uDEA6"),
    BLOCKADE("\uD83D\uDEA7"),
    STOP("\uD83D\uDEAB"),
    RED_FLAG("\uD83D\uDEA9"),
    REVOLVING_LIGHT("\uD83D\uDEA8"),
    DOOR("\uD83D\uDEAA"),
    HELICOPTER("\uD83D\uDE81"),
    SYRINGE("\uD83D\uDC89"),
    CREDIT_CARD("\uD83D\uDCB3");

    private String code;

}
