# Pen LLD

## Overview

This folder now uses a simpler design that still covers the requirements:

- write text
- consume ink
- stop when ink is empty
- keep writing behavior independent from pen type
- support runtime behavior change
- support refillable and disposable pens
- support open and closed state

## Approach

- `Pen`: the main class
- `Ink`: manages color, capacity, level, consume, and refill
- `WritingBehavior`: strategy for writing style and ink usage
- `PenType`, `PenState`, `InkColor`: enums for classification and state

## UML Diagram

```mermaid
classDiagram
    class Pen {
        -String brand
        -PenType penType
        -boolean refillSupported
        -Ink ink
        -PenState state
        -WritingBehavior writingBehavior
        +open() void
        +close() void
        +write(String) String
        +refill(int) void
        +setWritingBehavior(WritingBehavior) void
        +supportsRefill() boolean
        +getPenType() PenType
        +getInkColor() InkColor
        +getInkLevel() int
        +getState() PenState
    }

    class Ink {
        -InkColor color
        -int capacity
        -int currentLevel
        +consume(int) void
        +refill(int) void
        +isEmpty() boolean
    }

    class WritingBehavior {
        <<interface>>
        +getName() String
        +calculateInkRequired(String) int
        +getWritableCharacterCount(int) int
    }

    class SmoothWritingBehavior
    class RoughWritingBehavior
    class PressureBasedWritingBehavior

    class Writable {
        <<interface>>
        +write(String) String
    }

    class Closable {
        <<interface>>
        +open() void
        +close() void
    }

    class PenType {
        <<enum>>
        BALL_PEN
        GEL_PEN
        FOUNTAIN_PEN
    }

    class PenState {
        <<enum>>
        OPEN
        CLOSED
    }

    class InkColor {
        <<enum>>
        BLUE
        BLACK
        RED
        GREEN
    }

    Writable <|.. Pen
    Closable <|.. Pen
    Pen --> Ink
    Pen --> WritingBehavior
    Pen --> PenType
    Pen --> PenState
    Ink --> InkColor
    WritingBehavior <|.. SmoothWritingBehavior
    WritingBehavior <|.. RoughWritingBehavior
    WritingBehavior <|.. PressureBasedWritingBehavior
```

## How To Use

Compile:

```bash
javac *.java
```

Run:

```bash
java Main
```

Example:

```java
Pen pen = new Pen(
    "Pilot",
    PenType.GEL_PEN,
    new Ink(InkColor.BLACK, 100, 20),
    new SmoothWritingBehavior(),
    true
);

pen.open();
String output = pen.write("Hello");
pen.setWritingBehavior(new PressureBasedWritingBehavior());
pen.refill(10);
pen.close();
```
