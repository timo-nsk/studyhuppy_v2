import {expect} from "vitest";
import {fireEvent, render, screen} from "@testing-library/react";
import {TimerCard} from "./TimerCard";
import userEvent from '@testing-library/user-event'
import { vi } from "vitest";
import {act} from "react";

test("Für einen Timer werden alle relevanten Infos angezeigt", () => {
    render(<TimerCard id={1} titel={"Mathe"} sekunden={70} aktiv={true}/>);
    const titel = screen.getByTestId("timer-link");
    const zeitangabe = screen.getByTestId("zeitangabe");
    const timerBtn = screen.getByTestId("timer-btn");

    expect(titel.innerHTML).toBe("Mathe");
    expect(zeitangabe.innerHTML).toBe("Zeit: 1m 10s");
    expect(timerBtn.innerHTML).toBe("start");
})

test("Wenn der Timer-Button geklickt wird, ändert sich seine Beschriftung", async () => {
    render(<TimerCard id={1} titel={"Mathe"} sekunden={70} aktiv={true}/>);
    const user = userEvent.setup();
    const timerBtn = screen.getByTestId("timer-btn");
    await user.click(timerBtn);
    const btnTitel = screen.getByTestId("timer-btn").innerHTML;

    expect(btnTitel).toBe("stop");
})

//TODO: FIX wird nicht zweimal geklickt?
test.skip("Wenn der Timer läuft und auf den Timer-Button geklickt wird, ändert sich die Beschriftung", async () => {
    render(<TimerCard id={1} titel={"Mathe"} sekunden={70} aktiv={true}/>);
    const user = userEvent.setup();
    const timerBtn = screen.getByTestId("timer-btn");
    await user.click(timerBtn);
    await user.click(timerBtn);
    const btnTitel = screen.getByTestId("timer-btn").innerHTML;

    expect(btnTitel).toBe("start");
})

test("Ein Timer mit 10 Sekunden wird für 10 Sekunden gestartet, die Zeitangabe zeigt danach 20 Sekunden an", () => {
    vi.useFakeTimers();
    render(<TimerCard id={1} titel={"Mathe"} sekunden={10} aktiv={true}/>);
    const timerBtn = screen.getByTestId("timer-btn");
    fireEvent.click(timerBtn);
    act(() => {
        vi.advanceTimersByTime(10000);
    });
    const zeitangabe = screen.getByTestId("zeitangabe");

    expect(zeitangabe.innerHTML).toBe("Zeit: 20s");
})

test("Der Link zur Detail-Seite eines Timers mit id=1 wird korrekt im Attribut zusammengesetzt", () => {
    render(<TimerCard id={1} titel="Mathe" sekunden={70} aktiv={true}/>);

    expect(screen.getByTestId("timer-link")).toHaveAttribute("href", "/timer/1");
})